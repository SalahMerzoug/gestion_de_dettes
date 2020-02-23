/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_de_dettes;

/**
 *
 * @author Salah_Mer
 */
public class TableClient  {
    String id ;
    String nomp;
    String desp;
    String etat;
     String date;
     
    double prix;
    Double prixc;
    Double prixr;
    String sprix;
     public TableClient(String id, String nomp,String d, String desp, String etat, double prix) {
        this.id = id;
        this.nomp = nomp;
        this.date = d;
        this.desp = desp;
        this.etat = etat;
        this.prix = prix;
        sprix=""+prix;
    }

    public TableClient(String id, String nomp, String desp, String etat, String date, double prix, double prixc, double prixr) {
        this.id = id;
        this.nomp = nomp;
        this.desp = desp;
        this.etat = etat;
        this.date = date;
        this.prix = prix;
        this.prixc = prixc;
        this.prixr = prixr;
         sprix=""+prix;
    }
 
     
    public Double getPrixc() {
        return prixc;
    }

    public void setPrixc(double prixc) {
        this.prixc = prixc;
    }

    public Double getPrixr() {
        return prixr;
    }

    public void setPrixr(Double prixr) {
        this.prixr = prixr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
        this.sprix = ""+prix;
    }

    public String getSprix() {
        return sprix;
    }

    public void setSprix(String sprix) {
        this.sprix = sprix;
    }
    
}
