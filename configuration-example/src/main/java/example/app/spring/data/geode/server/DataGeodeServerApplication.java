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

package example.app.spring.data.geode.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEmbeddedLocator;
import org.springframework.data.gemfire.config.annotation.EnableEmbeddedManager;

import example.app.config.server.EchoServerApplicationConfiguration;

/**
 * The BootGeodeServerApplication class is a {@link SpringBootApplication} that configures and bootstrap
 * a Geode Server application JVM process using Spring Data Geode's new and improved Java annotation-based
 * configuration approach.
 *
 * @author John Blum
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.context.annotation.Import
 * @see org.springframework.data.gemfire.config.annotation.CacheServerApplication
 * @see org.springframework.data.gemfire.config.annotation.EnableEmbeddedLocator
 * @see org.springframework.data.gemfire.config.annotation.EnableEmbeddedManager
 * @see example.app.config.server.EchoServerApplicationConfiguration
 * @since 1.0.0
 */
@SpringBootApplication
@EnableEmbeddedLocator
@EnableEmbeddedManager(jmxManagerStart = true)
@CacheServerApplication(name = "DataGeodeServerApplication", port = DataGeodeServerApplication.GEODE_CACHE_SERVER_PORT)
@Import(EchoServerApplicationConfiguration.class)
public class DataGeodeServerApplication {

	public static final int GEODE_CACHE_SERVER_PORT = 11235;

	public static void main(String[] args) {
		SpringApplication.run(DataGeodeServerApplication.class, args);
	}
}
