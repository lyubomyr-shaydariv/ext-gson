package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
final class PatternWrapper {

	private final String pattern;
	private final int flags;

	static PatternWrapper from(final Pattern pattern) {
		return new PatternWrapper(pattern.pattern(), pattern.flags());
	}

}
