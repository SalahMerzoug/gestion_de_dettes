/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_de_dettes;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import jfxtras.labs.scene.control.gauge.Content;
import jfxtras.labs.scene.control.gauge.ContentBuilder;
import jfxtras.labs.scene.control.gauge.MatrixPanel;
import jfxtras.labs.scene.control.gauge.MatrixPanelBuilder;

/**
 *
 * @author Salah_Mer
 */
public class thread extends Thread{
     StackPane ro;
     TableView<TableClient> tableClient;
      MatrixPanel panel;
       Pane panem;
         ArrayList<TableClient> listc;
       
    public thread(StackPane ro, TableView<TableClient> tableClient ,Pane p,  ArrayList<TableClient> li) {
        this.ro = ro;
        this.tableClient = tableClient;
        this.panem=p;
        listc=li;
    }
    // Platform.runLater(() ->
     public void creatpanm() {

      String pT="M_Total:" + MSAccessBase.getPrixT() + "DA";
       String pC="M_Paye:"+ MSAccessBase.getPrixC() + "DA";
       String pR="M_Restant:"+ MSAccessBase.getPrixR() + "DA";
        panel = MatrixPanelBuilder.create()
                .ledWidth(400)
                         .ledHeight(17)
                         .prefWidth(1100)
                         .prefHeight(50)
                .frameDesign(MatrixPanel.FrameDesign.DARK_GLOSSY)
                .frameVisible(true)
                .contents(new Content[]{
            new ContentBuilder().create()
            .color(Content.MatrixColor.GREEN)
            .type(Content.Type.TEXT)
            .origin(0, 1)
            .area(0, 0, 400, 15)
            .txtContent(pT)
            .font(Content.MatrixFont.FF_7x9)
            .fontGap(Content.Gap.DOUBLE)
            .align(Content.Align.CENTER)
            .effect(Content.Effect.NONE)
            .postEffect(Content.PostEffect.REPEAT)
            .order(Content.RotationOrder.SINGLE)
            .build() ,
             new ContentBuilder().create()
            .color(Content.MatrixColor.YELLOW)
            .type(Content.Type.TEXT)
            .origin(200, 0)
            .area(0, 0, 400, 7)
            .txtContent(pC)
            .font(Content.MatrixFont.FF_7x7)
            .fontGap(Content.Gap.DOUBLE)
            .align(Content.Align.CENTER)
            .effect(Content.Effect.NONE)
            .postEffect(Content.PostEffect.REPEAT)
            .order(Content.RotationOrder.SINGLE)
            .build(), 
             new ContentBuilder().create()
            .color(Content.MatrixColor.RED)
            .type(Content.Type.TEXT)
            .origin(200, 9)
            .area(0, 0, 400, 17)
            .txtContent(pR)
            .font(Content.MatrixFont.FF_7x7)
            .fontGap(Content.Gap.DOUBLE)
            .align(Content.Align.CENTER)
            .effect(Content.Effect.NONE)
            .postEffect(Content.PostEffect.REPEAT)
            .order(Content.RotationOrder.SINGLE)
            .build() 
        })
                .build();
      
       Platform.runLater(() -> panem.getChildren().clear());
       Platform.runLater(() -> panem.getChildren().add(panel));
    }
     @Override
     public void run()
     {
         System.out.println("Starting");
         Platform.runLater(() ->MSAccessBase.updatetab(tableClient,listc));
        Platform.runLater(()-> creatpanm());
          System.out.println("fin");
           Platform.runLater(() -> ro.setVisible(false));
             Platform.runLater(() -> ro.toBack());
         this.stop();
     }
}
