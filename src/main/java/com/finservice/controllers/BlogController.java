package main.java.com.finservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.finservice.dao.TransactionDAO;
import main.java.com.finservice.models.Blog;

@RestController
public class BlogController {
	@RequestMapping("/")
	public String index() {
		return "Congratulations from BlogController.java";
	}

	@GetMapping("/blog")
	public Blog getBlog(){
		return TransactionDAO.fetchBlog();
	}
}