import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.lang.Integer;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


@SuppressWarnings("unused")
public class Connection implements Runnable {
    public static final int BUFFER_SIZE = 256;
    public static final int PORT = 1134;
    // The number of bytes to read at a time
    public static final int CHUNK = 1024;
    private static ArrayList<String> usernames = new ArrayList<String>();
    private static ArrayList<Socket> connections = new ArrayList<Socket>();
    private Socket client;
    private String ipAddr; // IP of client
    /**
     * Headers
     */
    private String username, dm, message, sender, date;
    private int usernameCode, errorCode;
    private boolean isConnected, disconnect;
    private double length;


    public Connection(Socket client) {
        this.client = client;
        connections.add(client);
        // get client IP address
        ipAddr = client.getInetAddress().getHostAddress();

    }

    /**
     * This method runs in a separate thread.
     */
    public void run() {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedReader fromClient;
        //OutputStreamWriter toClient;
        DataOutputStream toClient;
        String jsonString;
        JSONObject jsonObject;
        JSONObject jsonResponse;


        try {
            /*
             * get the input and output streams for client
             */
            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //toClient = new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8);
            toClient = new DataOutputStream(client.getOutputStream());

            // read message from client
            while (fromClient != null) {
                jsonString = fromClient.readLine();
                System.out.println("jsonString from client is: " + jsonString);

                //this creates a json object from the string coming from client
                jsonObject = createJson(jsonString);

                //make the response
                jsonResponse = makeResponse(jsonObject);

                //check to see if the response is going to disconnect the user...if so..send them the message
                //and then disconnect
                if (jsonResponse.containsKey("disconnect") && jsonResponse.containsValue(true)) {
                    //tell user they are disconnecting
                    toClient.writeBytes(jsonResponse.toString() + "\n"); // \n for the readLine() method

                    //disconnect
                    closeSocket(fromClient, toClient);

                    // remove username from usernames
                    for (String uname : usernames) {
                        System.out.println("username:" + uname);
                        if (sender.equals(uname)) usernames.remove(uname);
                    }
                    // remove client from connections
                    for (Socket client : connections)
                        if (this.client == client) connections.remove(client);

                }
                // print usernames for debugging
                for (String uname : usernames)
                    System.out.println("username:" + uname);


                // send jsonResponse String to each connected client, only if it's a message
                if (jsonObject.containsKey("message")) {
                    for (Socket client : connections) {
                        //toClient = new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8);
                        toClient = new DataOutputStream(client.getOutputStream());
                        toClient.writeBytes(jsonResponse.toString() + "\n"); // \n for the readLine() method
                    }
                } else { // otherwise just send it to this client
                    toClient.writeBytes(jsonResponse.toString() + "\n");
                }
            }

        } catch (java.io.IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void closeSocket(BufferedReader fromClient, DataOutputStream toClient) {
        System.out.print("Got to closeSocket");
        if ((fromClient != null)) {
            try {
                fromClient.close();
            } catch (IOException ioe) {
            }
        }
        if (toClient != null) {
            try {
                toClient.close();
            } catch (IOException ioe) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject makeResponse(JSONObject jsonObject) {
        JSONObject responseObject = new JSONObject();

        //TODO: add one to sender
        //if the client sends us sender there will only be one k,v pair... and we respond with isConnected and
        //errorCode
        if (jsonObject.size() == 1) {
            sender = (String) jsonObject.get("sender");
            if (!usernames.contains(sender)) {
                usernames.add(sender);
                isConnected = true;
                errorCode = -1;
            } else if (usernames.contains(sender)) {
                isConnected = false;
                errorCode = 1;
            } else if (usernames.size() >= 100) {
                isConnected = false;
                errorCode = 2;
            }
            responseObject.put("isConnected", isConnected);
            responseObject.put("errorCode", errorCode);

            return responseObject;
        }

        //if the client sends us message...then we basically echo back what they
        //sent to us
        else if (jsonObject.containsKey("message")) {
            if (jsonObject.containsKey("dm")) {
                responseObject.put("dm", dm);
            }
            responseObject.put("sender", sender);
            responseObject.put("message", message);
            responseObject.put("length", length);
            responseObject.put("date", date);
            return responseObject;
        }
        //only other option is to disconnect
        //TODO: disconnect the client after send response
        else {
            disconnect = true;
            responseObject.put("disconnect", disconnect);
            return responseObject;

        }

    }

    /**
     * This method constructs a json object from the string that is being read in from the client
     */
    private JSONObject createJson(String inputFromClient) {
        JSONObject object = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            object = (JSONObject) parser.parse(inputFromClient);
            System.out.println("got to createJson");
            //if it contains sender...this is all that is being sent by the client
            //according to the protocol
            if (object.size() == 1) {
                //if it does...extract the value
                sender = (String) object.get("sender");
            }
            //if it contains disconnect...it should also contain sender
            //according to the protocol
            else if (object.containsKey("disconnect")) {
                disconnect = (Boolean) object.get("disconnect");
                sender = (String) object.get("sender");
            }

            //if it contains "dm"...it should also contain message,
            //length and date according to the protocol
            else if (object.containsKey("dm")) {
                dm = (String) object.get("dm");
                message = (String) object.get("message");
                length = (long) object.get("length");
                date = (String) object.get("date");
                sender = (String) object.get("sender");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }
}
