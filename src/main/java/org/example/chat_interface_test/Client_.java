package org.example.chat_interface_test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client_ extends Application {
    FXMLLoader fxmlLoader;
    ArrayList<String> user_list = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        this.fxmlLoader = new FXMLLoader(Client_.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("Css_file.css").toExternalForm());
        stage.setTitle("Chat room");
        stage.setScene(scene);
        stage.show();

        Socket socket = new Socket("localhost",54444);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        ClientController controller = fxmlLoader.getController();
        // message read
        new Thread(()->{
            try {
                String message ;
                while (true) {
                    message = in.readLine();
                    String ui_message = message;
                    if(message.startsWith("`") && message.endsWith("`")){

                        message = message.substring(2,message.length()-2);
                        String []array = message.split(",");

                        user_list.addAll(Arrays.asList(array));


                        javafx.application.Platform.runLater(()->{
                            controller.member_view.getItems().clear();
                            controller.member_view.getItems().addAll(array);
                        });
                    }
                    else if (message.startsWith("<") && message.endsWith(">")){
                        javafx.application.Platform.runLater(()->{
                            controller.alerter_(ui_message);
                        });
                    }
                    else{
                        javafx.application.Platform.runLater(()->{
                            controller.set_message_left(ui_message);
                        });
                    }
                }
            }catch (Exception e){

            }
        }).start();

        controller.text_field.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER) {
                String message = controller.text_field.getText();
                String ui_message = message;

                out.println(message);
                javafx.application.Platform.runLater(()->{
                    controller.set_message_right(ui_message);
                });
            }


        });
        controller.send_btn.setOnAction(actionEvent -> {
            String message = controller.text_field.getText();
            String ui_message = message;
            out.println(message);
            javafx.application.Platform.runLater(()->{
                controller.set_message_right(ui_message);
            });
        });

        stage.setOnCloseRequest(windowEvent -> {
            out.println("%-X-%");
        });
    }


    public static void main(String[] args)throws Exception {
        launch();

    }
}