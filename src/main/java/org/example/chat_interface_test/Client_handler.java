package org.example.chat_interface_test;

import java.io.*;
import java.net.Socket;

public class Client_handler implements Runnable {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    String username;

    public Client_handler(Socket socket, String username)throws Exception{
        this.username = username;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(),true);
        server_broad_cast("<!>Notification from server!!! "+ username+" has joined the chat!!<!>");
    }
    public void broad_cast(String message){
        for(Client_handler user_info : Server_.list){
            if(!username.equals(user_info.username)){
               // System.out.println(message);
                user_info.out.println(username+":"+message);
            }
        }
    }
    public void server_broad_cast(String message){
        for(Client_handler user_info : Server_.list){
            if(!username.equals(user_info.username)){
                // System.out.println(message);
                user_info.out.println(message);
            }
        }
    }
    public void private_msg(String message){
        int space_index = message.indexOf(" ");
        if(space_index != -1) {
            String private_user = message.substring(1, space_index);
            for (Client_handler user_info : Server_.list) {
                if (user_info.username.equals(private_user)) {
                    // System.out.println(message);
                    user_info.out.println("[PVT]msg " + username + ":" + message.substring(space_index + 1, message.length()));
                }
            }
        }
    }

    @Override
    public void run() {
        // reading messages
        try{
            while (true) {

                String message = in.readLine();
                if (message.startsWith("@")) {
                    private_msg(message);

                } else if (message.equals("%-X-%")) {
                    break;

                } else if (message.startsWith("<") && message.endsWith(">")) {
                    server_broad_cast(message);
                } else {
                    broad_cast(message);
                }
            }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        finally {
            try {
                server_broad_cast("<!> user "+username+" left the chat..<!>");
                Server_.list.remove(this);
                socket.close();
            } catch (Exception e) {
                System.out.println("error while exiting !!! ");
            }
            }





    }
}
