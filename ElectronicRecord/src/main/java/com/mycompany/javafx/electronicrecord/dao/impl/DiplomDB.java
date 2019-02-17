package com.mycompany.javafx.electronicrecord.dao.impl;

import com.mycompany.javafx.electronicrecord.dao.interfaces.AbstractObject;
import com.mycompany.javafx.electronicrecord.dao.interfaces.DiplomDAO;
import com.mycompany.javafx.electronicrecord.model.Diplom;



public class DiplomDB extends AbstractObject <Diplom> implements DiplomDAO {

    private static DiplomDB instance;

    public static DiplomDB getInstance() {
        if (instance == null) {
            instance = new DiplomDB();
        }
        return instance;
    }

    
    private DiplomDB() {
        
    }
    

}
