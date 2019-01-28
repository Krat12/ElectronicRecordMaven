package com.mycompany.javafx.electronicrecord.dao.impl;


import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.StudentDAO;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
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
            Query query = session.createQuery("from Student s join fetch s.user u join fetch s.group");
            students = query.getResultList();
        } finally {
            session.close();
        }
        return students;
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Student> students = null;
        try {
            Query query = session.createQuery("from Student s join fetch s.user u join fetch s.group g where g.groupName = :group");
            query.setParameter("group", group);
            students = query.getResultList();
        } finally {
            session.close();
        }
        return students;
    }

}
