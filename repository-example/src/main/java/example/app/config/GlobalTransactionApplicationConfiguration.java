/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package example.app.config;

import javax.transaction.TransactionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Spring {@link Configuration} class used to configure Global, JTA-based Transactions using Apache Geode
 * (or Pivotal GemFire) mixed with an external {@link javax.sql.DataSource}, such as a relational database
 * (e.g. MySQL).
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories
 * @see org.springframework.transaction.annotation.EnableTransactionManagement
 * @see org.springframework.transaction.PlatformTransactionManager
 * @see org.springframework.transaction.jta.JtaTransactionManager
 * @see example.app.config.ApplicationConfiguration
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "example.app.repo.jpa")
@EnableTransactionManagement
@Import(ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class GlobalTransactionApplicationConfiguration {

	protected static final String USER_TRANSACTION_NAMING_CONTEXT_NAME = "java:comp/UserTransaction";

	static {
		try {
			SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		}
		catch (Exception e) {
			System.err.println("Failed to initialize the Naming Context!");
			e.printStackTrace(System.err);
		}

	}

	@Bean
	public PlatformTransactionManager transactionManager(TransactionManager transactionManager) {
		return new JtaTransactionManager(transactionManager);
	}
}
