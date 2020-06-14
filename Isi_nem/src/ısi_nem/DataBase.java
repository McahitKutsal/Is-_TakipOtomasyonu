/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ısi_nem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojo.NewHibernateUtil;

/**
 *
 * @author shest
 */
public class DataBase {
    private static SessionFactory SesssionFactory;
    
    public static void connect(){
        SesssionFactory = NewHibernateUtil.getSessionFactory();
    }
    public static void disconnect(){
        SesssionFactory.close();
    }
    public static class users{
    
        public static ObservableList<pojo.Users> getUsers(){
        
        
            Session session = SesssionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObservableList<pojo.Users> users = FXCollections.observableArrayList(session.createSQLQuery("SELECT* FROM users").addEntity(pojo.Users.class).list());
            transaction.commit();
            session.close();
            return users;
            
        }
        
    }
    public static class Ortam{
    
        public static ObservableList<pojo.Ortam> getOrtam(){
        
        
            Session session = SesssionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObservableList<pojo.Ortam> ortam = FXCollections.observableArrayList(session.createSQLQuery("SELECT* FROM Ortam").addEntity(pojo.Users.class).list());
            transaction.commit();
            session.close();
            return ortam;
            
        }    
    }
    public static class Dolap{
    
        public static ObservableList<pojo.Dolap> getDolap(){
        
        
            Session session = SesssionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObservableList<pojo.Dolap> dolap = FXCollections.observableArrayList(session.createSQLQuery("SELECT* FROM Dolap").addEntity(pojo.Users.class).list());
            transaction.commit();
            session.close();
            return dolap;
            
        }    
    } 
}
