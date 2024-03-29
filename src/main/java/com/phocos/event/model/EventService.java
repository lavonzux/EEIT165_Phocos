package com.phocos.event.model;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.studio.util.Studio;

import jakarta.transaction.Transactional;

@Service
public class EventService {
	@Autowired
	private EventRepository eRepo;

	//取得所有資料
	public List<Event> findAll(){
		return eRepo.findAll();
	}
	
	// 取得單筆資料
	public Event readEntry(int eventID) {
		Optional<Event> optional = eRepo.findById(eventID);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	// 前台換頁
	public Page<Event> findByPage(Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber - 1, 12, Sort.Direction.ASC, "eventID");
		Page<Event> page = eRepo.findAll(pgb);
		return page;
	}

	//新增資料
	public void insertEvent(Event event) {
		eRepo.save(event);
	}
	//前台新增資料
	public void insertEvent2(Event event) {
		eRepo.save(event);
	}

	//刪除資料
	public void deleteByEventId(Integer eventID) {
		eRepo.deleteById(eventID);
	}

	//取得修改資料
	@GetMapping("/event/{id}")
	public Event getById(@PathVariable Integer eventID) {
		Optional<Event> optional = eRepo.findById(eventID);
		
		if(optional.isPresent()) {
			Event result = optional.get();
			return result;
		}
		
		Event event = new Event();
		event.setEventType("沒有這筆資料");
		
		return event;
	}
	
	//修改資料
	@Transactional
	public Event updateEventById(@RequestParam int eventID,@RequestParam int memberID, @RequestParam String eventType, 
			@RequestParam String eventTopic, @RequestParam String eventInfo, @RequestParam Date eventDate) {
		
		Optional<Event> optional = eRepo.findById(eventID);
		
		if(optional.isPresent()) {
			Event event = optional.get();
			event.setEventType(eventType);
			event.setMemberID(memberID);
			event.setEventTopic(eventTopic);
			event.setEventInfo(eventInfo);
			event.setEventDate(eventDate);
//			event.setEventPicID(eventPicID);
			return event;
		}
		
		System.out.println("no update data");
		
		return null;
		
	}
}
