package com.phocos.event.model;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EventService {

	@Autowired
	private EventRepository eRepo;
	
	// 新增單筆資料
	public void insert(Event event) {
		eRepo.save(event);
		
	}
	
	
	// 用ID查詢
	public Event findById(Integer eventID) {
		Optional<Event> optional = eRepo.findById(eventID);
		if ( optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 查詢全部
	public List<Event> findAll() {
		return eRepo.findAll();
	}
	
	@Transactional
	// 修改
	public Event updateEventById(Integer eventID, Event updateBean) {
		Optional<Event> optional = eRepo.findById(eventID);
		int memberID = updateBean.getMemberID();
		String eventType = updateBean.getEventType();
		String eventTopic = updateBean.getEventTopic();
		String eventInfo = updateBean.getEventInfo();
		Date eventDate = updateBean.getEventDate();
		if(optional.isPresent()) {
			Event event = optional.get();
			event.setMemberID(memberID);
			event.setEventType(eventType);
			event.setEventTopic(eventTopic);
			event.setEventInfo(eventInfo);
			event.setEventDate(eventDate);
			return event;
		}
		return null;
	}
	
	//刪除
	public void deleteById(Integer eventID) {
		eRepo.deleteById(eventID);
	}
	
	// 分頁查詢
	public Page<Event> findByPage(Integer pageNumber) {
		PageRequest pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, "id");

		Page<Event> page = eRepo.findAll(pgb);

		return page;
	}

	
}
