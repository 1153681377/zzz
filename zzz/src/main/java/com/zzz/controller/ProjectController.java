package com.zzz.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzz.model.Project;
import com.zzz.model.User;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.TaskRepository;

@Controller
public class ProjectController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@GetMapping("/project")
	public String project(HttpSession session,Model model) {
		String userid = (String) session.getAttribute("user_id");
		List list=projectRepository.select(userid);
		model.addAttribute("pu", list);	
		return "project";
	}	
	@GetMapping("/project_details")
	public String project_details(Model model,@RequestParam("pro_id") String proid) {
		List list=projectRepository.selectpro(proid);
		List list1=taskRepository.selecttask(proid);
		model.addAttribute("task",list1);
		model.addAttribute("pro",list);
		return "project_details";
	}	
	@GetMapping("/project1")
	public String project1() {
		return "project1";
	}
	@PostMapping("/pro_save")
	public String pro_save(HttpSession session,@Valid Project project){

		   User user = (User) session.getAttribute("user");
		   project.setPro_id(UUID.randomUUID().toString());
		   project.setDate(new Date());
		   project.setUser(user);
		   projectRepository.save(project);
		return "redirect:/project";
	}
}
