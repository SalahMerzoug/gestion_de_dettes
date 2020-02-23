/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_de_dettes.client;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import gestion_de_dettes.MSAccessBase;
import gestion_de_dettes.TableClient;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Salah_Mer
 */
public class FXMLeditC implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField nomp;
    @FXML
    TextField prix;
     @FXML
    TextField prixc;
    @FXML
    TextField prixr;
  
    @FXML  
     StackPane root;
      @FXML
    TextArea desp;
    @FXML
    JFXDatePicker date;
     int id =-1;
 private JFXSnackbar toastMsg; 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nomp.requestFocus();
        nomp.setFocusTraversable(true);
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();       
        date.getEditor().setText("" + dateFormat.format(date1));
           
        toastMsg = new JFXSnackbar(root);
         prixr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                System.out.println(" Text Changed to  " + newValue + ")\n");
               
            }
        });
        prixc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                System.out.println(" Text Changed to  " + newValue + ")\n");
                double p= Double.parseDouble(newValue);
                double pt =Double.parseDouble(prix.getText());
                if(p<=pt)
                {
                      prixr.setText(""+(pt-p));
                }
                else
                {
                     prixc.setText(""+pt);
                     prixr.setText("0");
                }
            }
        });
         prix.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                 prixr.setText(newValue);
                 prixc.setText(""+0);
            }
        });
    }
     public void init(TableClient T)
     {
         id  =Integer.parseInt(T.getId());
         System.out.println("id"+id);
         nomp.setText(T.getNomp());
        date.getEditor().setText(T.getDate());
        prix.setText(T.getSprix());
        prixc.setText(""+T.getPrixc());
        prixr.setText(""+T.getPrixr());
        desp.setText(T.getDesp());
     }
    @FXML
    public void addclient() {
        String nompp = nomp.getText();
        String p = prix.getText();
        String dat = date.getEditor().getText();
        String etat ="dette";
        String des =this.desp.getText();
        if (!nompp.equals("") && !p.equals("") && !dat.equals("")) {
            try {
                double prix =Double.parseDouble(p);
                double pc=Double.parseDouble(prixc.getText());
                double pr=Double.parseDouble(prixr.getText());
                 if (pr> 0) {
                    etat = "الدين";
                } else {
                    etat = "خالص";
                }
                  LocalDateTime dateTime = LocalDateTime.now();// your ldt
                java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
               
                MSAccessBase.SQLupdate(id,nompp,sqlDate, des, etat,prix,pc,pr);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLadd.class.getName()).log(Level.SEVERE, null, ex);
                toastMsg.show("حدث مشكب في التعديل ", 3000);
            }
          toastMsg.show("تم التعديل بنجاخ", 2000);
        }
        else
        {
            toastMsg.show("الرجاء ملئ جميع حقول * ", 3000); 
        }

    }
}