package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "reating")
@NamedQueries({
    @NamedQuery(name = "Reating.findAll", query = "SELECT r FROM Reating r")})
public class Reating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reating_id")
    private Integer reatingId;
    @Column(name = "mark")
    private Integer mark;
    @JoinColumn(name = "statement_id", referencedColumnName = "statement_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Statement statementId;
    @JoinColumn(name = "Student_id", referencedColumnName = "Student_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student studentid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reating", fetch = FetchType.LAZY)
    private Coursework coursework;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reating", fetch = FetchType.LAZY)
    private Diplom diplom;

    public Reating() {
    }

    public Reating(Integer reatingId) {
        this.reatingId = reatingId;
    }

    public Integer getReatingId() {
        return reatingId;
    }

    public void setReatingId(Integer reatingId) {
        this.reatingId = reatingId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Statement getStatementId() {
        return statementId;
    }

    public void setStatementId(Statement statementId) {
        this.statementId = statementId;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    public Coursework getCoursework() {
        return coursework;
    }

    public void setCoursework(Coursework coursework) {
        this.coursework = coursework;
    }

    public Diplom getDiplom() {
        return diplom;
    }

    public void setDiplom(Diplom diplom) {
        this.diplom = diplom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reatingId != null ? reatingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reating)) {
            return false;
        }
        Reating other = (Reating) object;
        if ((this.reatingId == null && other.reatingId != null) || (this.reatingId != null && !this.reatingId.equals(other.reatingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Reating[ reatingId=" + reatingId + " ]";
    }

}
