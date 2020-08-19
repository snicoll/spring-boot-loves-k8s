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

import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpellChecker {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private final NavigableSet<String> dictionary = new TreeSet<>();

	private final ApplicationEventPublisher eventPublisher;

	public SpellChecker(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void addWordToDictionary(String word) {
		this.dictionary.add(word);
	}

	public List<Typo> spellCheck(String text) {
		return Arrays.stream(text.toLowerCase().replaceAll("[^a-z\n ]", "").split("[ \n]+"))
				.filter(StringUtils::hasText).map(this::checkWord).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	Typo checkWord(String word) {
		if ("broken".equals(word)) {
			AvailabilityChangeEvent.publish(this.eventPublisher, this, LivenessState.BROKEN);
			return null;
		}
		if (this.dictionary.contains(word)) {
			return null;
		}
		Typo typo = new Typo(word);
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < ALPHABET.length(); j++) {
				String candidate = word.substring(0, i) + ALPHABET.charAt(j) + word.substring(i + 1);
				if (this.dictionary.contains(candidate)) {
					typo.addSuggestion(candidate);
				}
			}
		}
		return typo;
	}

}
