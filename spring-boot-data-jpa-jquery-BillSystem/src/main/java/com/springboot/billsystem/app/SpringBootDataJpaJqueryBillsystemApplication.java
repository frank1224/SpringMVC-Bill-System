package com.springboot.billsystem.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.billsystem.app.services.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaJqueryBillsystemApplication implements CommandLineRunner {


	@Autowired
	@Qualifier("UploadService")
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaJqueryBillsystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAllUpload();
		uploadFileService.initDirectoryUpload();;
	}

	
}
