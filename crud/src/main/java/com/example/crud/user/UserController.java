package com.example.crud.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
	private UserDAO dao = new UserDAO();
	
	@RequestMapping("/")
	public ModelAndView home() throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/home");


		return mav;
	}
	
	@RequestMapping("/logout")
	public String tryLogout(HttpServletRequest request) throws Exception{

		request.getSession().setAttribute("userId", "");
		request.getSession().setAttribute("userName", "");

		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ModelAndView tryLogin(HttpServletRequest request, Model model) throws Exception{
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String sha_pwd = getSHA256(pwd);
		
		List<UserDTO> result = dao.tryLogin(id, sha_pwd);
		
		if(result.size() == 0) {
			mav.setViewName("resultPage");
			model.addAttribute("message", "회원정보가 일치하지 않습니다.");
			request.getSession().setAttribute("userId", "");
			request.getSession().setAttribute("userName", "");
		}else {
			UserDTO dto = dao.getDTO(id);
			mav.setViewName("user/home");
			request.getSession().setAttribute("userId", id);
			request.getSession().setAttribute("userName", dto.getName());
			System.out.println(dto.getName());
		}

		return mav;
	}
	
	@RequestMapping("/join")
	public ModelAndView JoinPage() throws Exception{
		ModelAndView mav = new ModelAndView("user/joinUpdate");
		mav.addObject("type", "userInsert");
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/userJoinServlet")
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ModelAndView mav = new ModelAndView(); //결과를 알려주는 페이지
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String sha_pwd = getSHA256(pwd);
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		
		Boolean chk = dao.checkId(id);
		if(chk == true) {
			model.addAttribute("message", "ID중복");
			mav.setViewName("resultPage");
			return mav;
		}else{
			mav.setViewName("resultPage");
			UserDTO dto = new UserDTO(id, sha_pwd, name, age, sex);
			
			boolean result = dao.insert(dto);
			
			mav.addObject("db", "mongo");
			mav.addObject("result", result);
			model.addAttribute("message", "성공");
			return mav;
		}

	}
	
	@RequestMapping("/userUpdate")
	public ModelAndView updatePage(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView("user/joinUpdate");
		mav.addObject("type", "update");
		
		String id = (String) request.getSession().getAttribute("userId");
		UserDTO dto = dao.getDTO(id);
		mav.addObject("id", id);
		mav.addObject("name", dto.getName());
		mav.addObject("age", dto.getAge());
		return mav;
	}
	
	@RequestMapping("/userUpdateServlet")
	public ModelAndView update(HttpServletRequest request, Model model) throws Exception{
		ModelAndView mav = new ModelAndView("resultPage");
		
		String pwd = request.getParameter("pwd");
		String id = request.getParameter("id");
		UserDTO dto1 = dao.getDTO(id);
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		
		if(pwd.equals("")) {
			
			String existingPwd = dto1.getPwd();
			
			UserDTO dto2 = new UserDTO(id, existingPwd, name, age, sex);
			boolean result = dao.update(dto2);
			
			if(result == true) {
				UserDTO dto = dao.getDTO(id);
				request.getSession().setAttribute("userName", dto.getName());
				model.addAttribute("message", "수정 성공");
			}else {
				
				model.addAttribute("message", "수정 오류");
			}
			
		}else {
			String sha_pwd = getSHA256(pwd);
			
			UserDTO dto = new UserDTO(id, sha_pwd, name, age, sex);
			boolean result = dao.update(dto);
			if(result == true) {
				
				model.addAttribute("message", "수정 성공");
			}else {
				
				model.addAttribute("message", "수정 오류");
			}
		}
		
		return mav;
	}
	
	@RequestMapping("/userDelete")
	public ModelAndView deletePage() throws Exception{
		ModelAndView mav = new ModelAndView("user/delete");

		return mav;
	}

	@RequestMapping("/deleteServlet")
	public ModelAndView deleteServletPage(HttpServletRequest request, Model model) throws Exception{
		ModelAndView mav = new ModelAndView("resultPage");
		
		
		
		String id = (String) request.getSession().getAttribute("userId");
		Boolean result = dao.delete(id);
		if(result == true) {
			model.addAttribute("message", "회원탈퇴");
			request.getSession().setAttribute("userId", "");
			request.getSession().setAttribute("userName", "");
		}else {
			model.addAttribute("message", "탈퇴실패");
		}
		
		return mav;
	}
	
	public String getSHA256(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}
}
