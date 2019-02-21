package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.SubjectDAO;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
public class SubjectDB extends AbstractObject<Subject> implements SubjectDAO {

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
            subjects = query.list();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return subjects;
    }

    @Override
    public List<Subject> getSubjectsByGroupAndTeacher(int groupId, int teacherId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Subject> subjects = null;
        try {
            Query query = session.createQuery("SELECT s FROM Subject s "
                    + "join fetch s.subjectTeacherGroupList stg join fetch stg.teacher t join fetch stg.groupstud g "
                    + "WHERE t.teacherid = :teacherId AND g.groupid = :groupId");
            query.setParameter("teacherId", teacherId);
            query.setParameter("groupId", groupId);
            subjects = query.list();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return subjects;
    }

    @Override
    public List<Subject> getSubjectsByTeacher(int teacherId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Subject> subjects = null;
        try {
            Query query = session.createQuery("SELECT s FROM Subject s "
                    + "join fetch s.subjectTeacherGroupList stg join fetch stg.teacher t join fetch stg.groupstud g "
                    + "WHERE t.teacherid = :teacherId");
            query.setParameter("teacherId", teacherId);
          
            subjects = query.list();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return subjects;
    }

}
