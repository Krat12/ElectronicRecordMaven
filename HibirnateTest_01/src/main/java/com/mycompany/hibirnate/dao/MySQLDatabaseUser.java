package com.mycompany.hibirnate.dao;

import com.mycompany.hibirnate.model.User;
import com.mycompany.hibirnate.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class MySQLDatabaseUser implements com.mycompany.hibirnate.dao.DAO<User, String>{

    @Override
    public boolean insert(User user) {
        
         Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            
        } catch (Exception e) {
            System.out.println("Exeption" + e);
            return false;
        }finally{
            session.close();
        }
        
        return true;
    }

    @Override
    public boolean update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getObjectByID(String email) {
        return HibernateSessionFactoryUtill.getSessionFactory().openSession().get(User.class, email);
    }

    @Override
    public List<User> getAllObject() {
        List<User> users = HibernateSessionFactoryUtill.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

}
