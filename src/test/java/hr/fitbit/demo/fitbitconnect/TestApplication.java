package hr.fitbit.demo.fitbitconnect;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "hr.fitbit.demo.fitbitconnect.service",
        "hr.fitbit.demo.fitbitconnect.swagger",
        "hr.fitbit.demo.fitbitconnect.controller",
        "hr.fitbit.demo.fitbitconnect.security",
        "hr.fitbit.demo.fitbitconnect.aspect",
        "hr.fitbit.demo.fitbitconnect.client",
        "hr.fitbit.demo.fitbitconnect.entity",
        "hr.fitbit.demo.fitbitconnect.dao",
        "hr.fitbit.demo.fitbitconnect.pagination"
})
public class TestApplication {
}

