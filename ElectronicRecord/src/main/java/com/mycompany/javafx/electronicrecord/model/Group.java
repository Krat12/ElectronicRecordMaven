/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user07
 */
@Entity
@Table(name = "groupstud")
public class Group implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Group_id")
    private Integer groupId;
    @Column(name = "Group_name")
    private String groupName;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Student> studentList;
    @JoinColumn (name = "Speciality_id",referencedColumnName = "Speciality_id")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Speciality speciality;

    public Group() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.groupId);
        hash = 71 * hash + Objects.hashCode(this.groupName);
        hash = 71 * hash + Objects.hashCode(this.studentList);
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
        final Group other = (Group) obj;
        if (!Objects.equals(this.groupName, other.groupName)) {
            return false;
        }
        if (!Objects.equals(this.groupId, other.groupId)) {
            return false;
        }
        if (!Objects.equals(this.studentList, other.studentList)) {
            return false;
        }
        return true;
    }

    public Group(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the studentList
     */
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     * @param studentList the studentList to set
     */
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Group{" + "groupId=" + groupId + ", groupName=" + groupName + ", studentList=" + studentList + '}';
    }

    /**
     * @return the speciality
     */
    public Speciality getSpeciality() {
        return speciality;
    }

    /**
     * @param speciality the speciality to set
     */
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
