/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author user07
 */
@Entity
@Table(name = "student")
public class Student implements Serializable{
    
    @Id
    @Column(name = "Student_id")
    private Integer studentId;
    @Column(name = "number_book")
    private Integer numberBook;
    private Integer course;
    @JoinColumn(name = "Student_id",referencedColumnName = "user_id",insertable = false,unique = false)
    @OneToOne(optional = false,fetch = FetchType.LAZY)
    private User user;
    @JoinColumn (name = "Group_id",referencedColumnName = "Group_id")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Group group;
    
    
    
    

    public Student() {
    }

    public Student(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the numberBook
     */
    public Integer getNumberBook() {
        return numberBook;
    }

    /**
     * @param numberBook the numberBook to set
     */
    public void setNumberBook(Integer numberBook) {
        this.numberBook = numberBook;
    }

    /**
     * @return the course
     */
    public Integer getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Integer course) {
        this.course = course;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }




    @Override
    public String toString() {
        return "Student{" + "studentId=" + studentId + ", numberBook=" + numberBook + ", course=" + course + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.studentId);
        hash = 37 * hash + Objects.hashCode(this.numberBook);
        hash = 37 * hash + Objects.hashCode(this.course);
        hash = 37 * hash + Objects.hashCode(this.user);
        hash = 37 * hash + Objects.hashCode(this.group);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.studentId, other.studentId)) {
            return false;
        }
        if (!Objects.equals(this.numberBook, other.numberBook)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.group, other.group)) {
            return false;
        }
        return true;
    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
    
}
