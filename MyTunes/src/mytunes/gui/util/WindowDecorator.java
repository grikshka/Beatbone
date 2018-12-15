/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Acer
 */
public class WindowDecorator {
    
    public static void fadeOutStage(Stage stage)
    {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1000),
                new KeyValue (stage.getScene().getRoot().opacityProperty(), 0.8))); 
            timeline.play();
    }
    
    public static void fadeInStage(Stage stage)
    {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1000),
                new KeyValue (stage.getScene().getRoot().opacityProperty(), 1)));   
        timeline.play();
    }
    
}
