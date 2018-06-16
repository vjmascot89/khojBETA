package com.khojbeta.engine.websock;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.khojbeta.engine.model.UserContent;

@EnableScheduling
@Configuration
public class WebSockScheduling {
	@Autowired
	SimpMessagingTemplate simp;
	
	private static AtomicInteger count=new AtomicInteger(1);
	
	@Scheduled(fixedDelay=3000)
	public void getScheduler(){
	simp.convertAndSend("/topic/user", new UserContent("From Scheduler "+count.getAndIncrement()));
	}
}
