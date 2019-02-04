package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.GroupDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class GroupDB extends AbstractObject<Groupstud> implements GroupDAO {

    private static GroupDB instance;

    public static GroupDB getInstance() {

        if (instance == null) {
            instance = new GroupDB();
        }
        return instance;
    }

    @Override
    public List<Groupstud> getAllGroups() {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Groupstud> groups = null;
        try {
            Query query = session.createQuery("from Groupstud g join fetch g.specialityId");
            groups = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return groups;
    }

    @Override
    public List<Groupstud> getSortGroupstudsByNameGroup(String name) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Groupstud> groups = null;
        try {
            Query query = session.createQuery("from Groupstud g join fetch g.specialityId where g.groupname LIKE CONCAT('%',:name,'%')");
            query.setParameter("name", name);
            groups = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return groups;
    }

    @Override
    public boolean deleteGroup(int id) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Groupstud g where g.groupid = " + id);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Exeption " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

    }

    @Override
    public Groupstud getGroupstudsByName(String name) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Groupstud group = null;
        try {
            Query query = session.createQuery("from Groupstud g join fetch g.specialityId where g.groupname = :name");
            query.setParameter("name",name);
            group = (Groupstud) query.uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return group;
    }
}
