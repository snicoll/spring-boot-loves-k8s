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

package io.spring.sample.scribe.spell;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
class DictionaryLoader implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final SpellChecker spellChecker;

	DictionaryLoader(SpellChecker spellChecker) {
		this.spellChecker = spellChecker;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Initializing dictionary");
		Resource resource = new DefaultResourceLoader().getResource("classpath:words.txt");
		try (InputStream in = resource.getInputStream()) {
			String content = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
			for (String word : content.split("\\r?\\n")) {
				this.spellChecker.addWordToDictionary(word);
			}
		}
		Thread.sleep(Duration.ofSeconds(10).toMillis());
	}

}
