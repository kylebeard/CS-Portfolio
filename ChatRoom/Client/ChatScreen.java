/**
 * This program is a rudimentary demonstration of Swing GUI programming.
 * Note, the default layout manager for JFrames is the border layout. This
 * enables us to position containers using the coordinates South and Center.
 * <p>
 * Usage:
 * java ChatScreen
 * <p>
 * When the user enters text in the textfield, it is displayed backwards
 * in the display area.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;

public class ChatScreen extends JFrame implements ActionListener, KeyListener {

    private JButton sendButton;
    private JButton exitButton;
    private JTextField sendText;
    private JTextArea displayArea;
    private DataOutputStream toServer;
    //private OutputStreamWriter toServer;
    private JSONObject jo;
    private LocalDateTime date;

    /**
     * Headers
     */
    public String dm, message, username;
    public boolean isConnected, disconnect;
    public double length;

    public ChatScreen(DataOutputStream toServer, String username) {
        this.toServer = toServer;
        this.username = username;

        /**
         * a panel used for placing components
         */
        JPanel p = new JPanel();


        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Enter Message Here ...");
        p.setBorder(titled);

        /**
         * set up all the components
         */
        sendText = new JTextField(30);
        sendButton = new JButton("Send");
        exitButton = new JButton("Exit");

        /**
         * register the listeners for the different button clicks
         */
        sendText.addKeyListener(this);
        sendButton.addActionListener(this);
        exitButton.addActionListener(this);

        /**
         * add the components to the panel
         */
        p.add(sendText);
        p.add(sendButton);
        p.add(exitButton);

        /**
         * add the panel to the "south" end of the container
         */
        getContentPane().add(p, "South");

        /**
         * add the text area for displaying output. Associate
         * a scrollbar with this text area. Note we add the scrollpane
         * to the container, not the text area
         */
        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        getContentPane().add(scrollPane, "Center");

        /**
         * set the title and size of the frame
         */
        setTitle("ChatRoom: " + username);
        pack();

        setVisible(true);
        sendText.requestFocus();

        /* anonymous inner class to handle window closing events */
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

    }


    public static void main(String[] args) {
        try {
            Socket server = new Socket(args[0], 1134);
            DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
            //OutputStreamWriter toServer = new OutputStreamWriter(server.getOutputStream(), StandardCharsets.UTF_8);
            ChatScreen win = new ChatScreen(toServer, args[1]);
            //win.displayMessage("My name is " + args[1], "None");

            Thread HelperThread = new Thread(new HelperThread(server, win, args[1]));
            HelperThread.start();
        } catch (UnknownHostException uhe) {
            System.out.println(uhe);
        } catch (java.io.IOException ie) {
        }

    }

    /**
     * Send the message to the specified DataOutputStream
     *
     * @param message
     * @param toServer
     */
    public void sendTextToServer(String message, DataOutputStream toServer) {
        if (message.length() != 0) {
            try {
                toServer.writeBytes(message + "\n");
                sendText.setText("");
                sendText.requestFocus();
            } catch (java.io.IOException ie) {
            }
        }
    }

    /**
     * Displays a message and if dm == the users username, bold the text.
     */
    public void displayMessage(String message, String dm) {
        if (dm.equals(username)) {
            displayArea.setFont(new Font("SansSerif", Font.BOLD, 14));
            message = message.toUpperCase();
            displayArea.append(message + "\n");
        } else {
            displayArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            displayArea.append(message + "\n");
        }
    }


    /**
     * Constructs a JSONObject with headers required to disconnect
     *
     * @param disconnect
     * @param sender
     */
    public String constructHeaders(boolean disconnect, String sender) {
        JSONObject jo = new JSONObject();
        this.disconnect = disconnect;

        jo.put("disconnect", disconnect);
        jo.put("sender", sender);
        return jo.toString();
    }

    /**
     * Constructs a JSONObject with headers required to send a message
     *
     * @param dm
     * @param sender
     * @param message
     * @param length
     * @param date
     */
    public String constructHeaders(String dm, String sender, String message, int length, LocalDateTime date) {
        JSONObject jo = new JSONObject();

        jo.put("dm", dm);
        jo.put("sender", sender);
        jo.put("message", message);
        jo.put("length", length);
        jo.put("date", date.toString());
        return jo.toString();
    }

    public void setUsername(String username){
        this.username = username;
        setTitle("ChatRoom:" + username);
    }
    /*
     * These methods responds to keystroke events and fulfills
     * the contract of the KeyListener interface.
     */

    /**
     * This method responds to action events .... i.e. button clicks
     * and fulfills the contract of the ActionListener interface.
     */
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source == sendButton) {
            date = (java.time.LocalDateTime.now());
            String s = sendText.getText().trim();
            if (s.contains("@")) {
                int i = s.indexOf("@"); //get index of '@'

                // then get the text from the index of '@' up until the next space
                int j = s.indexOf(" ", i);

                // the username
                dm = s.substring(i + 1, j + 1);
                dm = dm.toLowerCase();
                // trim the @username
                s = s.substring(j + 1);
                System.out.println("DM is: " + dm);
            }
            // send the formatted headers to the server
            sendTextToServer(constructHeaders(dm, username, s, s.length(), date), toServer);
        } else if (source == exitButton) {
            sendTextToServer(constructHeaders(true, username), toServer);
            System.exit(0);
        }
    }

    /**
     * This is invoked when the user presses
     * the ENTER key.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            date = (java.time.LocalDateTime.now());
            String s = sendText.getText().trim();
            if (s.contains("@")) {
                int i = s.indexOf("@"); //get index of '@'
                int j = s.indexOf(" ", i); // then get the text from the index of '@' up until the next space
                dm = s.substring(i + 1, j + 1);
                dm = dm.toLowerCase();

                s = s.substring(j + 1);
                System.out.println("DM is: " + dm);
            }
            // send the formatted headers to the server
            sendTextToServer(constructHeaders(dm, username, s, s.length(), date), toServer);
        }
    }

    /**
     * Not implemented
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Not implemented
     */
    public void keyTyped(KeyEvent e) {
    }
}

