package com.phocos.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.event.model.Event;
import com.phocos.event.model.EventService;


@Controller
public class EventController {
	
	@Autowired
	private EventService eService;

	@GetMapping("/dashboard/event")
	public String processMainAction(Model m) {
		List<Event> events = eService.findAll();
		m.addAttribute("events", events);
		return "backstage/event/Event";
	}

	@GetMapping("/dashboard/event/create")
	public String insertPage() {
		return "backstage/event/CreateEvent";
	}

	@PostMapping("/dashboard/event/created")
	public String createMember(@ModelAttribute("event") Event eve2) {
		eService.insert(eve2);
		return "redirect:/dashboard/event";
	}

	@PostMapping("/dashboard/event/delete")
	public void deleteEvent(@RequestParam("eventID") int eventID) {
		eService.deleteById(eventID);
	}

	@PostMapping("/dashboard/event/update")
	public String updateEvent1(@RequestParam("eventID") int eventID, Model m) {
		Event event = eService.findById(eventID);
		m.addAttribute("event", event);
		return "backstage/event/UpdateEvent";
	}

	@PostMapping("/dashboard/event/updated")
	public String updateEvent2(@RequestParam("eventID") int eventID,
			@ModelAttribute("updateBean") Event updatedEvent) {
		eService.updateEventById(eventID, updatedEvent);
		return "redirect:/dashboard/event";
	}


}
