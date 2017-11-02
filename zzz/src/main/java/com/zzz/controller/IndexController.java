package com.zzz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzz.model.Task;
import com.zzz.repository.TaskRepository;
@Controller
public class IndexController {
	@Autowired
	private TaskRepository taskRepository;
	@GetMapping("/task")
	public String task(Model model) {
		model.addAttribute("pu", taskRepository.select());	
		return "task";
	}	
	@GetMapping("/task_1")
	public String task_1() {
		return "task1";
	}	
	@GetMapping("/project")
	public String project() {
		return "project";
	}	
	@GetMapping("/project_details")
	public String project_details() {
		return "project_details";
	}	
	@GetMapping("/test")
	public String Test() {
		return "test";
	}
	@RequestMapping(value = "/test_save")
	public String Test_save(@Valid Task task,BindingResult bindingResult) {
		taskRepository.save(task);
		return "redirect:/task";
	}
}
