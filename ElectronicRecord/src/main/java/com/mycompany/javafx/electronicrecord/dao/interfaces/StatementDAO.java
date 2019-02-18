/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

import com.mycompany.javafx.electronicrecord.model.Statement;

/**
 *
 * @author Admin
 */
public interface StatementDAO extends ObjectDAO<Statement>{
    
    void insertById(int subjectId,int teacherId,int groupId,Statement statement);
    
    
    
}
