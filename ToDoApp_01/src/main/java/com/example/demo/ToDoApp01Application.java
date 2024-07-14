package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.SavingPurposeRepository;

@SpringBootApplication
public class ToDoApp01Application implements ApplicationRunner{
	
	@Autowired
	SavingPurposeRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(ToDoApp01Application.class, args);
	}

	@Override
	public void run(ApplicationArguments arg) throws Exception{
//		SavingPurpose savings1 = new SavingPurpose();
//        savings1.setName("Emergency Fund");
//        savings1.setNeededAmount(1000.0);
//        savings1.setCurrentAmount(0.0);
//        repository.save(savings1);
//        
//        SavingPurpose savings2 = new SavingPurpose();
//        savings2.setName("Dental Treatment");
//        savings2.setNeededAmount(1000.0);
//        savings2.setCurrentAmount(0.0);
//        repository.save(savings2);
	}
}

