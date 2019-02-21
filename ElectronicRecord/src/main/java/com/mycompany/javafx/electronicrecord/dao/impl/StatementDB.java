package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.StatementDAO;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Statement;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StatementDB extends AbstractObject<Statement> implements StatementDAO {

    private static StatementDB instance;

    public static StatementDB getInstance() {
        if (instance == null) {
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

    @Override
    public List<Statement> getStatementsByCriteria(String type,Subject subject, Groupstud groupstud,Date startDate, Date endDate,String typeUser,int teacherId) {
        
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Statement> statements = null;
        boolean isFirst = true;

        StringBuilder query = new StringBuilder("from Statement st join fetch st.subjectId s join fetch st.groupId g join fetch st.teacherId t ");

        if (type != null) {
            
            query = isFirst ? query.append("where st.type = '"+type+"' ") : query.append("and st.type = '"+type+"' ");
            
            isFirst = false;
        }
        if (subject != null) {
            query = isFirst ? query.append("where s.subjectId = "+subject.getSubjectId()+" ") : 
                              query.append("and s.subjectId = "+subject.getSubjectId()+" ");
            
            isFirst = false;
        }
        if (groupstud != null) {
            query = isFirst ? query.append("where g.groupid = "+groupstud.getGroupid()+" ") : 
                              query.append("and g.groupid = "+groupstud.getGroupid()+" ");
            
            isFirst = false;
        }
        if (startDate != null) {
            query = isFirst ? query.append("where st.date >= '"+simpleDateFormat.format(startDate.getTime())+"' ") : 
                              query.append("and st.date >= '"+simpleDateFormat.format(startDate.getTime())+"' ");
            
            isFirst = false;
        }
        if (endDate != null) {
            query = isFirst ? query.append("where st.date <= '" + simpleDateFormat.format(endDate.getTime()) +"' ") : 
                              query.append("and st.date <= '" +simpleDateFormat.format(endDate.getTime())+"' ");
            
            isFirst = false;
        }
        if(typeUser.equals("Teacher")){
            query = isFirst ? query.append("where t.teacherid = "+teacherId+" "):query.append("and t.teacherid = "+teacherId+" ");
            isFirst = false;
        }
        try {
            Query resultQuery = session.createQuery(query.toString());
            statements = resultQuery.list();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            session.close();
        }
        
        
        return statements;
    }
}
