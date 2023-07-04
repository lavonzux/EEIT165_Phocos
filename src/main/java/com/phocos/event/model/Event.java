package com.phocos.event.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data
@Table(name = "events")
@Component
	public class Event {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(nullable = false)
    private Integer eventID;
    
	@Column(name = "memberID")
	private Integer memberID;
	
	@Column(name = "eventType")
	private String eventType;
	
	@Column(name = "eventTopic")
	private String eventTopic;
	
	@Column(name = "eventInfo")
	private String eventInfo;
	
	@Column(name = "eventDate")
	private Date eventDate;
	
		
}
