package com.zzz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.model.Task;
import com.zzz.model.User;
import com.zzz.repository.TaskRepository;
@Controller
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@GetMapping("/task")
	public String task(Model model,HttpSession session) {
		if (session.getAttribute("name") == null) {// 判断是否登录
			return "index";
    	}
	
		User user=(User) session.getAttribute("user");
       String path = user.getPath();
       model.addAttribute("path",path);
		model.addAttribute("pu", taskRepository.select(user));	
		
		return "task";
	}	
	@GetMapping("/task_1")
	public String task_1() {
		return "task1";
	}	
	@PostMapping("/task_save")
	@ResponseBody
	public Map<String, Object> Task_save(HttpSession session,@Valid Task task,@RequestParam("by_date")String bydate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			Date qwq=sdf.parse(bydate);
			String qwq1=sdf.format(new Date());
			if(java.sql.Date.valueOf(qwq1).after(java.sql.Date.valueOf(bydate))) {
				map.put("code", "400");
			}else {
			   User user=(User) session.getAttribute("user");
			    task.setDate(new Date());
			    task.setBydate(qwq);
				task.setUser(user);
				task.setProject(null);
				task.setState(0);
				task.setDelay(0);
				taskRepository.save(task);
				map.put("code", "200");
			}
	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return map;
	}
}
