package com.payhere.homework.core.db.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.payhere.homework.core.db.domain")
@EnableJpaAuditing
@EntityScan(basePackages = "com.payhere.homework.core.db.domain")
public class CoreJpaConfig {

}
