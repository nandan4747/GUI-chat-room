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

    public static  void main(String [] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(54444);
        System.out.println("Server is online !!!");
        while(true){
           Socket socket = serverSocket.accept();
           PrintWriter ask_username = new PrintWriter(socket.getOutputStream(),true);
           ask_username.println("<!>notification from server ! Enter your username <!>");
           BufferedReader take_username = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           String username = take_username.readLine().replace(" ","");
           new Thread(() ->{
               try {
                   Client_handler clientHandler = new Client_handler(socket,username);
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
