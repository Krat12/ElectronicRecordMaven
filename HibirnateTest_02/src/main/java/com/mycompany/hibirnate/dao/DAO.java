/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibirnate.dao;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface DAO <Entity,Key> {
    
    boolean insert(Entity model);

    boolean update(Entity model);

    boolean delete(Entity model);

    Entity getObjectByID(Key key);

    List<Entity> getAllObject();
}
