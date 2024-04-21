package com.ssafy.controller;
import java.net.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.dto.User;
import com.ssafy.service.AttentionService;
import com.ssafy.service.HouseService;
import com.ssafy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
	
	
	private final UserService userService;
	private final AttentionService attentionService;
	private final HouseService houseService;
	
	
	public UserController(UserService userService, AttentionService attentionService, HouseService houseService) {
		super();
		this.userService = userService;
		this.attentionService = attentionService;
		this.houseService = houseService;
	}
	
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.setAttribute("msg", "완료되었습니다.");

		return "forward:/index";
	}
	
	
	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}
	
	
	
	
	
	
	@PostMapping("/login")
	public String login(@RequestParam("userid") String userId, @RequestParam("userpw") String userPw,  HttpServletRequest request) {
		
		try {
			User user = userService.find(userId, userPw);
			
			System.out.println(user);
			
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", user);
			session.setAttribute("msg", "로그인 완료");
			return "redirect:/index";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());	
			return "redirect:/user/login?userid=" + userId;
		}
	}
	
	
	
	
	@PostMapping("/join")
	public String join(@RequestParam("userid") String userId,
			@RequestParam("userpw") String userPw,
			@RequestParam("username") String userName,
			@RequestParam("address") String userAddress,
			HttpServletRequest request) {
		
		
		User newUser = new User(userId, userPw, userName, userAddress);
		
		try {
			if (userService.join(newUser) == 1) {
				request.setAttribute("msg", "가입 완료");
				return "index";
				
			} else {
				System.out.println("가입 실패");

				request.setAttribute("msg", "가입 실패");
				return "index";
			}
		} catch (Exception e) {
			System.out.println("예외");

			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());	
			return "index";
		}
		
		
	}
	
	
	
	
	
	
	

}
