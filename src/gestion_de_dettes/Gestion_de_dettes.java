/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_de_dettes;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Gestion_de_dettes extends Application {
    
      public static Stage stage;
    @Override
    public void start(Stage stage) {
        
        Parent root = null;
        try { // load the FXML file
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        stage.setScene(new Scene(root)); // Add the GUI to scene and the scene to stage (GUI > Scene > Stage)

        // Adding icon of App
        stage.getIcons().add(new Image("/resources/images/gc-logo-48px.png"));
        stage.setTitle("Gestion Dettes"); // Change the title of app
        Gestion_de_dettes.stage = stage;
        stage.show(); // make stage visible
    }

    public static void centerOnScreen() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Gestion_de_dettes.stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        Gestion_de_dettes.stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
   
}
