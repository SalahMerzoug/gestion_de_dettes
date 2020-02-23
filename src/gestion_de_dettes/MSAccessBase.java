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
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import javafx.scene.control.TableView;
import static net.ucanaccess.converters.Functions.date;

public class MSAccessBase {

    // Chemin de la base
    private String path;

    // Nom d'utilisateur
    private String user;
  
    // Mot de passe
    private String password;

    // Connection vers la base
    private static Connection connection;
    private static int countc;
    private static double  prixC;
    private static double prixR;
    private static double  prixT;

    /* Constructeur */
    public MSAccessBase(String path, String user, String password) {
        this.path = path;
        this.user = user;
        this.password = password;
        prixC=0;
        prixR=0;
        prixT=0;
    }


    /*
    *Connection à la base
    *@return : true si la connexion est réussie, false si échouée
     */
    public int connect() {
        try {
            // Chargement du driver ODBC
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            File F = new File(path);
            
            // Connexion à la base
            String connectionString = "jdbc:ucanaccess://" + path;

            connection = DriverManager.getConnection(connectionString, user, password);
            countc=0;
            System.out.println("conx b1");

        } catch (ClassNotFoundException e) {
            System.out.println("Problème avec le driver ODBC" + e.getMessage());
            
            return -1;
        } catch (SQLException e) {
            System.out.println("Impossible de se connecter à la base" + e.getMessage() + "\n" + password);
            return 0;
        }
        return 1;
    }


    /*
    *Déconnexion de la base
    *@return : true si la déconnexion est réussie, false sinon
     */
    public boolean disconnect() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /*
    *Envoi d'une requête de sélection
    *@param : sql
    *@return : result
     */
    public ResultSet SQLSelect(String sql) throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            return result;
        } catch (SQLException e) {
            result.close();
            statement.close();
            return null;
        }
    }

    /*
    *Envoi d'une requête de mise à jour (insert, update, delete)
    *@param : sql
     */
    public void SQLUpdate(String sql) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }

    public static void SQLinsert(String nomp, Date d, String desp, String etat, double prix,Double pc,Double pr) throws SQLException {
       // String sql = "INSERT INTO client VALUES(" + nomp + "," + d + "," + desp + "," + prix + "," + etat + ");";
        try {
            int id=5;
            Double p=prix;
            String query = " insert into client values (?, ? , ? , ? , ? , ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
             preparedStmt.setInt(1, id);
            preparedStmt.setString(2, nomp);
            preparedStmt.setDate(6, d);
            preparedStmt.setString(3, desp);
            preparedStmt.setDouble(5, p);
            preparedStmt.setString(4, etat);
            preparedStmt.setDouble(7, pc);
            preparedStmt.setDouble(8, pr);
            preparedStmt.executeUpdate();
            
                System.out.println("okisss");
           
            
           
       // Statement statement = null;
       
            //statement = connection.createStatement();
            //statement.executeUpdate(sql);
        } catch (SQLException e) {
           System.out.println("no"+e.getMessage()); 
        }
    }

    public static void SQLupdate(int id, String nomp, Date d, String desp, String etat, double prix,Double pc,Double pr) throws SQLException {
        try {
           Double p=prix;
            String query = "update client set "
                    + " dated = ?"
                    + ", descp = ?"
                    + ", montant = ?"
                    + ", Etatd = ?"
                    + ", prixc = ?"
                    + ", prixr = ?"
                    + " where ID = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDate(1, d);
            preparedStmt.setString(2, desp);
            preparedStmt.setDouble(3, p);
            preparedStmt.setString(4, etat);
            preparedStmt.setDouble(5, pc);
            preparedStmt.setDouble(6, pr);
               preparedStmt.setInt(7, id);
            System.err.println("date"+preparedStmt.getParameterMetaData().getParameterClassName(6));
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
        System.out.println(""+e.getMessage());
        
        }
    }
 public static int  SQLdelet(int id) throws SQLException {
     int d=-1;  
     try {
            String query = "delete from client where ID = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, id);
          preparedStmt.execute();
          
               d=1;
         
        } catch (SQLException e) {
        System.out.println(""+e.getMessage());
        d=-1;
        }
     return d;
    }
    public static void  updatetab(TableView<TableClient> tableClient, ArrayList<TableClient> listc) {
        Statement statement = null;
         prixT=0;
         prixC=0;
         prixR=0;
        countc=0;
        tableClient.getItems().clear();
        listc.clear();
        String sql = "SELECT * FROM client ;";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = "" + rs.getInt(1);
                String nomp = rs.getString(2);
                String date = rs.getDate(6).toString();
                String descp = rs.getString(3);
                double prix = rs.getDouble(5);
                Double prixc = rs.getDouble(7);
                 Double prixr = rs.getDouble(8);
                prixT+=prix;
                prixC+=prixc;
                prixR+=prixr;
                String etat = rs.getString(4);
                TableClient t = new TableClient(id,nomp, descp, etat,date, prix,prixc,prixr);
              
                tableClient.getItems().add(t);
                listc.add(t);
                countc++;
            }
        } catch (SQLException e) {
System.out.println(e.getMessage());
        }
       System.out.println("prix t :"+prixT);
       System.out.println("prix c :"+prixC);
       System.out.println("prix r :"+prixR);

    }

    public static int getCountc() {
        return countc;
    }

    public static double getPrixC() {
        return prixC;
    }

    public static void setPrixC(double prixC) {
        MSAccessBase.prixC = prixC;
    }

    public static double getPrixR() {
        return prixR;
    }

    public static void setPrixR(double prixR) {
        MSAccessBase.prixR = prixR;
    }

    public static double getPrixT() {
         System.out.println("prix t :"+prixT);
        return prixT;
    }

    public static void setPrixT(double prixT) {
        MSAccessBase.prixT = prixT;
    }
    
}
