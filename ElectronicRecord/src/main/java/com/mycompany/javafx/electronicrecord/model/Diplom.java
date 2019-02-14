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
@Table(name = "diplom")
public class Diplom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Diplom_id")
    private Integer diplomid;
    @Column(name = "Thesis")
    private String thesis;
    @JoinColumn(name = "Diplom_id", referencedColumnName = "reating_id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Reating reating;

    public Diplom() {
    }

    public Diplom(Integer diplomid) {
        this.diplomid = diplomid;
    }

    public Integer getDiplomid() {
        return diplomid;
    }

    public void setDiplomid(Integer diplomid) {
        this.diplomid = diplomid;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
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
        hash += (diplomid != null ? diplomid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Diplom)) {
            return false;
        }
        Diplom other = (Diplom) object;
        if ((this.diplomid == null && other.diplomid != null) || (this.diplomid != null && !this.diplomid.equals(other.diplomid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Diplom[ diplomid=" + diplomid + " ]";
    }

}
