package gestion_de_dettes;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import gestion_de_dettes.client.FXMLeditC;
import java.awt.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import static javax.management.Query.value;
import jfxtras.labs.scene.control.gauge.Content;
import jfxtras.labs.scene.control.gauge.ContentBuilder;
import jfxtras.labs.scene.control.gauge.Gauge;
import jfxtras.labs.scene.control.gauge.MatrixPanel;
import jfxtras.labs.scene.control.gauge.MatrixPanelBuilder;

public class ClientController implements Initializable {

    @FXML
    private StackPane root;
      @FXML  
     StackPane ro;
   thread t=null;
    @FXML
    private TextField fieldSearch;
    @FXML
    private JFXComboBox<String> comboSearchBy;

    @FXML
    private TableView<TableClient> tableClient;
    @FXML
    private TableColumn<TableClient, String> idc;
    @FXML
    private TableColumn<TableClient, String> datec;
    @FXML
    private TableColumn<TableClient, String> nompc;
    @FXML
    private TableColumn<TableClient, String> prixc;
    @FXML
    private TableColumn<TableClient, Double> prc;
    @FXML
    private TableColumn<TableClient, Double> prr;
    ArrayList<TableClient> listc;
    
    @FXML
    private TableColumn<TableClient, String> etatc;
    @FXML
    private TableColumn<TableClient, String> desc;
    @FXML
    Pane panem;
   
   
    TableClient ClientSelected = null;
    // Dialog showing in (add/update) table
    public static JFXDialog dialogClientAdd, dialogClientEdit;
    Scene s;
    private JFXSnackbar toastMsg;
    // data of table
    // private List<Client> clients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      
        toastMsg = new JFXSnackbar(root);
        listc =new ArrayList<>();
       
       
        idc.setCellValueFactory(new PropertyValueFactory<TableClient, String>("id"));
        nompc.setCellValueFactory(new PropertyValueFactory<TableClient, String>("nomp"));
        datec.setCellValueFactory(new PropertyValueFactory<TableClient, String>("date"));
        prixc.setCellValueFactory(new PropertyValueFactory<TableClient, String>("sprix"));
         prc.setCellValueFactory(new PropertyValueFactory<TableClient, Double>("prixc"));
         prr.setCellValueFactory(new PropertyValueFactory<TableClient, Double>("prixr"));
        etatc.setCellValueFactory(new PropertyValueFactory<TableClient, String>("etat"));
        desc.setCellValueFactory(new PropertyValueFactory<TableClient, String>("desp"));      
       fieldSearch.setOnKeyPressed(s -> {
            switch (s.getCode().toString())
            {
                case "F1" :
                     onAdd();
                    break;
                 case "F2" :
                     onEdit(); 
                    
                      break;
                 case "F3" :
                     onDelete();
                     break;
                 case "F4" :
                     fieldSearch.requestFocus();
                     break;
            }
            
        });
      tableClient.setOnKeyPressed(s -> {
            switch (s.getCode().toString())
            {
                case "F1" :
                     onAdd();
                    break;
                 case "F2" :
                     onEdit(); 
                    
                      break;
                 case "F3" :
                     onDelete();
                     break;
                 case "F4" :
                     fieldSearch.requestFocus();
                     break;
            }
            
        });
       loadClientTableData();
        fieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                filterSearchTable(newValue);
              
            }
        });
        
    }
   
            
    public void init(Scene d) {
        s = d;
        s.setOnKeyPressed(s -> {
            switch (s.getCode().toString())
            {
                case "F1" :
                     onAdd();
                    break;
                 case "F2" :
                     onEdit(); 
                    
                      break;
                 case "F3" :
                    onDelete();
                     break;
                 case "F4" :
                     fieldSearch.requestFocus();
                     break;
            }
            
        });
      
 
    }

    @FXML
    private void onAdd() { // On Add ClientRegex
        AnchorPane paneAddClient = null;
        try {
            paneAddClient = FXMLLoader.load(getClass().getResource("client/FXMLad.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dialogClientAdd = getSpecialDialog(paneAddClient);
        dialogClientAdd.show();
    }
 @FXML
    private void onEdit() { // On Add ClientRegex
        AnchorPane paneAddClient = null;
        ClientSelected = tableClient.getSelectionModel().getSelectedItem();
       
        FXMLeditC pc;
        if(ClientSelected != null)
        { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client/FXMLedit.fxml"));
            paneAddClient = loader.load();
            pc=loader.getController();
            pc.init(ClientSelected);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dialogClientAdd = getSpecialDialog(paneAddClient);
        dialogClientAdd.show();
        
        }
        else
        {
            toastMsg.show("الرجاء اختر الزبون", 2000);
        }
    }    
    @FXML
    private void onDelete() {
       ClientSelected = tableClient.getSelectionModel().getSelectedItem();
        if(ClientSelected!=null)
        { JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefSize(600, 200);
        Text headerText = new Text("Confirmation");
        Text contentText = new Text("هل تريد حذف هذا الزبون؟ : "+ClientSelected.getNomp());
        headerText.setStyle("-fx-font-size: 18px");
        contentText.setStyle("-fx-font-size: 14px");

        content.setHeading(headerText);
        content.setBody(contentText);

        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("Oui");
        
        btnOk.setOnAction(e -> {
            int id=Integer.parseInt(ClientSelected.getId());
            int status=0;
            try {
                status = MSAccessBase.SQLdelet(id);
            } catch (SQLException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("status : " + status);
            if (status == -1) {
                toastMsg.show("Erreur de Connexion  !", 2000);
            } else {
                Notifications notification = Notifications.create()
                        .title("لقد قمت بإزالة الزبون :" +ClientSelected.getNomp())
                        .graphic(new ImageView(new Image("/resources/images/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT);
                notification.darkStyle();
                notification.show();
                loadClientTableData();
            }
            dialog.close();
            loadClientTableData();
        });

        JFXButton btnNo = new JFXButton("Non");
        btnOk.setPrefSize(120, 40);
        btnNo.setPrefSize(120, 40);
        btnOk.getStyleClass().addAll("btn", "btn-delete");
        btnNo.getStyleClass().addAll("btn");
        btnNo.setStyle("-fx-background-color: #DDD;-fx-text-fill: #333");

        content.setActions(btnOk, btnNo);
        StackPane stackpane = new StackPane();

        dialog.getStylesheets().add("/resources/css/crud-view.css");
          btnNo.setOnAction(e -> {
           
              dialog.close();
           
        });
        dialog.show();
        }
        else
        {
            toastMsg.show("الرجاء اختر الزبون", 2000);
        }
    }

    public void filterSearchTable(String str) {
      
        if(listc.size()>0 )
        {
          
           tableClient.getItems().clear();
          for( TableClient t :listc)  
          {
             if(t.getNomp().contains(str)==true) 
             {
                 tableClient.getItems().add(t);
             }
          }
        }
       if(str.length()==0)
       {
            tableClient.getItems().clear();
            tableClient.getItems().addAll(listc);
       }
        
        

    }
   public void loadClientTableData()
   {
      ro.toFront();
      ro.setVisible(true);
      if(t!=null && t.isAlive())
      {
          t.destroy();
      }
       t = new thread(ro, tableClient,panem, listc);
       t.start();
      
   }
    private JFXDialog getSpecialDialog(Region content) { // This function create dialog
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
                

         dialog.setOnDialogClosed(e -> loadClientTableData()); // if i close dialog: reload data to table
        return dialog;
    }

   

   
}
