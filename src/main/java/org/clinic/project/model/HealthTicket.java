package org.clinic.project.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "healthticket")
public class HealthTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;
    
    @ManyToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patientID", referencedColumnName = "patientID")
    public Patient patientID;

    @ManyToOne(targetEntity = Doctor.class)
    @JoinColumn(name = "doctorID", referencedColumnName = "doctorID")
    public Doctor doctorID;

    @Column(nullable = true, length = 300, name = "symptoms")
    private String symptoms;

    @Column(nullable = true, length = 300, name = "diagnois")
    private String diagnosis;

    private LocalDate dateSubmitted;
    
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Patient getPatientID() {
        return patientID;
    }

    public void setPatientID(Patient patientID) {
        this.patientID = patientID;
    }

    public Doctor getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Doctor doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate localdate) {
        this.dateSubmitted = localdate;
    }

    @Override
    public String toString() {
        return "HealthTicket [ticketID=" + ticketID + ", patientID=" + patientID + ", doctorID=" + doctorID
                + ", symptoms=" + symptoms + ", diagnosis=" + diagnosis + ", dateSubmitted=" + dateSubmitted + "]";
    }
    
    
}
