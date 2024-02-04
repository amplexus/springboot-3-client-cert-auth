package com.springboot.springbootsslbundles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MyController {

	private static final String template = "Hello, %s!";

	@Autowired
	private SecureServiceRestApi secureService;

	@GetMapping("/hello")
	public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.warn("MyController::hello: called with name {} - now invoking secure service", name);
		secureService.fetchData("test");
		log.warn("MyController::hello: called with name {} - invoked secure service", name);
		return new Greeting(String.format(template, name));
	}
}

