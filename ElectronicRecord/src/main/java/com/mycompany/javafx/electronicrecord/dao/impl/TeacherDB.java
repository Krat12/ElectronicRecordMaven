package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.TeacherDAO;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TeacherDB extends AbstractObject<Teacher> implements TeacherDAO{

    private static TeacherDB instance;

    public static TeacherDB getInstance() {

        if (instance == null) {
            instance = new TeacherDB();
        }
        return instance;
    }

    private TeacherDB() {
    }

    @Override
    public Teacher getTeacherById(Integer Id) {
       return null;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Teacher> teachers = new ArrayList<>();
        try {
            Query query = session.createQuery("SELECT t.teacherid,u.name,u.midleName,u.surname FROM Teacher t join t.user u ORDER BY u.surname");
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                
                User user = new User();
                user.setName(String.valueOf(obj[1]));
                user.setMidleName(String.valueOf(obj[2]));
                user.setSurname(String.valueOf(obj[3]));

                Teacher teacher = new Teacher(Integer.valueOf(String.valueOf(obj[0])));
                teacher.setUser(user);
                teachers.add(teacher);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return teachers;
    }
    
    

}
