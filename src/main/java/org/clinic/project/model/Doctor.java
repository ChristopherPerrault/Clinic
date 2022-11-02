package org.clinic.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorID;

    @Column(nullable = false, length = 25, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 25, name = "last_name")
    private String lastName;

    @Column(nullable = false, length = 50, name = "email")
    private String email;

    @Column(nullable = false, length = 64, name = "password")
    private String password;

    @Column(nullable = false, name = "date_of_birth")
    private Date dob;

    @Column(nullable = false, length = 6, name = "sex")
    private String sex;

    @OneToMany(targetEntity = HealthTicket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctorID", referencedColumnName = "doctorID")
    private List<HealthTicket> healthtickets;

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    
}