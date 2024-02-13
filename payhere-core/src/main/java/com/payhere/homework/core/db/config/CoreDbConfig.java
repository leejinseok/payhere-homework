package com.payhere.homework.core.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CoreJpaConfig.class, CoreQueryDslConfig.class})
public class CoreDbConfig {
}
