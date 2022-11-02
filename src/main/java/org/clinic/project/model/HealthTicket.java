package org.clinic.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "healthticket")
public class HealthTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;

    @Column(nullable = false, length = 300, name = "symptoms")
    private String symptoms;

    @Column(nullable = false, length = 300, name = "diagnois")
    private String diagnosis;
    
}
