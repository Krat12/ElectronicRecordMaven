/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;

/**
 *
 * @author Admin
 */
public interface SubjectTeacherGroupDAO extends ObjectDAO<SubjectTeacherGroup>{
    
    void copyByInsert(int sourse, int targer);
    
    void deleteSubjectTeacherGroup(int id);
    
    void updateHours(int id, int hours);
    
    void insetSubjectTeacherGroup(int subjectId,int teacherId,int groupId, int hours,SubjectTeacherGroup stg);
    
    
}
