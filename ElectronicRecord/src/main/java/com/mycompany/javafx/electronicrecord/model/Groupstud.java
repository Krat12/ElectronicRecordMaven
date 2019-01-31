package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "groupstud")
@NamedQueries({
    @NamedQuery(name = "Groupstud.findAll", query = "SELECT g FROM Groupstud g")})
public class Groupstud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Group_id")
    private Integer groupid;
    @Column(name = "Group_name")
    private String groupname;
    @Column(name = "setYear")
    private Short setYear;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupid", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Student> studentList;
    @JoinColumn(name = "speciality_id", referencedColumnName = "Speciality_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Speciality specialityId;

    public Groupstud() {
    }

    public Groupstud(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Speciality getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Speciality specialityId) {
        this.specialityId = specialityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupid != null ? groupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupstud)) {
            return false;
        }
        Groupstud other = (Groupstud) object;
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Groupstud[ groupid=" + groupid + " ]";
    }

}
