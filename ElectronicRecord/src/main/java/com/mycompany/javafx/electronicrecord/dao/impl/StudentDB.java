package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.StudentDAO;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class StudentDB extends AbstractObject<Student> implements StudentDAO {

    private static StudentDB instance;

    public static StudentDB getInstance() {

        if (instance == null) {
            instance = new StudentDB();
        }
        return instance;
    }

    @Override
    public Student getStudentById(Integer id) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Student student = null;

        try {
            Query query = session.createQuery("from Student s where s.studentId = :login");
            query.setParameter("login", id);
            student = (Student) query.uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Student> students = null;
        try {
            Query query = session.createQuery("from Student s join fetch s.user u join fetch s.groupid g join fetch g.specialityId");
            students = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return students;
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Student> students =  new ArrayList<Student>();
        try {
            Query query = session.createQuery("SELECT s.studentid,u.name,u.midleName,u.surname,s.numberBook,u.login,u.password "
                    + "FROM Student s join s.user u join s.groupid g where g.groupname = :group ORDER BY u.surname");
            query.setParameter("group", group);
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                
                User user = new User();
                user.setName(String.valueOf(obj[1]));
                user.setMidleName(String.valueOf(obj[2]));
                user.setSurname(String.valueOf(obj[3]));
                user.setLogin(String.valueOf(obj[5]));
                user.setPassword(String.valueOf(obj[6]));
                
                Student student = new Student(Integer.valueOf(String.valueOf(obj[0])));
                student.setNumberBook(Integer.valueOf(String.valueOf(obj[4])));
                student.setUser(user);
                
                students.add(student);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return students;
    }

    @Override
    public List<Student> getStudentsByNameAndGroup(String name,String group) {
         Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Student> students = new ArrayList<>();

        try {
            Query query = session.createQuery("SELECT s.studentid,u.name,u.midleName,u.surname,s.numberBook,u.login,u.password "
                    + "FROM Student s join s.user u join s.groupid g "
                    + "where g.groupname = :group "
                    + "AND CONCAT(u.surname,' ',u.name,' ',u.midleName) LIKE CONCAT('%', :name ,'%')" );
            query.setParameter("name",name);
            query.setParameter("group", group);
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                
                User user = new User();
                user.setName(String.valueOf(obj[1]));
                user.setMidleName(String.valueOf(obj[2]));
                user.setSurname(String.valueOf(obj[3]));
                user.setLogin(String.valueOf(obj[5]));
                user.setPassword(String.valueOf(obj[6]));
                
                Student student = new Student(Integer.valueOf(String.valueOf(obj[0])));
                student.setNumberBook(Integer.valueOf(String.valueOf(obj[4])));
                student.setUser(user);
                
                students.add(student);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return students;
    }

}
