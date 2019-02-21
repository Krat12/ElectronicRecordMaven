package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.SubjectTeacherGroupDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class SubjectTeacherGroupDB extends AbstractObject<SubjectTeacherGroup> implements SubjectTeacherGroupDAO {

    private static SubjectTeacherGroupDB instance;

    public static SubjectTeacherGroupDB getInstance() {
        if (instance == null) {
            instance = new SubjectTeacherGroupDB();
        }
        return instance;
    }

    private SubjectTeacherGroupDB() {
    }


    @Override
    public void copyByInsert(int sourse, int targer) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        String sql = "INSERT INTO subject_teacher_group (group_id,subject_id,teacher_id,hours)\n"
                + "(SELECT " + targer + ", A.subject_id,A.teacher_id,A.hours FROM subject_teacher_group as A where A.group_id = " + sourse + ");";
        System.out.println(sql);
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(SubjectTeacherGroup.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteSubjectTeacherGroup(int id) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from SubjectTeacherGroup s where s.idsubjectTeacherGroup = " + id);
            query.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void updateHours(int id, int hours) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE SubjectTeacherGroup s SET s.hours = :hours WHERE  s.idsubjectTeacherGroup = :id");
            query.setParameter("hours", hours);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void insetSubjectTeacherGroup(int subjectId, int teacherId, int groupId, int hours,SubjectTeacherGroup stg) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            stg.setTeacher((Teacher)session.load(Teacher.class, teacherId));
            stg.setGroupstud((Groupstud)session.load(Groupstud.class, groupId));
            stg.setSubjectId((Subject)session.load(Subject.class, subjectId));
            stg.setHours(hours);
            session.save(stg);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
