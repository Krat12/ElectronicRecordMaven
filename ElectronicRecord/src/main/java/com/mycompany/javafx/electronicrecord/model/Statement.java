package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "statement")
public class Statement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statement_id")
    private Integer statementId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "type")
    private String type;
    @Column(name = "hours")
    private Integer hours;
    @OneToMany(mappedBy = "statementId", fetch = FetchType.LAZY)
    private List<Reating> reatingList;
    @JoinColumn(name = "group_id", referencedColumnName = "Group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groupstud groupId;
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subjectId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "Teacher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacherId;

    public Statement() {
    }

    public Statement(Integer statementId) {
        this.statementId = statementId;
    }

    public Integer getStatementId() {
        return statementId;
    }

    public void setStatementId(Integer statementId) {
        this.statementId = statementId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public List<Reating> getReatingList() {
        return reatingList;
    }

    public void setReatingList(List<Reating> reatingList) {
        this.reatingList = reatingList;
    }

    public Groupstud getGroupId() {
        return groupId;
    }

    public void setGroupId(Groupstud groupId) {
        this.groupId = groupId;
    }

    public Subject getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subject subjectId) {
        this.subjectId = subjectId;
    }

    public Teacher getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statementId != null ? statementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statement)) {
            return false;
        }
        Statement other = (Statement) object;
        if ((this.statementId == null && other.statementId != null) || (this.statementId != null && !this.statementId.equals(other.statementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Statement[ statementId=" + statementId + " ]";
    }

}
