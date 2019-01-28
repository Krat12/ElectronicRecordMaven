/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.dao.interfaces;

/**
 *
 * @author user07
 */
public interface ObjectDAO <Entity> {
    
    void insert(Entity entity);
    
    void update(Entity entity);
    
    void delete (Entity entity);
}