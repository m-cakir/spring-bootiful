package com.bootiful.controller;

import com.bootiful.model.User;
import com.bootiful.security.AuthSuccessListener;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

	private String viewBase = "";

    protected BaseController() {
    }
    
    protected BaseController(String viewBase){
		setViewBase(viewBase);
    }
    
    protected String render(String viewName) {
        return viewBase + viewName;
    }

	protected String forwardTo(String location){
		if(location == null) {
			location = "";
		}
		return "forward:/" + location;
	}

	protected String redirectTo(String location){
		if(location == null) {
			location = "";
		}
		return "redirect:/" + location;
	}

	public void setViewBase(String viewBase){
		if(!StringUtils.isEmpty(viewBase)){
			viewBase = viewBase.trim();
			if (viewBase.length() > 0){
				this.viewBase = viewBase + "/";
			}
		}
	}

	public String getViewBase() {
		return viewBase;
	}

	public static User getCurrentUser(HttpServletRequest request){

		return (User) request.getSession().getAttribute(AuthSuccessListener.CURRENT_USER_KEY);

	}
}
