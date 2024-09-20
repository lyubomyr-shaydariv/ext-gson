package lsh.ext.gson.ext.java.util.regex;

import java.io.IOException;
import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class EdStylePatternTestCoverageTypeAdapterTest {

	private static final TypeAdapter<Pattern> unit = RegexTypeAdapter.getDefaultForEdStylePattern();

	@Test
	public void testFromStringRegexLiteralExpressionEmpty() {
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> unit.fromJson("\"\""));
		Assertions.assertEquals("Regex literal empty", ex.getMessage());
	}

	@Test
	public void testFromStringRegexLiteralTooShort() {
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> unit.fromJson("\"/\""));
		Assertions.assertEquals("Regex literal too short: /", ex.getMessage());
	}

	@Test
	public void testFromStringMalformedRegexLiteral() {
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> unit.fromJson("\"/|\""));
		Assertions.assertEquals("Malformed regex literal: /|", ex.getMessage());
	}

	@Test
	public void testFromStringEmptyRegex()
			throws IOException {
		final Pattern pattern = unit.fromJson("\"//\"");
		Assertions.assertEquals(PatternWrapper.from(Pattern.compile("")), PatternWrapper.from(pattern));
	}

	@Test
	public void testFromStringMalformedNoEndDelimiterRegexLiteral() {
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> unit.fromJson("\"/mimimi\""));
		Assertions.assertEquals("Malformed no end-delimiter regex literal: /mimimi", ex.getMessage());
	}

	@Test
	public void testFromStringPatternNoFlags()
			throws IOException {
		final Pattern pattern = unit.fromJson("\"/foo/\"");
		Assertions.assertEquals(PatternWrapper.from(Pattern.compile("foo")), PatternWrapper.from(pattern));
	}

	@Test
	public void testFromStringPatternWithFlags()
			throws IOException {
		final Pattern pattern = unit.fromJson("\"/foo/mi\"");
		Assertions.assertEquals(PatternWrapper.from(Pattern.compile("foo", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)), PatternWrapper.from(pattern));
	}

	@Test
	public void testToStringWithoutFlags() {
		final Pattern pattern = Pattern.compile("foo");
		Assertions.assertEquals("\"/foo/\"", unit.toJson(pattern));
	}

	@Test
	public void testToStringWithFlags() {
		final Pattern pattern = Pattern.compile("foo", Pattern.CASE_INSENSITIVE);
		Assertions.assertEquals("\"/foo/i\"", unit.toJson(pattern));
	}

	@Test
	public void testToStringDelimiterAndFlagClash() {
		final TypeAdapter<Pattern> unit = RegexTypeAdapter.forEdStylePattern('#', new RegexTypeAdapter.IFlagStrategy() {
			@Override
			public char encodeFlag(final int flag) {
				return '#';
			}

			@Override
			public int decodeFlag(final char c) {
				throw new AssertionError(c);
			}
		});
		final Pattern pattern = Pattern.compile("foo", Pattern.MULTILINE);
		final IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> unit.toJson(pattern));
		Assertions.assertEquals("Illegal pattern flag representation # clashes with the regex delimiter #", ex.getMessage());
	}

	@Test
	public void testToStringForIgnoredFlags() {
		final TypeAdapter<Pattern> unit = RegexTypeAdapter.forEdStylePattern('/', new RegexTypeAdapter.IFlagStrategy() {
			@Override
			public char encodeFlag(final int flag) {
				return 0;
			}

			@Override
			public int decodeFlag(final char c) {
				throw new AssertionError(c);
			}
		});
		final Pattern pattern = Pattern.compile("foo", Pattern.MULTILINE);
		final String json = unit.toJson(pattern);
		Assertions.assertEquals("\"/foo/\"", json);
	}

}
