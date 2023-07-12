package com.phocos.event.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "event")
	public class Event {
	
    @Id
    @Column(name = "eventID")
    private Integer eventID;
	public Integer getEventID() {
		return eventID;
	}


	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}


	public Integer getMemberID() {
		return memberID;
	}


	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	public String getEventTopic() {
		return eventTopic;
	}


	public void setEventTopic(String eventTopic) {
		this.eventTopic = eventTopic;
	}


	public String getEventInfo() {
		return eventInfo;
	}


	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}


	public Date getEventDate() {
		return eventDate;
	}


	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}


//	public Integer getEventPicID() {
//		return eventPicID;
//	}
//
//
//	public void setEventPicID(Integer eventPicID) {
//		this.eventPicID = eventPicID;
//	}


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
//	@Column(name = "eventPicID")
//	private Integer eventPicID;
	

	public Event() {
		
	}
}
