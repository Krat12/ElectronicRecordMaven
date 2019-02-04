/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.SpecialityDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Speciality;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Студент
 */
public class SpecialityDB extends AbstractObject<Speciality> implements SpecialityDAO{
    
    private static SpecialityDB instance;

    private SpecialityDB() {
    }
    
    public static SpecialityDB getInstance(){
        if(instance == null){
            instance = new SpecialityDB();
        }
        return instance;
    }

    @Override
    public List<Speciality> getAllSpecialitys() {
       Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Speciality> specialitys = null;
        try {
            Query query = session.createQuery("SELECT s FROM Speciality AS s");
            specialitys = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return specialitys;
    }

    @Override
    public Speciality getSpecialityByName(String name) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Speciality speciality = null;
        try {
            Query query = session.createQuery("SELECT s FROM Speciality AS s WHERE nameSpeciality = :name");
            query.setParameter("name", name);
            speciality = (Speciality) query.uniqueResult();
            System.out.println(speciality.getNameSpeciality());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return speciality;
    }
    
 
    
    
    
}
