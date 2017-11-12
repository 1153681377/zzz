package com.zzz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.zzz.model.Comment;
import com.zzz.model.Project;
import com.zzz.model.Task;
import com.zzz.model.User;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.TaskRepository;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/task")
	public String task(Model model, HttpSession session) {
		if (session.getAttribute("name") == null) {// 判断是否登录
			return "index";
		}

		User user = (User) session.getAttribute("user");
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("pu", taskRepository.select(user));

		return "task";
	}

	@GetMapping("/task_1")
	public String task_1(HttpSession session, Model model) {
		String id = (String) session.getAttribute("user_id");
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("pro", projectRepository.select(id));
		return "task1";
	}

	@PostMapping("/task_save")
	@ResponseBody
	public Map<String, Object> Task_save(HttpSession session, @Valid Task task, @RequestParam("by_date") String bydate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		String pro_id = "4a88dc78-a798-445d-bb15-e1799d2dc275";
		List<Project> list = projectRepository.selectpro(pro_id);
		Project project = list.get(0);
		try {
			User user = (User) session.getAttribute("user");
			Date qwq = sdf.parse(bydate);
			String qwq1 = sdf.format(new Date());
			if (java.sql.Date.valueOf(qwq1).after(java.sql.Date.valueOf(bydate))) {
				map.put("code", "400");
			} else {
				task.setDate(new Date());
				task.setBydate(qwq);
				task.setUser(user);
				task.setState(0);
				task.setDelay(0);
				if (pro_id == null) {
					task.setProject(null);
				} else {
					task.setProject(project);
					project.setMax(project.getMax() + 1);
					projectRepository.update(project);
				}
				taskRepository.save(task);
				map.put("code", "200");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return map;
	}

	@PostMapping("/task_update")
	@ResponseBody
	public Map<String, Object> Tuptade(@RequestParam("id") int task_id, @RequestParam("state") String newstate) {
		int state;
		Map<String, Object> map = new HashMap<String, Object>();
		if (task_id == 0 || newstate == null) {
			map.put("code", "400");

		} else {

			if (newstate.equals("true")) {
				state = 1;
				map.put("code", "200");
			} else {
				state = 0;
				map.put("code", "201");
			}
			List<Task> task1 = taskRepository.selectid(task_id);
			Task task = task1.get(0);
			task.setState(state);
			taskRepository.update(task);
			if (task.getProject() == null) {
			} else {
				Project project = task.getProject();
				project.setComplete(project.getComplete() + 1);
				projectRepository.update(project);
			}
		}
		return map;
	}
	@PostMapping("/comment_save")
	@ResponseBody
	public Map<String, Object> comment_save(@RequestParam("id") int task_id,@RequestParam("comment_content") String content,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Task> list = taskRepository.selectid(task_id);
		User user = (User) session.getAttribute("user");
		Task task = list.get(0);
		Comment comment = new Comment();
		comment.setId(UUID.randomUUID().toString());
		comment.setComment_content(content);
		comment.setUser(user);
		comment.setDate(new Date());
		comment.setTask(task);
		return map;
	}

	@GetMapping("/task_details")
	public String details(@RequestParam("id") int task_id, Model model,HttpSession session) {
		    Task task = (Task) session.getAttribute("task");
		    model.addAttribute("details", task);
			model.addAttribute("comment", taskRepository.taskdetails(task));
		
	return "task_details";
	}
	@PostMapping("/task_details")
	@ResponseBody
	public Map<String, Object> taskdetails(@RequestParam("id") int task_id,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Task> list = taskRepository.selectid(task_id);
	    Task task = list.get(0);
	    session.setAttribute("task", task);
	    map.put("code", "200");
		return map;
	}
}
