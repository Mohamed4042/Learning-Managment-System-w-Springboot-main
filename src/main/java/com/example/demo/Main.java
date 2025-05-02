package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		// Load the .env file
		Dotenv dotenv = Dotenv.load();
		
		// Set environment variables
		// NOTE!!!! 
		// you have to have a .env file with the following variables in order of the smtp server to run
		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
		
		SpringApplication.run(Main.class, args);
	}
}