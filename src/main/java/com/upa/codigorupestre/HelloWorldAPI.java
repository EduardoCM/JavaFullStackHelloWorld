package com.upa.codigorupestre;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class HelloWorldAPI {
	
	@Autowired
	private HelloWorldRepository repository;
	
	@GetMapping("/hello/{name}/{age}")
	public String hello(@PathVariable String name, @PathVariable Integer age) {
		String welcomeMsg = "Welcome: " + name + " Age: " + age + " : " + LocalDateTime.now();
		
		HelloEntity helloWorld = new HelloEntity();
		helloWorld.setMsj(welcomeMsg);
		repository.save(helloWorld);
		
		return welcomeMsg;
		
	}

}
