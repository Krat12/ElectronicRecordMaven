package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.GroupDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Speciality;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.ArrayList;
import java.util.Iterator;
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

    private GroupDB() {
    }

    @Override
    public List<Groupstud> getAllGroups() {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Groupstud> groups = null;
        try {
            Query query = session.createQuery("from Groupstud g join fetch g.specialityId ORDER BY g.groupname");
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
            query.setParameter("name", name);
            group = (Groupstud) query.uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return group;
    }

    @Override
    public List<Groupstud> getGroupsExceptForTarget(int groupId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Groupstud> groups = null;
        try {
            Query query = session.createQuery("from Groupstud g join fetch g.specialityId where g.groupid <> :target ORDER BY g.groupname");
            query.setParameter("target", groupId);
            groups = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return groups;
    }

    @Override
    public List<Groupstud> getGroupstudsByTeacher(int teacherId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        String sql = "SELECT g.Group_name,g.setYear,g.Group_id,s.nameSpeciality FROM groupstud g "
                + "INNER JOIN speciality s "
                + "     ON s.Speciality_id = g.speciality_id\n"
                + "WHERE g.Group_id  IN \n"
                + "(SELECT sub.group_id FROM subject_teacher_group sub WHERE sub.teacher_id = " + teacherId + ") GROUP BY g.Group_name;";
        List<Groupstud> groupstuds = new ArrayList<>();
        try {
            Query query = session.createSQLQuery(sql);
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                Speciality speciality = new Speciality();
                speciality.setNameSpeciality(String.valueOf(obj[3]));
                
                Groupstud groupstud = new Groupstud(Integer.valueOf(String.valueOf(obj[2])));
                groupstud.setSetYear(Short.valueOf(String.valueOf(obj[1])));
                groupstud.setGroupname(String.valueOf(obj[0]));
                groupstud.setSpecialityId(speciality);
                
                groupstuds.add(groupstud);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return groupstuds;
    }
}
