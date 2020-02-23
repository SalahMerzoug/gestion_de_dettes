package gestion_de_dettes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    @FXML // Parent node of all child (root node)
    private VBox root;

    @FXML
    private JFXTextField fieldUser;
    @FXML
    private JFXPasswordField fieldPass;

    private JFXSnackbar toastMsg; // For showing msg (like toast in Android)
 MSAccessBase conx=null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toastMsg = new JFXSnackbar(root);
        
        root.setOnKeyPressed(e -> { // Execute login action if i click Enter button from Keyboard
            if(e.getCode().equals(KeyCode.ENTER)) {
                onLogin();
            }
        });
    }

    @FXML
    private void onLogin() {
        if(fieldUser.getText() == null || !fieldUser.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            toastMsg.show("Nom utilisateur faux !", 2000);
            return;
        }
        if(fieldPass.getText() == null || fieldPass.getText().length() < 4) {
            toastMsg.show("Mot de passe faux !", 2000);
            return;
        }
          conx=new MSAccessBase("data/bases.mdb", fieldUser.getText(),fieldPass.getText());
        int status = conx.connect();
        switch (status) {
            case -1 :
                toastMsg.show("Connection failed !", 2000);
                break;
            case 0 :
                toastMsg.show("Nom Utilisateur et/ou le mot de passe faux !", 2000);
                break;
            case 1 : // Login to the system (show system gui)
                Parent systemView = null;
                 ClientController p=null;
                try {
                   // systemView = FXMLLoader.load(getClass().getResource("Client.fxml"));
                     FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Client.fxml"));
                            systemView= loader1.load();
                             p=loader1.getController();
                            
                            
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                Stage stage = ((Stage) fieldUser.getScene().getWindow());
                
                Scene s=new Scene(systemView);
               
                stage.setScene(s);
               //Platform.runLater(()->  stage.initStyle(StageStyle.UTILITY));
                Gestion_de_dettes.stage =stage;
              //   Gestion_de_dettes.stage.setMaximized(true);
             Gestion_de_dettes.centerOnScreen();
                p.init(s);
                //Launcher.centerOnScreen(); // make stage in the center
                break;
        }

    }
}
