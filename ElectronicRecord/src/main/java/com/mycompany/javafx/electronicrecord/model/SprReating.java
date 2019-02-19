/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user07
 */
@Entity
@Table(name = "spr_reating")
@NamedQueries({
    @NamedQuery(name = "SprReating.findAll", query = "SELECT s FROM SprReating s")})
public class SprReating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "midleName")
    private String midleName;
    @Basic(optional = false)
    @Column(name = "Student_id")
    private int studentid;
    @Column(name = "mark")
    private Integer mark;
    @Basic(optional = false)
    @Column(name = "reating_id")
    @Id
    private int reatingId;
    @Basic(optional = false)
    @Column(name = "statement_id")
    private int statementId;
    @Column(name = "fullNameBoss")
    private String fullNameBoss;
    @Column(name = "placePracticle")
    private String placePracticle;
    @Column(name = "Thesis")
    private String thesis;

    public SprReating() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidleName() {
        return midleName;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public int getReatingId() {
        return reatingId;
    }

    public void setReatingId(int reatingId) {
        this.reatingId = reatingId;
    }

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

    public String getFullNameBoss() {
        return fullNameBoss;
    }

    public void setFullNameBoss(String fullNameBoss) {
        this.fullNameBoss = fullNameBoss;
    }

    public String getPlacePracticle() {
        return placePracticle;
    }

    public void setPlacePracticle(String placePracticle) {
        this.placePracticle = placePracticle;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }
    
}
