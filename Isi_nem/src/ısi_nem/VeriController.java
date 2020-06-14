/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ısi_nem;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Dolap;
import pojo.Ortam;

/**
 * FXML Controller class
 *
 * @author shest
 */
public class VeriController implements Initializable {
    @FXML
    private DatePicker datepicker;
    @FXML
    private Button listelebutonu;
    public static void id_yaz(int id){
        System.out.println(id);
    }
    
    public static LocalDate date;
    
    String text1 ="";
    String text2 ="";
    String text3 ="";
    String text4 ="";
    @FXML
    private TextArea textarea2;
    @FXML
    private TextArea textarea1;
    @FXML
    private Label labelOrtam;
    @FXML
    private AnchorPane veri_ekrani;
    @FXML
    private TextArea textarea3;
    @FXML
    private TextArea textarea4;
    @FXML
    private Label labeldolap;
    @FXML
    private AreaChart<Number, Number> grafik;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sayfa ilk açıldığında grafik sabit x koordinatlarının tanımlanması gerekiyor
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(24);
        xAxis.setTickUnit(1);
        
    }    
    //Kullanıcı listele butonuna tıkladığında seçili tarihe göre alttaki fonksiyon çalışıyor
    @FXML
    private void listele(MouseEvent event) {
        
        textarea1.setText("");
        textarea2.setText("");
        textarea3.setText("");
        textarea4.setText("");
        text1 = "";
        text2 = "";
        text3 = "";
        text4 = "";
        
        date = datepicker.getValue();
        
        grafik.getData().removeAll(grafik.getData());
        //Veri tabanı işlemleri için hibernate oturumu açılıyor
        Session session = HibernateUtil.getSessionFactory().openSession();
        //Grafik verilerinin tutulacağı dizi tanımlanıyor
        float[] dolap_grafik_verisi = new float[24];
        float[] ortam_grafik_verisi = new float[24];
        
        /*Fonksiyon boyunca çok fazla veri tabanı işlemi yapılacağı için donmaları 
        önlemek adına bütün bu işlemleri Thread'lere bölmemiz gerekiyor
        aşağıda her bir Threade göndeilecek Runneable nesneleri ve run() fonksiyonları tanımlanıyor*/
        
        
        Runnable run6 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 23; i++) {
            if (i<10) {
                Query dolap1 = session.createQuery("from Dolap e where e.id = "+FXMLDocumentController.id_no+" AND e.time like '%"+date.toString()+" 0"+i+"%'");
                dolap1.setMaxResults(1);
                List<Dolap> dolap_g = dolap1.list();
                for(Dolap d : dolap_g){
                        if (String.valueOf(d.getDerece()).length()>5) {
                            continue;
                        }
                        dolap_grafik_verisi[i] = d.getDerece();
                }        
            }
            else{
                Query dolap1 = session.createQuery("from Dolap e where e.id = "+FXMLDocumentController.id_no+" AND e.time like '%"+date.toString()+" "+i+"%'");
                dolap1.setMaxResults(1);
                List<Dolap> dolap_g = dolap1.list();
                for(Dolap d : dolap_g){
                        if (String.valueOf(d.getDerece()).length()>5) {
                            continue;
                        }
                        dolap_grafik_verisi[i] = d.getDerece();
                } 
            }
        }
        XYChart.Series seriesDolap= new XYChart.Series();
        seriesDolap.setName("Dolap");
        for (int i = 0; i < 24; i++) {
            seriesDolap.getData().add(new XYChart.Data(i+1, dolap_grafik_verisi[i]));
        }
        
        
        for (int i = 0; i < 23; i++) {
            if (i<10) {
                Query ortam1 = session.createQuery("from Ortam e where e.id = "+FXMLDocumentController.id_no+" AND e.time like '%"+date.toString()+" 0"+i+"%'");
                ortam1.setMaxResults(1);
                List<Ortam> ortam_g = ortam1.list();
                for(Ortam o : ortam_g){
                        if (String.valueOf(o.getDerece()).length()>5) {
                            continue;
                        }
                        ortam_grafik_verisi[i] = o.getDerece();
                }        
            }
            else{
                Query ortam1 = session.createQuery("from Ortam e where e.id = "+FXMLDocumentController.id_no+" AND e.time like '%"+date.toString()+" "+i+"%'");
                ortam1.setMaxResults(1);
                List<Ortam> ortam_g = ortam1.list();
                for(Ortam o : ortam_g){
                        if (String.valueOf(o.getDerece()).length()>5) {
                            continue;
                        }
                        ortam_grafik_verisi[i] = o.getDerece();
                } 
            }
        }
        XYChart.Series seriesOrtam= new XYChart.Series();
        seriesOrtam.setName("Ortam");
        for (int i = 0; i < 24; i++) {
            seriesOrtam.getData().add(new XYChart.Data(i+1, ortam_grafik_verisi[i]));
        }
        
        grafik.getData().addAll(seriesOrtam, seriesDolap);
            }
        };
        
        
        
        //Textarea'lara yazdırılacak veriler için HQL sorguları
        
        Query q = session.createQuery("from Ortam e where e.id = "+FXMLDocumentController.id_no+" AND e.time like ?");
        Query q2 = session.createQuery("from Ortam e where e.id = "+FXMLDocumentController.id_no+" AND e.alarm = 1 AND e.time like ?");
        Query q3 = session.createQuery("select count(*) from Ortam e where e.id = "+FXMLDocumentController.id_no+" AND e.alarm = 1 AND e.time like ?");
        Query q4 = session.createQuery("from Dolap e where e.id = "+FXMLDocumentController.id_no+" AND e.alarm = 1 AND e.time like ?");
        Query q5 = session.createQuery("from Dolap e where e.id = "+FXMLDocumentController.id_no+" AND e.time like ?");
        Query q6 = session.createQuery("select count(*) from Dolap e where e.id = "+FXMLDocumentController.id_no+" AND e.alarm = 1 AND e.time like ?");

        
        
        q.setString(0, "%"+date.toString()+"%");
        q2.setString(0, "%"+date.toString()+"%");
        q3.setString(0, "%"+date.toString()+"%");
        q4.setString(0, "%"+date.toString()+"%");
        q5.setString(0, "%"+date.toString()+"%");
        q6.setString(0, "%"+date.toString()+"%");
        
        
        //Textarea'lar için runneable nesneleri
        Runnable run5 = new Runnable() {
            @Override
            public void run() {
                
                Long ortam_alarm_sayisi = (Long)q3.uniqueResult();
                Long dolap_alarm_sayisi = (Long)q6.uniqueResult();

                int ortam_saniye = (int) (ortam_alarm_sayisi*15);
                int ortam_dakika = ortam_saniye/60;
                int ortam_saat = ortam_dakika/60;
                ortam_dakika = ortam_dakika%60;
                ortam_saniye = ortam_saniye%60;

                int dolap_saniye = (int) (dolap_alarm_sayisi*15);
                int dolap_dakika = dolap_saniye/60;
                int dolap_saat = dolap_dakika/60;
                dolap_dakika = dolap_dakika%60;
                dolap_saniye = dolap_saniye%60;

                String yazilacak_veri_dolap = String.format("Seçilen günde \n%d saat \n%d dakika \n%d saniye ortam alarmı oluştu.", dolap_saat, dolap_dakika, dolap_saniye);  

                String yazilacak_veri_ortam = String.format("Seçilen günde \n%d saat \n%d dakika \n%d saniye dolap alarmı oluştu.", ortam_saat, ortam_dakika, ortam_saniye);  

        Platform.runLater(() ->{
            labelOrtam.setText(yazilacak_veri_ortam);
            labeldolap.setText(yazilacak_veri_dolap); 
                });
            }
        };

        
        
        
        Runnable run = new Runnable() {
            @Override
            public void run() {
                
                    List<Dolap> list_dolap = q4.list();
                    
                    for(Dolap d : list_dolap){
                        if (String.valueOf(d.getDerece()).length()>5) {
                            continue;
                        }
                        //text3 = textarea3.getText();
                        text3 = text3 + d.getTime().toString().subSequence(0, 16)+"  ISI: "+d.getDerece()+"  ALARM AKTİF"+"\n";
                        
                    }  
                    textarea3.setText(text3);

            }
        };
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                
                    
                    List<Ortam> alarmListesi = q2.list();
                   
                    for(Ortam o : alarmListesi){
                        //text2 = textarea2.getText();
                        text2 = text2 + o.getTime().toString().subSequence(0, 16)+"  ISI: "+o.getDerece()+"  ALARM AKTİF"+"\n";
                         
                    } 
                    textarea2.setText(text2);

            }
        };
        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                
                    List<Dolap> list_dolap_alarm = q5.list();
                    
                    for(Dolap d : list_dolap_alarm){
                        if (String.valueOf(d.getDerece()).length()>5) {
                            continue;
                        }
                        //text4 = textarea4.getText();
                        text4 = text4 + d.getTime().toString().subSequence(0, 16)+"  ISI: "+d.getDerece()+"\n";
                        
                    }
                    textarea4.setText(text4);
                        
                        
            }
        };
        Runnable run4 = new Runnable() {
            @Override
            public void run() {
                
                   List<Ortam> list = q.list();
                   
                   for(Ortam o : list){
                        if (String.valueOf(o.getDerece()).length()>5) {
                            continue;
                        }
                        //text1 = textarea1.getText();
                        text1 = text1 + o.getTime().toString().subSequence(0, 16)+"  ISI: "+o.getDerece()+"\n";
                        
                    } 
                    textarea1.setText(text1);                
            }
        };
        
        //Yukarıda tanımlanan bütün runneable fonksiyonları aşağıdaki Thread'ler yardımı ile paralel bir şekilde çalıştırılıyor
        Thread t = new Thread(run4);
        Thread t2 = new Thread(run3);
        Thread t3 = new Thread(run2);
        Thread t4 = new Thread(run);
        Thread t5 = new Thread(run5);
        Thread t6 = new Thread(run6);
        t.run();
        t2.run();
        t3.run();
        t4.run();
        t5.run();
        t6.run();
        //Hibernate oturumu kapatılıyor
        session.close();
    }

    //Menubar'daki geri dön menu ıtem'ına tıklandığında kullanıcı giriş safyasına yönlendiriliyor
    @FXML
    private void geri_don(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        
        Parent root=loader.load();
        FXMLDocumentController secondController=loader.getController();
        Stage stage2 = new Stage();
        stage2 = (Stage) veri_ekrani.getScene().getWindow();
        stage2.close();
        Stage stage =new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    //Kullanıcı menubar'daki sil menu ıtem'ına tıkladığında aşağıdaki fonsiyon çalışıyor
    @FXML
    private void sil(ActionEvent event) {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Mevcut kullanıcı silinecek.");
        alert1.setHeaderText(null);
        alert1.setContentText("Emin misiniz ?");
        

        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK){
            Session session = HibernateUtil.getSessionFactory().openSession();
                try {
                    Query silme_HQL = session.createQuery("delete from users u  where u.id=2");
                    Transaction transaction = session.beginTransaction();
                    silme_HQL.executeUpdate();
                    transaction.commit();
                    session.close();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata -> İşlem başarılı bir şekilde tamamlanmadı !!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Geliştirici ile iletişime geçiniz...");
                    alert.showAndWait();
                }
        } else {
            
        }
                
                
                

    }


    
    
}
