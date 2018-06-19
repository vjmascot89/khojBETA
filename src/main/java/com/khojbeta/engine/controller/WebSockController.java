package com.khojbeta.engine.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.khojbeta.engine.model.User;
import com.khojbeta.engine.model.UserContent;

@Controller
public class WebSockController {

	@MessageMapping("/user")
	@SendTo("/topic/user")
	public UserContent getUser(User user){
		return new UserContent("Hi "+user.getName());
	}
}
