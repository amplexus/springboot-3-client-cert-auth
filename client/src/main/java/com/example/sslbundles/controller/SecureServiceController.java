package com.example.sslbundles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sslbundles.model.Greeting;
import com.example.sslbundles.service.SecureRestApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
public class SecureServiceController {

	private static final String template = "Hello, %s!";

	@Autowired
	private SecureRestApi secureService;

	@GetMapping("/hello")
	public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.warn("MyController::hello: called with name {} - now invoking secure service", name);
		secureService.fetchData(name);
		log.warn("MyController::hello: called with name {} - invoked secure service", name);
		return new Greeting(String.format(template, name));
	}
}

