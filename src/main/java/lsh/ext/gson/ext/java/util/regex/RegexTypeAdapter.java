package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonStringTypeAdapter;

@UtilityClass
public final class RegexTypeAdapter {

	private static final Pattern emptyPattern = Pattern.compile("");

	private static final int[] noFlags = {};

	private static final int[] allKnownFlags = {
			Pattern.UNIX_LINES,             //
			Pattern.CASE_INSENSITIVE,       // /.../i
			Pattern.COMMENTS,               //
			Pattern.MULTILINE,              // /.../m
			Pattern.LITERAL,                //
			Pattern.DOTALL,                 //
			Pattern.UNICODE_CASE,           //
			Pattern.CANON_EQ,               //
			Pattern.UNICODE_CHARACTER_CLASS //
	};

	public interface IFlagStrategy {

		char encodeFlag(int flag);

		int decodeFlag(char c);

		IFlagStrategy defaultFlagStrategy = new IFlagStrategy() {
			@Override
			public char encodeFlag(final int flag) {
				switch ( flag ) {
				case Pattern.CASE_INSENSITIVE:
					return 'i';
				case Pattern.MULTILINE:
					return 'm';
				default:
					throw new UnsupportedOperationException("Unsupported flag: " + flag);
				}
			}

			@Override
			public int decodeFlag(final char c) {
				switch ( c ) {
				case 'i':
					return Pattern.CASE_INSENSITIVE;
				case 'm':
					return Pattern.MULTILINE;
				default:
					throw new UnsupportedOperationException("Unsupported flag: " + c);
				}
			}
		};

	}

	public static TypeAdapter<Pattern> forEdStylePattern(final char delimiter, final IFlagStrategy flagStrategy) {
		return forEdStylePattern(delimiter, flagStrategy, allKnownFlags);
	}

	public static TypeAdapter<Pattern> forEdStylePattern(final char delimiter, final IFlagStrategy flagStrategy, final int declaredFlagsMask) {
		if ( declaredFlagsMask == 0 ) {
			return forEdStylePattern(delimiter, flagStrategy, noFlags);
		}
		final int flagCount = countSetBits(declaredFlagsMask);
		final int[] declaredFlags = new int[flagCount];
		int i = 0;
		for ( int fi = 0; fi < allKnownFlags.length && i < flagCount; fi++ ) {
			final int knownFlag = allKnownFlags[fi];
			if ( (declaredFlagsMask & knownFlag) == 0 ) {
				continue;
			}
			declaredFlags[i++] = knownFlag;
		}
		return forEdStylePattern(delimiter, flagStrategy, declaredFlags);
	}

	@Getter
	private static final TypeAdapter<Pattern> defaultForEdStylePattern = forEdStylePattern('/', IFlagStrategy.defaultFlagStrategy);

	// https://en.wikipedia.org/w/index.php?title=Regular_expression&oldid=1239052130#Delimiters
	private static TypeAdapter<Pattern> forEdStylePattern(final char delimiter, final IFlagStrategy flagStrategy, final int[] declaredFlags) {
		return JsonStringTypeAdapter.getInstance(
				pattern -> formatEdStylePattern(pattern, delimiter, flagStrategy, declaredFlags),
				pattern -> parseEdStylePattern(pattern, delimiter, flagStrategy)
		);
	}

	private static String formatEdStylePattern(final Pattern pattern, final char delimiter, final IFlagStrategy flagStrategy, final int[] declaredFlags) {
		final String regex = pattern.pattern();
		final StringBuilder s = new StringBuilder(regex.length() + 2 + 10); // two delimiters + pattern + flags at most
		s.append(delimiter);
		s.append(regex); // TODO escape the delimiter in the pattern?
		s.append(delimiter);
		final int fs = pattern.flags();
		for ( final int kf : declaredFlags ) {
			if ( (fs & kf) == 0 ) {
				continue;
			}
			final char fc = flagStrategy.encodeFlag(kf);
			if ( fc == 0 ) {
				continue;
			}
			if ( fc == delimiter ) {
				throw new IllegalArgumentException("Illegal pattern flag representation " + fc + " clashes with the regex delimiter " + delimiter);
			}
			s.append(fc);
		}
		return s.toString();
	}

	private static Pattern parseEdStylePattern(final String pattern, final char delimiter, final IFlagStrategy flagStrategy) {
		final int l = pattern.length();
		if ( l < 1 ) {
			throw new IllegalArgumentException("Regex literal empty");
		}
		if ( l < 2 ) {
			throw new IllegalArgumentException("Regex literal too short: " + pattern);
		}
		if ( l == 2 ) {
			final char d = pattern.charAt(0);
			if ( d != pattern.charAt(1) ) {
				throw new IllegalArgumentException("Malformed regex literal: " + pattern);
			}
			return emptyPattern;
		}
		int flags = 0;
		int i;
		for ( i = l - 1; i >= 0; i-- ) {
			if ( i == 1 ) {
				throw new IllegalArgumentException("Malformed no end-delimiter regex literal: " + pattern);
			}
			final char c = pattern.charAt(i);
			if ( c == delimiter ) {
				break;
			}
			flags |= flagStrategy.decodeFlag(c);
		}
		final String regex = pattern.substring(1, i);
		return Pattern.compile(regex, flags);
	}

	private static int countSetBits(final int mask) {
		int count = 0;
		int m = mask;
		for ( int i = 0; i < 32 && m != 0; i++, m >>= 1 ) {
			if ( (m & 1) == 0 ) {
				continue;
			}
			count++;
		}
		return count;
	}

	@Getter
	private static final TypeAdapter<Pattern> defaultForSimplePattern = JsonStringTypeAdapter.getInstance(RegexTypeAdapter::formatSimplePattern, Pattern::compile);

	private static String formatSimplePattern(final Pattern pattern) {
		final int flags = pattern.flags();
		if ( flags != 0 ) {
			throw new UnsupportedOperationException("Pattern " + pattern + " has non-zero flags set: " + flags);
		}
		return pattern.pattern();
	}

}
