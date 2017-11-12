package com.zzz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.model.Project;
import com.zzz.model.Projectman;
import com.zzz.model.Task;
import com.zzz.model.User;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.ProjectmanRepository;
import com.zzz.repository.TaskRepository;

@Controller
public class ProjectController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectmanRepository projectmanRepository;
	@GetMapping("/project")
	public String project(HttpSession session,Model model) {
		String userid = (String) session.getAttribute("user_id");
		List list=projectRepository.select(userid);
		User user = (User)session.getAttribute("user");
		List<Project> list1 =projectRepository.selectuser(user);
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("user", list1);
		model.addAttribute("pu", list);	
		
		return "project";
	}
	@PostMapping("/pro_edit")
	@ResponseBody
	public Map<String, Object> pro_edit(@RequestParam("id") String id,HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		Map<String, Object> map = new HashMap<String, Object>();
		if(id.equals(user_id)) {
			map.put("code", "200");
		}
		else {
			map.put("code", "400");
		}
		return map;
	}
	@PostMapping("/pro_delete")
	@ResponseBody
	public Map<String, Object> pro_delete(@RequestParam("id") String id,HttpSession session,@RequestParam("proid") String proid) {
		String user_id = (String) session.getAttribute("user_id");
	    List<Project> list = projectRepository.selectpro(proid);
		Project project = list.get(0);
		List<Task> tlist = taskRepository.prodelete(project);
		List<Projectman> plist = projectmanRepository.mandelete(project);
		for(Task t1:tlist) {
			taskRepository.delete(t1);
		}
		for(Projectman p1:plist) {
			projectmanRepository.delete(p1);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(id.equals(user_id)) {
			projectRepository.delete(project);
			map.put("code", "200");
		}
		else {
			map.put("code", "400");
		}
		return map;
	}
	@GetMapping("/project_details")
	public String project_details(Model model,@RequestParam("pro_id") String proid,HttpSession session) {
		List list=projectRepository.selectpro(proid);
		List list1=taskRepository.selecttask(proid);
	    Project project = (Project) list.get(0);
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("task",list1);
		model.addAttribute("pro",list);
		return "project_details";
	}	
	@GetMapping("/project1")
	public String project1(HttpSession session,Model model) {
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		return "project1";
	}
	@PostMapping("/pro_save")
	public String pro_save(HttpSession session,@Valid Project project){

		   User user = (User) session.getAttribute("user");
		   project.setPro_id(UUID.randomUUID().toString());
		   project.setDate(new Date());
		   project.setUser(user);
		   project.setMax(0);
		   project.setComplete(0);
		   project.setFinish(0);
		   projectRepository.save(project);
		return "redirect:/project";
	}
	
}
