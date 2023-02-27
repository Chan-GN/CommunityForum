package com.me.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/* Auditing 활용을 위한 Configuration 클래스 */

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
