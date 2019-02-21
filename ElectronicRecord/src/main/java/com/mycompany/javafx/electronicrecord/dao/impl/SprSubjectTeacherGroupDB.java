/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.SprSubjectTeacherGorupDAO;
import com.mycompany.javafx.electronicrecord.model.SprSubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user07
 */
public class SprSubjectTeacherGroupDB implements SprSubjectTeacherGorupDAO {

    private static SprSubjectTeacherGroupDB instance;

    public static SprSubjectTeacherGroupDB getInstance() {
        if (instance == null) {
            instance = new SprSubjectTeacherGroupDB();
        }
        return instance;
    }

    @Override
    public List<SprSubjectTeacherGroup> getSubjectAndTeacherByGroup(String name) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<SprSubjectTeacherGroup> subjectTeacherGroups = null;
        
        try {
            Query query = session.createQuery("SELECT s FROM SprSubjectTeacherGroup s WHERE s.groupname = :name");
            query.setParameter("name", name);
            subjectTeacherGroups = query.list();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            session.close();
        }
        return subjectTeacherGroups;
    }

  

}
