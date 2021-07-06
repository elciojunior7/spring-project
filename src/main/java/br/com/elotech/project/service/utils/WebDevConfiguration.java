package br.com.elotech.project.service.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class WebDevConfiguration {

}
