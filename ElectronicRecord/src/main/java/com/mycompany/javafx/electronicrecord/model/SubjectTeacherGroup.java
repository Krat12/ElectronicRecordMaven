package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subject_teacher_group")
public class SubjectTeacherGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubject_teacher_group")
    private Integer idsubjectTeacherGroup;
    @Column(name = "hours")
    private Integer hours;
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subjectId;
    @JoinColumn(name = "Teacher_id", referencedColumnName = "teacher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    @JoinColumn(name = "Group_id", referencedColumnName = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groupstud groupstud;
    
    

    public SubjectTeacherGroup() {
    }

    public SubjectTeacherGroup(Integer idsubjectTeacherGroup) {
        this.idsubjectTeacherGroup = idsubjectTeacherGroup;
    }

    public Integer getIdsubjectTeacherGroup() {
        return idsubjectTeacherGroup;
    }

    public void setIdsubjectTeacherGroup(Integer idsubjectTeacherGroup) {
        this.idsubjectTeacherGroup = idsubjectTeacherGroup;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Subject getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subject subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsubjectTeacherGroup != null ? idsubjectTeacherGroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectTeacherGroup)) {
            return false;
        }
        SubjectTeacherGroup other = (SubjectTeacherGroup) object;
        if ((this.idsubjectTeacherGroup == null && other.idsubjectTeacherGroup != null) || (this.idsubjectTeacherGroup != null && !this.idsubjectTeacherGroup.equals(other.idsubjectTeacherGroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup[ idsubjectTeacherGroup=" + idsubjectTeacherGroup + " ]";
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Groupstud getGroupstud() {
        return groupstud;
    }

    public void setGroupstud(Groupstud groupstud) {
        this.groupstud = groupstud;
    }

}
