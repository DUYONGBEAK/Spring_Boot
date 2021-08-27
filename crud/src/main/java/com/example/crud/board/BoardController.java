package com.example.crud.board;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@RequestMapping("/board")
public class BoardController {
	private BoardDAO dao = new BoardDAO();

	@RequestMapping("/main")
	public ModelAndView home() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/main");
		List<BoardDTO> arr = dao.getDTO();
		if (arr.get(0).getTitle().equals("err"))
			arr = new ArrayList<BoardDTO>();
		mav.addObject("conArr", arr);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public ModelAndView insertPage(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mav = new ModelAndView("board/insertUpdate");
		mav.addObject("type", "insert");

		String id = (String) request.getSession().getAttribute("userId");
		mav.addObject("id", id);
		System.out.println(id);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/insertServlet")
	public ModelAndView insertServletPage(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(Calendar.getInstance().getTime());
		BoardDTO dto = new BoardDTO(title, content, id, date);
		boolean result = dao.insert(dto);
		mav.setViewName("resultPage");
		if (result == true) {

			model.addAttribute("message", "등록 성공");
		} else {
			model.addAttribute("message", "등록 실패");
		}

		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update")
	public ModelAndView updatePage(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();
	
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String sessionId = (String) request.getSession().getAttribute("userId");
		String sessionName = (String) request.getSession().getAttribute("userName");
		
		if(!userId.equals(sessionId)) {
			mav.setViewName("resultPage");
			model.addAttribute("message", sessionName + "님이 작성한 글이 아닙니다.");
		}else {
			mav.setViewName("board/insertUpdate");
			mav.addObject("type", "update");
			BoardDTO dto = dao.getDTO(id);
			System.out.println(id);
			mav.addObject("id", id);
			mav.addObject("title", dto.getTitle());
			mav.addObject("content", dto.getContent());
		}
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/updateServlet")
	public ModelAndView updateServletPage(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mav = new ModelAndView("resultPage");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String sessionId = (String) request.getSession().getAttribute("userId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(Calendar.getInstance().getTime());
		
		BoardDTO dto = new BoardDTO(id, title, content,sessionId, date);
		
		boolean result = dao.update(dto);
		if(result == true) {
			model.addAttribute("message", "게시판 글 수정 성공");
		}else {
			model.addAttribute("message", "게시판 글 수정 실패");
		}
		return mav;
	}
	

	@RequestMapping(method = RequestMethod.POST, path = "/delete")
	public ModelAndView deletePage(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String sessionId = (String) request.getSession().getAttribute("userId");
		String sessionName = (String) request.getSession().getAttribute("userName");
		System.out.println(id);
		System.out.println(sessionName);
		System.out.println(userId);
		System.out.println(sessionId);
		
		if (!userId.equals(sessionId)) {
			mav.setViewName("resultPage");
			model.addAttribute("message", sessionName + "님이 작성한 글이 아닙니다.");
		} else {
			mav.setViewName("board/delete");
			mav.addObject("id", id);
		}

		return mav;
	}

	// delete 수행
	@RequestMapping(method = RequestMethod.POST, path ="/deleteServlet")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ModelAndView mav = new ModelAndView("resultPage"); // 결과를 알려주는 페이지
		String id = request.getParameter("id");
		System.out.println(id);
		boolean result = dao.delete(id);
		if(result==true) {
			model.addAttribute("message", "게시판 글 삭제 성공");
		}else {
			model.addAttribute("message", "게시판 글 삭제 실패");
		}
		
		return mav;
	}
}
