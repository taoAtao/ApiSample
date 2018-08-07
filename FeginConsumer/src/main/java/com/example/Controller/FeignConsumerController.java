package com.example.Controller;

import com.example.Service.AlarmService;
import com.example.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController public class FeignConsumerController {
	@Autowired
	LogService logService;

	@Autowired
	AlarmService alarmService;

	@PostMapping(value = "/log/queryInfo")
	public String logQuery(@RequestParam("name") String name) {
		return logService.queryInfo(name);
	}

	@DeleteMapping(value = "/log/delete")
	public String logDelete(@RequestParam("name") String name) {
		return logService.deleteInfo(name);
	}

	@PostMapping(value = "/alarm/queryInfo")
	public String alarmQuery(@RequestParam("name") String name) {
		return alarmService.queryInfo(name);
	}

	@DeleteMapping(value = "/alarm/delete")
	public String alarmdelete(@RequestParam("name") String name){
		return alarmService.deleteInfo(name);
	}
}