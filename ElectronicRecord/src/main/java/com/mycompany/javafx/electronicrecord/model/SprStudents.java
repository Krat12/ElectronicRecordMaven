package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "spr_students")
@NamedQueries({
    @NamedQuery(name = "SprStudents.findAll", query = "SELECT s FROM SprStudents s")})
public class SprStudents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "midleName")
    private String midleName;
    @Column(name = "teacher_id")
    private int teacherId;
    @Basic(optional = false)
    @Column(name = "Student_id")
    @Id
    private int studentid;

    public SprStudents() {
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

    @Override
    public String toString() {
        return "SprStudents{" + "surname=" + surname + ", name=" + name + ", midleName=" + midleName + ", studentid=" + studentid + '}';
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    

}
