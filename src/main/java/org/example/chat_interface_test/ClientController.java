package org.example.chat_interface_test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private HBox h_box;
    private VBox v_box;
   @FXML
   public TextField text_field;
   @FXML
   private Button button_left,button_right;
   @FXML
   private ListView list_view;

   @FXML
   public Button send_btn;

   @FXML
   ListView member_view;

   public TextFlow reciver,sender;

   public Effects_ effects;

   public void set_message_left(String message){
       if(!message.equals("")){
           int message_index = message.indexOf(":");
           if(message_index == -1){
               message_index = 0;
           }
           h_box = new HBox();
           h_box.setMaxWidth(822);
           h_box.setAlignment(Pos.CENTER_LEFT);

           v_box = new VBox();
           v_box.setAlignment(Pos.TOP_LEFT);

           Text text = new Text(message.substring(message_index+1));
           text.setWrappingWidth(826);
           reciver = new TextFlow(text);
           reciver.setMaxWidth(826);
           reciver.setStyle("-fx-background-color: rgb(208,208,208);" +
                   "-fx-background-radius:12px;"+
                   "-fx-padding :8");

           v_box.getChildren().add(new Text(message.substring(0,message_index)));
           h_box.getChildren().add(reciver);
           v_box.getChildren().add(h_box);

           list_view.getItems().add(v_box);
           list_view.scrollTo(list_view.getItems().size()-1);
           text_field.clear();
       }
   }
    public void set_message_right(String message){
       helper(message);
    }
    public void helper(String message){
        if(!message.equals("")){

            h_box = new HBox();
            h_box.setMaxWidth(820);
            h_box.setAlignment(Pos.CENTER_RIGHT);
            Text text = new Text(message);
            text.setWrappingWidth(820);
            sender = new TextFlow(text);
            sender.setStyle("-fx-background-color:rgb(90, 167, 255);" +
                    "-fx-background-radius:12px;"+
                    "-fx-padding :8");
            sender.setMaxWidth(820);
            h_box.getChildren().add(sender);
            list_view.getItems().add(h_box);
            list_view.scrollTo(list_view.getItems().size()-1);
            text_field.clear();
    }
    }
    public void alerter_(String message){
        Text text = new Text(message);

       text.setStyle("-fx-fill:rgb(118, 118, 118);"+
               "-fx-font-family:'Arial Black';");
       h_box = new HBox();
       h_box.setAlignment(Pos.CENTER);
       h_box.getChildren().add(text);
       list_view.getItems().add(h_box);
       list_view.scrollTo(list_view.getItems().size()-1);

    }

    // effects

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            effects = new Effects_();
            double x,y;
        x = send_btn.getScaleX();
        y = send_btn.getScaleY();

            send_btn.setOnMouseEntered(mouseEvent -> {
                effects.Hover_effect(send_btn,"white","rgb(11, 170, 255)",1.2,150);
            });

            send_btn.setOnMouseExited(mouseEvent -> {
                effects.Cancel_Effect(send_btn,x,y,"rgb(11, 170, 255)","white",150);
            });
            double X = text_field.getScaleX();
            double Y = text_field.getScaleY();

        text_field.setOnMouseEntered(mouseEvent -> {
            effects.Hover_effect(text_field,"white","black",1.05,150);
        });

        text_field.setOnMouseExited(mouseEvent -> {
            effects.Cancel_Effect(text_field,x,y,"rgb(207, 207, 207)","black",150);
        });

        // listview cell modify
        member_view.setFixedCellSize(50);
    }
}