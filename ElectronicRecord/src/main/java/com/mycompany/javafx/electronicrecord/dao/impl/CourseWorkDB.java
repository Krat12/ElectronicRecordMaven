package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.CourseWorkDAO;
import com.mycompany.javafx.electronicrecord.model.Coursework;


public class CourseWorkDB extends AbstractObject<Coursework> implements CourseWorkDAO{
    
    private static CourseWorkDB insatance;
    
    public static CourseWorkDB getInstance(){
        if(insatance == null){
            insatance = new CourseWorkDB();
        }
        return insatance;
    }

    private CourseWorkDB() {
    }
    
    
}
