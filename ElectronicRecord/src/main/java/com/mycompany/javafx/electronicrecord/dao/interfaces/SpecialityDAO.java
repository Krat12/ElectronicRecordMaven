/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

import com.mycompany.javafx.electronicrecord.model.Speciality;
import java.util.List;

/**
 *
 * @author Студент
 */
public interface SpecialityDAO extends ObjectDAO<Speciality>{
    
    List<Speciality> getAllSpecialitys();
    
    Speciality getSpecialityByName(String name);
    
}
