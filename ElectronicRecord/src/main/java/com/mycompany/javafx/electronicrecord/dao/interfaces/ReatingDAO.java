/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

import com.mycompany.javafx.electronicrecord.model.Reating;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ReatingDAO extends ObjectDAO<Reating>{
    
    void insertBySelect(int statementId, int groupId);
    
    List<Reating> getReatingByStatement(int statementId);
    
    List<Reating> getReatingByDiplom(int statementId);
    
    List<Reating> getReatingByCoursework(int statementId);
    
}