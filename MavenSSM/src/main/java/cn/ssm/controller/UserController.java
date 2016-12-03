package cn.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ssm.entity.User;
import cn.ssm.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 指定当前启动页为index.jsp
	 * 
	 * @return
	 */
	@RequestMapping(value = { "index", "/" })
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	@RequestMapping("toLogin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("login")
	public ModelAndView login(User user) {
		ModelAndView mav = new ModelAndView("user/user_list");

		User loginUser = userService.login(user.getName(), user.getAge());
		if (loginUser != null) {
			List<User> list = userService.getUserAll(null);
			mav.addObject("userlst", list);

			mav.addObject("loginUser", loginUser.getName());
		} else {
			mav.addObject("loginUser", "登录失败");
		}

		return mav;
	}

	@RequestMapping("toUserList")
	public ModelAndView toUserList() {
		ModelAndView mav = new ModelAndView("user/user_list");
		List<User> list = userService.getUserAll(null);
		mav.addObject("userlst", list);
		return mav;
	}

	@RequestMapping("toAddUser")
	public String toAddUser() {
		return "user/user_add";
	}

	@RequestMapping("addUser")
	public ModelAndView addUser(User user) {
		boolean flag = userService.addUser(user);
		if (flag) {
			return toUserList();
		}
		return null;
	}

	@RequestMapping("toDelUser")
	public ModelAndView toDelUser(Integer id) {
		boolean flag = userService.deleteUser(id);
		if (flag) {
			return toUserList();
		}
		return null;
	}

	@RequestMapping("toEditUser")
	public ModelAndView toEditUser(Integer id) {
		ModelAndView mav = new ModelAndView("user/user_update");

		User user = userService.getUserOne(id);
		mav.addObject("oldUser", user);

		return mav;
	}

	@RequestMapping("editUser")
	public ModelAndView editUser(User user) {
		boolean flag = userService.editUser(user);
		if (flag) {
			return toUserList();
		}
		return null;
	}

	@RequestMapping("toPageList")
	public ModelAndView toPageList(User user, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("user/user_pagelist");

		// 设置默认页码
		Integer pageNum = 1;
		// 页码
		if (request.getParameter("pageNum") != null
				&& request.getParameter("") != "pageNum") {
			pageNum = Integer.parseInt(request.getParameter("pageNum")
					.toString());
		}

		PageHelper.startPage(pageNum, 5);
		List<User> userlst = userService.getUserAll(user);

		PageInfo<User> lst = new PageInfo<User>(userlst);
		mav.addObject("page", lst);

		return mav;
	}
}
