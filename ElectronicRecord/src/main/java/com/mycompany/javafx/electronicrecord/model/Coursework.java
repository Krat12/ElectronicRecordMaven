package com.mycompany.javafx.electronicrecord.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "coursework")
public class Coursework implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Coursework_id")
    private Integer courseworkid;
    @Column(name = "fullNameBoss")
    private String fullNameBoss;
    @Column(name = "placePracticle")
    private String placePracticle;
    @JoinColumn(name = "Coursework_id", referencedColumnName = "reating_id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Reating reating;

    public Coursework() {
    }

    public Coursework(Integer courseworkid) {
        this.courseworkid = courseworkid;
    }

    public Integer getCourseworkid() {
        return courseworkid;
    }

    public void setCourseworkid(Integer courseworkid) {
        this.courseworkid = courseworkid;
    }

    public String getPlacePracticle() {
        return placePracticle;
    }

    public void setPlacePracticle(String placePracticle) {
        this.placePracticle = placePracticle;
    }

    public Reating getReating() {
        return reating;
    }

    public void setReating(Reating reating) {
        this.reating = reating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseworkid != null ? courseworkid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coursework)) {
            return false;
        }
        Coursework other = (Coursework) object;
        if ((this.courseworkid == null && other.courseworkid != null) || (this.courseworkid != null && !this.courseworkid.equals(other.courseworkid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Coursework[ courseworkid=" + courseworkid + " ]";
    }

    public String getFullNameBoss() {
        return fullNameBoss;
    }

    public void setFullNameBoss(String fullNameBoss) {
        this.fullNameBoss = fullNameBoss;
    }

}
