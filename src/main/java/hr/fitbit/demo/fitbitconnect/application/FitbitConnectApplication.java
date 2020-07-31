package hr.fitbit.demo.fitbitconnect.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/* disable security Spring Boot 2.2.x */
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})

@SpringBootApplication
@ComponentScan(basePackages = {
        "hr.fitbit.demo.fitbitconnect.service",
        "hr.fitbit.demo.fitbitconnect.swagger",
        "hr.fitbit.demo.fitbitconnect.controller",
        "hr.fitbit.demo.fitbitconnect.security",
        "hr.fitbit.demo.fitbitconnect.aspect",
        "hr.fitbit.demo.fitbitconnect.client",
        "hr.fitbit.demo.fitbitconnect.pagination"
})
@EntityScan("hr.fitbit.demo.fitbitconnect.entity")
@EnableJpaRepositories("hr.fitbit.demo.fitbitconnect.dao")
public class FitbitConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitbitConnectApplication.class, args);
    }

}
