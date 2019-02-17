/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

import com.mycompany.javafx.electronicrecord.model.Groupstud;
import java.util.List;

/**
 *
 * @author user07
 */
public interface GroupDAO extends ObjectDAO<Groupstud>{
    
    List<Groupstud> getAllGroups();
    
    List<Groupstud> getSortGroupstudsByNameGroup(String name);
    
    boolean deleteGroup (int id);
    
    Groupstud getGroupstudsByName(String name);
    
     List<Groupstud> getGroupsExceptForTarget(int groupId);
     
     List<Groupstud> getGroupstudsByTeacher(int teacherId);
}
