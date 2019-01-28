/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.utill;

import com.mycompany.javafx.electronicrecord.model.Group;
import com.mycompany.javafx.electronicrecord.model.Speciality;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 *
 * @author user07
 */
public class HibernateSessionFactoryUtill {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Teacher.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Group.class);
            configuration.addAnnotatedClass(Speciality.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }
    
}
