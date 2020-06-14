/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ısi_nem;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojo.Ortam;
import pojo.Users;

/**
 * FXML Controller class
 *
 * @author shest
 */
public class KayitsayfasiController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField e_posta;
    @FXML
    private TextField telno;
    @FXML
    private PasswordField sifre;
    @FXML
    private PasswordField sifre_tekrar;
    @FXML
    private AnchorPane ana;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

    @FXML
    private void kayıt_butonuna_tıklandı(MouseEvent event) throws IOException {
        EmailValidator emailValidator = new EmailValidator();
        if (sifre.getText().equals("") || sifre_tekrar.getText().equals("") || id.getText().equals("") || telno.getText().equals("") || e_posta.getText().equals("") ) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hata !!!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen boş alan bırakmayınız.");
            alert.showAndWait(); 
        }
        else if (!(sifre.getText().equals(sifre_tekrar.getText()))) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hata !!!");
            alert.setHeaderText(null);
            alert.setContentText("Girdiğiniz şifreler uyuşmuyor.");
            alert.showAndWait(); 
        }
        else if (!emailValidator.validate(e_posta.getText().trim())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hata !!!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen geçerli bir e posta adresi giriniz.");
            alert.showAndWait(); 
        }
        else if (sifre.getText().length()<8 || sifre_tekrar.getText().length()<8) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hata !!!");
            alert.setHeaderText(null);
            alert.setContentText("Şifreniz en az 8 karakterden oluşmalıdır.");
            alert.showAndWait(); 
        }else{
            int id_no = Integer.parseInt(id.getText());
            Users u =new Users();
            u.setId(id_no);
            u.setPassword(sifre.getText());
            u.setTel(telno.getText());
            u.setMail(e_posta.getText());
            u.setUsername(id.getText());
            u.setSms(1);
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                Transaction transaction = session.beginTransaction();
                session.save(u);
                transaction.commit();
                session.close();
            
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Geliştirici Mesajı !!!");
                alert.setHeaderText(null);
                alert.setContentText("Kayıt işlemi başarılı giriş ekranına yönlendirileceksiniz.");
                alert.showAndWait(); 
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata -> İşlem başarılı bir şekilde tamamlanmadı !!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Geliştirici ile iletişime geçiniz...");
                    alert.showAndWait();
            }
             
            
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root=loader.load();
            FXMLDocumentController secondController=loader.getController();
            Stage stage2 = new Stage();
            stage2 = (Stage) ana.getScene().getWindow();
            stage2.close();
            Stage stage =new Stage();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void geri_don_tıklandı(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root=loader.load();
        FXMLDocumentController secondController=loader.getController();
        Stage stage2 = new Stage();
        stage2 = (Stage) ana.getScene().getWindow();
        stage2.close();
        Stage stage =new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
