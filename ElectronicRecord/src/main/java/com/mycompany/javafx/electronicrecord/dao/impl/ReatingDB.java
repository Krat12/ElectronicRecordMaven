package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.ReatingDAO;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.model.SprReating;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ReatingDB extends AbstractObject<Reating> implements ReatingDAO {

    private static ReatingDB instance;

    public static ReatingDB getInstance() {
        if (instance == null) {
            instance = new ReatingDB();
        }
        return instance;
    }

    private ReatingDB() {
    }

    @Override
    public void insertBySelect(int statementId, int groupId) {

        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        String sql = "INSERT INTO reating (statement_id,Student_id)\n"
                + "(SELECT " + statementId + " as statement_id, A.Student_id FROM student as A where A.Group_id = " + groupId + ");";

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(Reating.class);
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
    public List<SprReating> getReatingByStatement(int statementId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<SprReating> reatings = null;
        try {
            Query query = session.createQuery("SELECT sr FROM SprReating sr WHERE sr.statementId = :statementId ORDER BY sr.surname");
            query.setParameter("statementId", statementId);
            reatings = query.list();
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            session.close();
        }
        return reatings;
    }

    @Override
    public List<SprReating> getReatingsByStudentId(int studentId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<SprReating> reatings = null;
        try {
            Query query = session.createQuery("SELECT sr FROM SprReating sr WHERE sr.studentid = :studentId");
            query.setParameter("studentId", studentId);
            reatings = query.list();
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            session.close();
        }
        return reatings;
    }

    @Override
    public int insertReatinBySelect(int statementId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        int result = 0;
        String sql = "Insert into reating (Student_id,statement_id) select s.Student_id, " + statementId + " as statement_id from student s where "
                + "s.Student_id not in (select r.Student_id from reating r where r.statement_id = " + statementId + ")";

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(Reating.class);
            result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<SprReating> getReatingsByNameStudent(String name,int statementId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<SprReating> reatings = null;
        try {
            Query query = session.createQuery("SELECT sr FROM SprReating sr WHERE sr.statementId = :statementId and CONCAT(sr.surname,' ',sr.name,' ',sr.midleName) LIKE CONCAT('%', :name ,'%')");
            query.setParameter("name", name);
            query.setParameter("statementId", statementId);
            reatings = query.list();
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            session.close();
        }
        return reatings;
       
    }

}
