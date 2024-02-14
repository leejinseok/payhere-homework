package com.payhere.homework.api.application.config;

import com.payhere.homework.core.db.config.CoreDbConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CoreDbConfig.class})
public class ApiDbConfig {
}
