/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.UserDAO;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user07
 */
public class UserDB extends AbstractObject<User> implements UserDAO {

    private static UserDB instance;

    public static UserDB getInstance() {

        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    private UserDB() {
    }
    

    @Override
    public User getUserByLoginAndPassword(String login,String password) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        User user = null;
        
        try {
            Query query = session.createQuery("SELECT u FROM User u LEFT OUTER JOIN FETCH u.teacher LEFT OUTER JOIN FETCH u.admin LEFT OUTER JOIN FETCH u.student WHERE u.login = :login and u.password = :password");
            query.setParameter("login",login);
            query.setParameter("password", password);
            user = (User) query.uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            session.close();
        }
        return user;
        
        
    }

}
