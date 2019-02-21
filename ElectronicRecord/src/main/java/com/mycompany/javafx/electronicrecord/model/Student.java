package com.mycompany.javafx.electronicrecord.model;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "Student_id")
    private Integer studentid;
    @Column(name = "number_book")
    @CsvBindByName(column = "Номер зачетки")
    private Integer numberBook;
    @Column(name = "course")
    private Integer course;
    @JoinColumn(name = "Group_id", referencedColumnName = "Group_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Groupstud groupid;
    @JoinColumn(name = "Student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentid", fetch = FetchType.LAZY)
    private List<Reating> reatingList;

    @Transient
    @CsvBindByName(column = "ФИО")
    private String fullName;

    public Student() {
    }

    public Student(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getNumberBook() {
        return numberBook;
    }

    public void setNumberBook(Integer numberBook) {
        this.numberBook = numberBook;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Groupstud getGroupid() {
        return groupid;
    }

    public void setGroupid(Groupstud groupid) {
        this.groupid = groupid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentid != null ? studentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentid == null && other.studentid != null) || (this.studentid != null && !this.studentid.equals(other.studentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "studentid=" + studentid + ", numberBook=" + numberBook + ", course=" + course + ", groupid=" + groupid + ", user=" + user + ", fullName=" + fullName + '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Reating> getReatingList() {
        return reatingList;
    }

    public void setReatingList(List<Reating> reatingList) {
        this.reatingList = reatingList;
    }

}
