package org.example.chat_interface_test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server_ {
    public static ArrayList<Client_handler> list = new ArrayList<>();
    public static ArrayList<String> username_list = new ArrayList<>();

    public static  void main(String [] args) throws Exception{
        String user_verification;

        ServerSocket serverSocket = new ServerSocket(54444);
        System.out.println("Server is online !!!");
        while(true){
           Socket socket = serverSocket.accept();
           PrintWriter ask_username = new PrintWriter(socket.getOutputStream(),true);
           BufferedReader take_username = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           do {
               ask_username.println("<!>notification from server..Enter a !!UNIQUE!! username <!>");
               user_verification = take_username.readLine();
               user_verification = user_verification.replace(" ","");
               if(!username_list.contains(user_verification)){
                   break;
               }
           }while(true);
           String username = user_verification;
            username_list.add(username);
            ask_username.println("`"+username_list+"`");
            String string = "`"+username_list+"`";
            ask_username.println("<!> Welcome "+username+":) <!>");
           new Thread(() ->{
               try {
                   Client_handler clientHandler = new Client_handler(socket,username,string);
                   list.add(clientHandler);
                   System.out.println(username +" connected!!");
                   clientHandler.run();
               }catch (Exception e){
                   System.out.println("problem while connecting user ");
               }
           }).start();

        }
    }
}
