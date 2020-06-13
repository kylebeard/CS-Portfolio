/**
 * This thread is passed a socket that it reads from. Whenever it gets input
 * it writes it to the ChatScreen text area using the displayMessage() method.
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDateTime;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class HelperThread implements Runnable {
    Socket server;
    BufferedReader fromServer;
    DataOutputStream toServer;
    ChatScreen screen;

    /**
     * Headers
     */
    private String dm, message, sender;
    private boolean isConnected, disconnect;
    private double length;
    private int usernameCode = 0;
    private long errorCode; // number to attach to username if it is taken
    private String date;

    public HelperThread(Socket server, ChatScreen screen, String username) {
        this.server = server;
        this.screen = screen;
        this.sender = username;
        try {
            fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            toServer = new DataOutputStream(server.getOutputStream());
        } catch (java.io.IOException ie) {
        }
    }

    public void run() {
        String jsonString; // jsonString sent from server
        JSONObject jsonObject;


        try {
            sendInitHeader(toServer, usernameCode); // send the server our username
            while (true) {
                jsonString = fromServer.readLine();
                System.out.println("jsonString from server: " + jsonString);

                //extract keys and values from server-sent json string
                jsonObject = createJson(jsonString);

                // check if username is taken
                if (jsonObject.containsKey("errorCode")) {
                    errorCode = (long) jsonObject.get("errorCode");
                    if (errorCode == 1) { // username is taken
                        usernameCode++;
                        sendInitHeader(toServer, usernameCode);
                        continue;
                    }
                }
                String chatMsg = date + "\n" + sender + ": " + message + "\n";
                // now display it on the display area
                if ((date != null) && (sender != null) && (message != null)) {
                    if (dm != null)
                        screen.displayMessage(chatMsg, dm);
                    else
                        screen.displayMessage(chatMsg, "None");
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

    /**
     * Sends the initial header specifying username
     */
    private void sendInitHeader(DataOutputStream toServer, int usernameCode) {
        JSONObject json = new JSONObject();
        if (usernameCode > 0) {
            json.put("sender", sender + usernameCode); // there was a conflict, so add an integer
            screen.setUsername(sender + usernameCode); // set the users username to the new username
        } else {
            json.put("sender", sender);
        }
        screen.sendTextToServer(json.toString(), toServer);
    }

    /**
     * This method constructs a json object from the string that is being read in from the server
     */
    private JSONObject createJson(String fromServer) {
        JSONObject object = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            object = (JSONObject) parser.parse(fromServer);
            System.out.println("got to createJson");

            //if it contains errorCode it will contain isConnected
            if (object.containsKey("errorCode")) {
                //if it does...extract the value
                errorCode = (long) object.get("errorCode");
                isConnected = (boolean) object.get("isConnected");
            }

            // disconnect is sent by itself
            else if (object.containsKey("disconnect")) {
                disconnect = (Boolean) object.get("disconnect");
            }

            //if it contains "message"...it should also contain sender,
            //length and date according to the protocol
            else if (object.containsKey("message")) {
                message = (String) object.get("message");
                length = (Double) object.get("length");
                date = (String) object.get("date");
                sender = (String) object.get("sender");
                // it may contain a dm
                if (object.containsKey("dm")) {
                    dm = (String) object.get("dm");
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

}
