package pojo;
// Generated 02.May.2020 03:58:38 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Users generated by hbm2java
 */
public class Users  implements java.io.Serializable {

//     @Id @GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
     private String username;
     private String password;
     private Date time;
     private String mail;
     private String tel;
     private int sms;

    public Users() {
    }

    public Users(String username, String password, Date time, String mail, String tel, int sms) {
       this.username = username;
       this.password = password;
       this.time = time;
       this.mail = mail;
       this.tel = tel;
       this.sms = sms;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    public String getMail() {
        return this.mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    public int getSms() {
        return this.sms;
    }
    
    public void setSms(int sms) {
        this.sms = sms;
    }




}

