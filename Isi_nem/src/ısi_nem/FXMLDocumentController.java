/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ısi_nem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Ortam;

/**
 * FXML Controller class
 *
 * @author shest
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    TextField urun_giris;
    @FXML
    private PasswordField sifre_giris;
    @FXML
    private Button Giris_butonu;
    @FXML
    private Label hata;
    @FXML
    private AnchorPane ana;
    public static int id_no;
    @FXML
    private Label kayit_text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void Click(MouseEvent event) throws ClassNotFoundException, SQLException, IOException{
        //Kullanıcı girişi kontrol ediliyor
        ArrayList<String> urun_no = new ArrayList<>();
        ArrayList<String> sifre = new ArrayList<>();
        System.out.println(urun_giris.getText());
        System.out.println(sifre_giris.getText());
        DataBase.users.getUsers().forEach((t)->{
            urun_no.add(t.getUsername());
            sifre.add(t.getPassword());
        });
        boolean b = false;
        for (int i = 0; i < urun_no.size(); i++) {
            if (urun_no.get(i).equals(urun_giris.getText()) && sifre.get(i).equals(sifre_giris.getText())) {
                b = true;
            }
        }
        if (b) {
            hata.setText("Giriş başarılı yönlendiriliyorsunuz...");
                FXMLLoader loader=new FXMLLoader(getClass().getResource("veri.fxml"));
            
                // Böylece fxml dosyamızı bir loader nesnenisne atadık
 
                try {
                    Parent root=loader.load();
                    VeriController secondController=loader.getController();
                    
 
                    // Şimdi diğer stage yi oluşturup gösterelim
                    Stage stage2 = new Stage();
                    stage2 = (Stage) ana.getScene().getWindow();
                    stage2.close();
                    Stage stage =new Stage();
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    //Kullanıcının girdiği ürün no bir sonraki sayfada lazım olduğu için static olarak gönderiliyor
                    id_no=Integer.parseInt(urun_giris.getText());
                    secondController.id_yaz(id_no);
                   
 
                    
 
 
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        else{
           //Hatalı giriş durumunda kullanıcı uyarılıyor
           Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Bilgilendirme Mesajı");
                alert.setHeaderText("Hata -> Kullanıcı adı yada şifre hatalı !!!");
                alert.setContentText("Lütfen tekrar deneyiniz...");
                alert.showAndWait(); 
        }

    }
    
    //Kayıt ol label'ına tıklandığında bu fonksiyon çalışıp kullanıcı kayıt olma sayfasına yönlendiriliyor
    @FXML
    private void kayit_ol_tıklandı(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Kayitsayfasi.fxml"));
        Parent root=loader.load();
        KayitsayfasiController secondController=loader.getController();
        Stage stage2 = new Stage();
        stage2 = (Stage) ana.getScene().getWindow();
        //Giris sayfası kapatılıyor
        stage2.close();
        Stage stage =new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        //Yeni kayıt sayfası açılıyor
        stage.show();
        
    }
    
    


    
}
