/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user07
 */
@Entity
@Table(name = "speciality")
@NamedQueries({
    @NamedQuery(name = "Speciality.findAll", query = "SELECT s FROM Speciality s")})
public class Speciality implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Speciality_id")
    private Integer specialityid;
    @Column(name = "nameSpeciality")
    private String nameSpeciality;
    @OneToMany(mappedBy = "speciality",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Group> groups;
     
    

    public Speciality() {
    }

    public Speciality(Integer specialityid) {
        this.specialityid = specialityid;
    }

    public Integer getSpecialityid() {
        return specialityid;
    }

    public void setSpecialityid(Integer specialityid) {
        this.specialityid = specialityid;
    }

    public String getNameSpeciality() {
        return nameSpeciality;
    }

    public void setNameSpeciality(String nameSpeciality) {
        this.nameSpeciality = nameSpeciality;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specialityid != null ? specialityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Speciality)) {
            return false;
        }
        Speciality other = (Speciality) object;
        if ((this.specialityid == null && other.specialityid != null) || (this.specialityid != null && !this.specialityid.equals(other.specialityid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.javafx.electronicrecord.model.Speciality[ specialityid=" + specialityid + " ]";
    }

    /**
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
}
