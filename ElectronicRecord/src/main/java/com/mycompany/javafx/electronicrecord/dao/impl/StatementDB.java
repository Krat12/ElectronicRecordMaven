package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.StatementDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Statement;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StatementDB extends AbstractObject<Statement> implements StatementDAO {

    private static StatementDB instance;
    
    public static StatementDB getInstance(){
        if(instance == null){
            instance = new StatementDB();
        }
        return instance;
    }

    private StatementDB() {
    }
    
    @Override
    public void insertById(int subjectId, int teacherId, int groupId, Statement statement) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            statement.setTeacherId((Teacher) session.load(Teacher.class, teacherId));
            statement.setGroupId((Groupstud) session.load(Groupstud.class, groupId));
            statement.setSubjectId((Subject) session.load(Subject.class, subjectId));
            session.save(statement);
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
