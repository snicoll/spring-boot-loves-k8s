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

import io.spring.sample.scribe.markdown.MarkdownRenderer;
import io.spring.sample.scribe.spell.SpellChecker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class EditorController {

	private final SpellChecker spellChecker;

	private final MarkdownRenderer markdownRenderer;

	EditorController(SpellChecker spellChecker, MarkdownRenderer markdownRenderer) {
		this.spellChecker = spellChecker;
		this.markdownRenderer = markdownRenderer;
	}

	@PostMapping("/")
	String spellcheck(@ModelAttribute("document") ScribeDocument document, Model model) {
		document.setHtml(this.markdownRenderer.renderToHtml(document.getMarkup()));
		document.setTypos(this.spellChecker.spellCheck(document.getMarkup()));
		model.addAttribute("document", document);
		return "index";
	}

	@GetMapping("/")
	String home(Model model) {
		model.addAttribute("document", new ScribeDocument());
		return "index";
	}

}
