package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.GroupDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class GroupDB extends AbstractObject<Groupstud> implements GroupDAO{

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

    
    
}
