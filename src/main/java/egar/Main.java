package egar;

import egar.repository.ReportRepository;
import egar.service.ReportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        application.setLazyInitialization(true);
        application.run(args);
    }

}
