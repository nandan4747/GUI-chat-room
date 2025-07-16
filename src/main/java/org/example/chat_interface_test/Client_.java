package org.example.chat_interface_test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client_ extends Application {
    FXMLLoader fxmlLoader;
    ArrayList<String> user_list = new ArrayList<>();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    ClientController controller;

    Text text_;
    TextFlow text_flow_;

    Stage stage;
    @Override
    public void start(Stage stage) {
        try {
            this.stage = stage;

            this.fxmlLoader = new FXMLLoader(Client_.class.getResource("hello-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            scene.getStylesheets().add(getClass().getResource("Css_file.css").toExternalForm());

            stage.setTitle("Chat room");
            stage.setScene(scene);
            stage.show();

            Socket socket = new Socket("localhost", 54444);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            controller = fxmlLoader.getController();

            // message read
            new Thread(() -> {
                try {
                    String message;
                    while (true) {
                        message = in.readLine();
                        String ui_message = message;
                        if (message.startsWith("`") && message.endsWith("`")) {

                            message = message.substring(2, message.length() - 2);
                            String[] array = message.split(",");

                            user_list.addAll(Arrays.asList(array));


                            Platform.runLater(() -> {
                                controller.member_view.getItems().clear();

                                for (String t : array) {
                                    text_ = new Text(t);
                                    text_.setFont(Font.font(18));
                                    text_flow_ = new TextFlow(text_);
                                    text_flow_.setStyle("-fx-background-color:rgb(186, 233, 255);" + "-fx-border-radius:8px;" + "-fx-padding:8;" + "-fx-background-radius:6px;");
                                    text_flow_.setTextAlignment(TextAlignment.CENTER);

                                    controller.member_view.getItems().addAll(text_flow_);
                                }

                            });
                        } else if (message.startsWith("<") && message.endsWith(">")) {
                            Platform.runLater(() -> {
                                controller.alerter_(ui_message);
                            });
                        } else {
                            Platform.runLater(() -> {
                                controller.set_message_left(ui_message);
                            });
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error at message reading..");
                }
            }).start();

            controller.text_field.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String message = controller.text_field.getText();
                    String ui_message = message;

                    out.println(message);
                    javafx.application.Platform.runLater(() -> {
                        controller.set_message_right(ui_message);
                    });
                }


            });
            controller.send_btn.setOnAction(actionEvent -> {
                String message = controller.text_field.getText();
                String ui_message = message;
                out.println(message);
                javafx.application.Platform.runLater(() -> {
                    controller.set_message_right(ui_message);
                });
            });

            stage.setOnCloseRequest(windowEvent -> {
                out.println("%-X-%");
            });
        } catch (Exception e) {
            Alerting();
        }
    }
    //alert method
    public void Alerting(){
        javafx.application.Platform.runLater(()->{
            alert.setHeaderText("Unable to connect you to Server!!!!");
            alert.setContentText("Server is OFFLINE :/ ");
            alert.showAndWait();
            stage.close();
        });
    }


    public static void main(String[] args)throws Exception {
        launch();

    }
}