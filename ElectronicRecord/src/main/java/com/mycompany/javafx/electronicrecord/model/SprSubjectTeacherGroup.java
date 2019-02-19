/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 *
 * @author user07
 */
@Entity
@Table(name = "sprsubjectteachergroup")
@Immutable
public class SprSubjectTeacherGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "subject_id")
    private Integer subjectId;
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Column(name = "hours")
    private Integer hours;
    @Column(name = "surname")
    private String surname;
    @Column(name = "midleName")
    private String midleName;
    @Column(name = "name")
    private String name;
    @Column(name = "name_subject")
    private String nameSubject;
    @Column(name = "Group_name")
    private String groupname;
    @Column(name = "setYear")
    private Short setYear;
    @Basic(optional = false)
    @Column(name = "idsubject_teacher_group")
    @Id
    private int idsubjectTeacherGroup;

    public SprSubjectTeacherGroup() {
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMidleName() {
        return midleName;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Short getSetYear() {
        return setYear;
    }

    public void setSetYear(Short setYear) {
        this.setYear = setYear;
    }

    public int getIdsubjectTeacherGroup() {
        return idsubjectTeacherGroup;
    }

    public void setIdsubjectTeacherGroup(int idsubjectTeacherGroup) {
        this.idsubjectTeacherGroup = idsubjectTeacherGroup;
    }

    @Override
    public String toString() {
        return "SprSubjectTeacherGroup{" + "groupId=" + groupId + ", subjectId=" + subjectId + ", teacherId=" + teacherId + ", hours=" + hours + ", surname=" + surname + ", midleName=" + midleName + ", name=" + name + ", nameSubject=" + nameSubject + ", groupname=" + groupname + ", setYear=" + setYear + ", idsubjectTeacherGroup=" + idsubjectTeacherGroup + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.groupId);
        hash = 17 * hash + Objects.hashCode(this.subjectId);
        hash = 17 * hash + Objects.hashCode(this.teacherId);
        hash = 17 * hash + Objects.hashCode(this.hours);
        hash = 17 * hash + Objects.hashCode(this.surname);
        hash = 17 * hash + Objects.hashCode(this.midleName);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.nameSubject);
        hash = 17 * hash + Objects.hashCode(this.groupname);
        hash = 17 * hash + Objects.hashCode(this.setYear);
        hash = 17 * hash + this.idsubjectTeacherGroup;
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
        final SprSubjectTeacherGroup other = (SprSubjectTeacherGroup) obj;
        if (this.idsubjectTeacherGroup != other.idsubjectTeacherGroup) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.midleName, other.midleName)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.nameSubject, other.nameSubject)) {
            return false;
        }
        if (!Objects.equals(this.groupname, other.groupname)) {
            return false;
        }
        if (!Objects.equals(this.groupId, other.groupId)) {
            return false;
        }
        if (!Objects.equals(this.subjectId, other.subjectId)) {
            return false;
        }
        if (!Objects.equals(this.teacherId, other.teacherId)) {
            return false;
        }
        if (!Objects.equals(this.hours, other.hours)) {
            return false;
        }
        if (!Objects.equals(this.setYear, other.setYear)) {
            return false;
        }
        return true;
    }
    
    
}
