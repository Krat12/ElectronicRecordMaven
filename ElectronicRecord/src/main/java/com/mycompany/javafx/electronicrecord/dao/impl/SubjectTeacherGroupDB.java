package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.SubjectTeacherGroupDAO;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class SubjectTeacherGroupDB extends AbstractObject<SubjectTeacherGroup> implements SubjectTeacherGroupDAO{

    private static SubjectTeacherGroupDB instance;

    public static SubjectTeacherGroupDB getInstance(){
        if(instance == null){
             instance = new SubjectTeacherGroupDB();
        }
        return instance;
    }
    
    private SubjectTeacherGroupDB() {
    }
    
    
    
    @Override
    public List<SubjectTeacherGroup> getSubjectAndTeacherByGroup(String name) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<SubjectTeacherGroup> subjectTeacherGroups = new ArrayList<>();
        try {
            Query query = session.createQuery("SELECT sub.hours,u.name,u.surname,u.midleName,s.nameSubject,"
                    + "sub.idsubjectTeacherGroup FROM SubjectTeacherGroup sub "
                    + "join sub.groupstud g join sub.teacher t join t.user u join sub.subjectId s where g.groupname = :name");
            query.setParameter("name", name);
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                
                User user = new User();
                user.setName(String.valueOf(obj[1]));
                user.setSurname(String.valueOf(obj[2]));
                user.setMidleName(String.valueOf(obj[3]));
                
                Teacher teacher = new Teacher();
                teacher.setUser(user);
                
                Subject subject = new Subject();
                subject.setNameSubject(String.valueOf(obj[4]));
                
                
                SubjectTeacherGroup stg = new SubjectTeacherGroup();
                stg.setHours(Integer.valueOf(String.valueOf(obj[0])));
                stg.setTeacher(teacher);
                stg.setSubjectId(subject);
                stg.setIdsubjectTeacherGroup(Integer.valueOf(String.valueOf(obj[5])));
                
                subjectTeacherGroups.add(stg);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return subjectTeacherGroups;
    }

    @Override
    public void copyByInsert(int sourse, int targer) {
       Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        String sql = "INSERT INTO subject_teacher_group (group_id,subject_id,teacher_id,hours)\n" +
        "(SELECT "+targer+", A.subject_id,A.teacher_id,A.hours FROM subject_teacher_group as A where A.group_id = "+sourse+");";
        System.out.println(sql);
        try {
            transaction = session.beginTransaction();
            System.out.println("OK");
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
    public void updateHours(int id,int hours) {
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
    

}
