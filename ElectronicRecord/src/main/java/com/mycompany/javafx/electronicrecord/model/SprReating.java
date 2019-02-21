package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "spr_reting")
public class SprReating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "reating_1_4_0_")
    @Id
    private int reatingId;
    @Column(name = "mark2_4_0_")
    private Integer mark;
    @Basic(optional = false)
    @Column(name = "Student_4_4_0_")
    private int studentid;
    @Column(name = "type4_9_1_")
    private String type;
    @Column(name = "name_sub2_11_2_")
    private String nameSubject;
    @Column(name = "Thesis")
    private String thesis;
    @Column(name = "fullNameBoss")
    private String fullNameBoss;
    @Column(name = "placePracticle")
    private String placePracticle;
    @Column(name = "midleName")
    private String midleName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "statement_id")
    private int statementId;

    public SprReating() {
    }

    public int getReatingId() {
        return reatingId;
    }

    public void setReatingId(int reating) {
        this.reatingId = reating;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }


    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int student) {
        this.studentid = student;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
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

    public String getMidleName() {
        return midleName;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
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

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

}
