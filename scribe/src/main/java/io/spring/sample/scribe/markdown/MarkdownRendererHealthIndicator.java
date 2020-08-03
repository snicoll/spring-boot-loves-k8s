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

package io.spring.sample.scribe.markdown;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
class MarkdownRendererHealthIndicator extends AbstractHealthIndicator {

	private final MarkdownRenderer markdownRenderer;

	MarkdownRendererHealthIndicator(MarkdownRenderer markdownRenderer) {
		this.markdownRenderer = markdownRenderer;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		String result = this.markdownRenderer.renderToHtml("This is a *test*.");
		if ("<p>This is a <em>test</em>.</p>".equals(result)) {
			builder.up();
		}
		else {
			builder.outOfService().withDetail("result", result);
		}
	}

}
