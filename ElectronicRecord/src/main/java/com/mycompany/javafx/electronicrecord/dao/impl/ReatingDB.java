package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.ReatingDAO;
import com.mycompany.javafx.electronicrecord.model.Coursework;
import com.mycompany.javafx.electronicrecord.model.Diplom;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ReatingDB extends AbstractObject<Reating> implements ReatingDAO {

    private static ReatingDB instance;

    public static ReatingDB getInstance() {
        if (instance == null) {
            instance = new ReatingDB();
        }
        return instance;
    }

    private ReatingDB() {
    }

    @Override
    public void insertBySelect(int statementId, int groupId) {

        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        Transaction transaction = null;
        String sql = "INSERT INTO reating (statement_id,Student_id)\n"
                + "(SELECT " + statementId + " as statement_id, A.Student_id FROM student as A where A.Group_id = " + groupId + ");";
      
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(Reating.class);
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
    public List<Reating> getReatingByStatement(int statementId) {
        Session session = HibernateSessionFactoryUtill.getSessionFactory().openSession();
        List<Reating> reatings = new ArrayList();
        try {
            Query query = session.createQuery("SELECT u.name,u.surname,u.midleName,s.studentid,r.mark,r.reatingId "
                    + "FROM Reating r JOIN r.studentid s JOIN r.statementId st JOIN s.user u "
                    + "WHERE st.statementId = :statementId ORDER BY u.surname"); 
            
            query.setParameter("statementId", statementId);
            List<Object> objects = query.list();
            Iterator itr = objects.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();

                User user = new User();
                user.setName(String.valueOf(obj[0]));
                user.setMidleName(String.valueOf(obj[2]));
                user.setSurname(String.valueOf(obj[1]));
               

                Student student = new Student(Integer.valueOf(String.valueOf(obj[3])));
                student.setUser(user);

                Reating reating = new Reating(Integer.valueOf(String.valueOf(obj[5])));
                if(obj[4] != null){
                     reating.setMark(Integer.valueOf(String.valueOf(obj[4])));
                }
                reating.setStudentid(student);

                reatings.add(reating);
            }
         
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            session.close();
        }
        return reatings;
    }

}
