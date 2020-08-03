/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.sample.scribe;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ScribeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScribeApplication.class, args);
	}

	@Bean
	public ApplicationRunner osLogger(Environment environment) {
		return (arguments) -> {
			System.out.printf("======%n");
			System.out.printf("Running on %s %s (%s)%n", environment.getProperty("os.name"),
					environment.getProperty("os.version"), environment.getProperty("os.arch"));
			System.out.printf("custom.test.property is %s%n", environment.getProperty("custom.test.property"));
			System.out.printf("======%n");
		};
	}

}
