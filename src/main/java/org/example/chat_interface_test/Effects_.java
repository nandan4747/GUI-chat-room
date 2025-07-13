package org.example.chat_interface_test;

import javafx.animation.ScaleTransition;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Effects_ {
    ScaleTransition transition;
    public void Hover_effect(Region obj, String bg_color,String fg_color,double zoom,int sec){
        transition = new ScaleTransition(Duration.millis(sec),obj);
        transition.setToX(zoom);
        transition.setToY(zoom);
        obj.setStyle("-fx-background-color:"+bg_color+";"+"-fx-text-fill:"+fg_color+";");
        transition.play();
    }
    public void Cancel_Effect(Region obj,double x,double y,String color,String fg_color,int sec){
        transition = new ScaleTransition(Duration.millis(sec),obj);
        transition.setToX(x);
        transition.setToY(y);
        transition.play();
        obj.setStyle("-fx-background-color:"+color+";"+"-fx-text-fill:"+fg_color+";");
    }
}
