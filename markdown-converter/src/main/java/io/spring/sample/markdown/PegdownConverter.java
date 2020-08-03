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

package io.spring.sample.markdown;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import org.springframework.stereotype.Component;

@Component
class PegdownConverter {

	private final PegDownProcessor pegdown;

	PegdownConverter() {
		this.pegdown = new PegDownProcessor(Extensions.ALL ^ Extensions.ANCHORLINKS);
	}

	String convert(String markup) {
		// synchronizing on pegdown instance since neither the processor nor the
		// underlying parser is thread-safe.
		synchronized (this.pegdown) {
			return this.pegdown.markdownToHtml(markup.toCharArray());
		}
	}

}
