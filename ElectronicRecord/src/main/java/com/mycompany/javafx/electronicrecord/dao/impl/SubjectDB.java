package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.SubjectDAO;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SubjectDB extends AbstractObject<Subject> implements SubjectDAO{

    private static SubjectDB instance;

    public static SubjectDB getInstance() {
        if (instance == null) {
            instance = new SubjectDB();
        }
        return instance;
    }

    private SubjectDB() {
    }

    @Override
    public List<Subject> getAllSubjects() {
       Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Subject> subjects = null;
        try {
            Query query = session.createQuery("from Subject s ORDER BY s.nameSubject");
            subjects = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return subjects;
    }

}
