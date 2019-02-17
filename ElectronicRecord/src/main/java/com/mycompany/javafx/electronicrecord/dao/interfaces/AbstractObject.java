/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

/**
 *
 * @author user07
 */
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author user07
 */
public abstract class AbstractObject<Entity> implements ObjectDAO<Entity> {

    @Override
    public void insert(Entity entity) {

        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(Entity entity) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void insertOrUpdate(Entity entity) {

        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    

}