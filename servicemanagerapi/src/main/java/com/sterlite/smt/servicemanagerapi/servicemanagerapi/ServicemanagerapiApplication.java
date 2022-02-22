package com.sterlite.smt.servicemanagerapi.servicemanagerapi;

import com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration.Status;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.repositories.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServicemanagerapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicemanagerapiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo){
		return args -> {
			serverRepo.save(new Server(null, "10.123.10.12", "Deep Linux", "32 GB", "Working Env",
					"http://localhost:8088/server/image/server1.png", Status.SERVER_UP));
			serverRepo.save(new Server(null, "10.189.23.89", "Windows", "64 GB", "Gaming Env",
					"http://localhost:8088/server/image/server2.png", Status.SERVER_UP));
		};
	}

}
