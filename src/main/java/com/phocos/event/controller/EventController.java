package com.phocos.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.event.model.Event;
import com.phocos.event.model.EventService;
import com.phocos.studio.util.Shed;
import com.phocos.studio.util.Studio;
import com.phocos.studio.util.StudioPic;



@Controller
public class EventController {
	@Autowired
	private EventService eServ;

	//取得所有資料
	@GetMapping("/events")
	public String findAll(Model model) {
		List<Event> events = eServ.findAll();
		
		model.addAttribute("events",events);
		return "backstage/event/Event";
	}
	
	// 前台取得所有資料
	@GetMapping("/browseevents")
	public String findAllfore(Model model) {
		List<Event> events = eServ.findAll();
		
		model.addAttribute("events",events);
		return "forestage/event/eventFore";
	}
	
	// 前台取得單筆資料
	@GetMapping("/event/read")
	public String readPage(@RequestParam("eventID") Integer eventID, Model model) {
		Event event = eServ.getById(eventID);
		model.addAttribute("event", event);
		return "forestage/event/eventForeread";
	}
	
	// 前台新增資料
		@PostMapping("/event/insertFore")
		public String insert2(@ModelAttribute("event") Event event) {
			eServ.insertEvent2(event);
			return "redirect:/browseevents";
		}
	// 跳到前台新增頁面
		@GetMapping("/event/insertFore")
		public String insertPage2() {
			return "forestage/event/InsertEventFore";
		}
	// 跳到新增頁面
	@GetMapping("/event/insert")
	public String insertPage() {
		return "backstage/event/InsertEvent";
	}
	
	// 新增資料
	@PostMapping("/event/insertData")
	public String insert(@ModelAttribute("event") Event event) {
		eServ.insertEvent(event);
		return "redirect:/events";
	}

	// 刪除資料
	@DeleteMapping("/event/delete")
	public String deleteEvent(@RequestParam("eventID") Integer eventID) {
		eServ.deleteByEventId(eventID);
		return "redirect:/events";
	}
	
	// 修改資料
	@GetMapping("/event/edit")
	public String editPage(@RequestParam("eventID") Integer eventID, Model model) {
		Event event = eServ.getById(eventID);
		model.addAttribute("event", event);
		return "backstage/event/UpdateEvent";
	}
	
	@PutMapping("/event/editData")
	public String editPost(@ModelAttribute(name="event") Event event) {
		eServ.updateEventById(event.getEventID(), event.getMemberID(), 
				event.getEventType(), event.getEventTopic(), event.getEventInfo(),
				event.getEventDate());
		
		return "redirect:/events";
	}

}
