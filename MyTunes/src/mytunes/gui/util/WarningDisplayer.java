/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.util;

import java.util.Optional;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Acer
 */
public class WarningDisplayer {
    
    public void displayError(Stage currentStage, String header, String content)
    {
        WindowDecorator.fadeOutStage(currentStage);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/mytunes/gui/css/Alert.css").toExternalForm());  
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setOnCloseRequest(new EventHandler()
            {
                @Override
                public void handle(Event event) 
                {
                    WindowDecorator.fadeInStage(currentStage);
                }
            }
        );
        Optional<ButtonType> action = alert.showAndWait();
    }
    
    public Optional<ButtonType> displayConfirmation(Stage currentStage, String header, String content)
    {
        WindowDecorator.fadeOutStage(currentStage);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/mytunes/gui/css/Alert.css").toExternalForm()); 
        alert.setOnCloseRequest(new EventHandler()
            {
                @Override
                public void handle(Event event) 
                {
                    WindowDecorator.fadeInStage(currentStage);
                }
            }
        );
        return alert.showAndWait();
    }
    
}
