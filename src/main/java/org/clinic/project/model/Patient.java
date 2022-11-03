package org.clinic.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(nullable = false, length = 10)
    private String patientID;

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

    @Column(nullable = false, length = 300, name = "medical_conditions")
    private String medicalConditions;

    @OneToMany(targetEntity = HealthTicket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "patientID", referencedColumnName = "patientID")
    private List<HealthTicket> healthtickets;

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
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

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    @Override
    public String toString() {
        return "Patient [patientID=" + patientID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", password=" + password + ", dob=" + dob + ", sex=" + sex + ", medicalConditions="
                + medicalConditions + "]";
    }

}
