package com.zzz.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zzz.model.User;
import com.zzz.repository.UserRepository;

@Controller
public class IconController {
	@Autowired
	private UserRepository userRepository;	
	@PostMapping(value="/icon_save")
	@ResponseBody
	public Map<String, Object> dupload(@RequestParam("files") List<MultipartFile> files,HttpServletRequest req,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		String realpath = req.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(realpath);
		for (MultipartFile file : files) {
			Path filename = path.resolve(file.getOriginalFilename());//getOriginalFilename()这个方法是获取上传时的文件名
			try {
				User user = (User) session.getAttribute("user");
				if(user.getPath().equals("../"+file.getOriginalFilename())) {
					map.put("code", "400");
				}else {
				Files.copy(file.getInputStream(), filename);
			
				user.setPath("../"+file.getOriginalFilename());
				userRepository.update(user);
				 map.put("code", "200");
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return map;
	}
}
