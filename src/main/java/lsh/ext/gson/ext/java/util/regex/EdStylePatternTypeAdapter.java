package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

// https://en.wikipedia.org/w/index.php?title=Regular_expression&oldid=1239052130#Delimiters
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EdStylePatternTypeAdapter
		extends AbstractStringTypeAdapter<Pattern> {

	private static final Pattern emptyPattern = Pattern.compile("");

	public interface IFlagConverter {

		char fromFlag(int flag);

		int toFlag(char c);

		IFlagConverter defaultFlagConverter = new IFlagConverter() {
			@Override
			public char fromFlag(final int flag) {
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
			public int toFlag(final char c) {
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

	public static final char DEFAULT_REGEX_DELIMITER = '/';

	@Getter
	private static final TypeAdapter<Pattern> instance = new EdStylePatternTypeAdapter(DEFAULT_REGEX_DELIMITER, IFlagConverter.defaultFlagConverter);

	private final char delimiter;
	private final IFlagConverter flagConverter;

	public static TypeAdapter<Pattern> getInstance(
			final char delimiter,
			final IFlagConverter flagConverter
	) {
		return new EdStylePatternTypeAdapter(delimiter, flagConverter);
	}

	@Override
	protected Pattern fromString(final String pattern) {
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
			flags |= flagConverter.toFlag(c);
		}
		final String regex = pattern.substring(1, i);
		return Pattern.compile(regex, flags);
	}

	@Override
	protected String toString(final Pattern pattern) {
		final String regex = pattern.pattern();
		final StringBuilder s = new StringBuilder(regex.length() + 2 + 10); // two delimiters + pattern + flags at most
		s.append(delimiter);
		s.append(regex); // TODO escape the delimiter in the pattern?
		s.append(delimiter);
		final int fs = pattern.flags();
		// TODO should the type adapter accept reduced number of flags in order not to make a useless loop iteration?
		// TODO or, better, take the converter in the factory method and ruh it against all Pattern flags so that parsing the string would be, if possible, quicker?
		for ( final int kf : knownFlags ) {
			if ( (fs & kf) == 0 ) {
				continue;
			}
			final char fc = flagConverter.fromFlag(kf);
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

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<Pattern> {

		@Getter
		public static final ITypeAdapterFactory<Pattern> instance = new EdStylePatternTypeAdapter.Factory(DEFAULT_REGEX_DELIMITER, IFlagConverter.defaultFlagConverter);

		private final char delimiter;
		private final IFlagConverter flagConverter;

		private Factory(final char delimiter, final IFlagConverter flagConverter) {
			super(Pattern.class);
			this.delimiter = delimiter;
			this.flagConverter = flagConverter;
		}

		public static ITypeAdapterFactory<Pattern> getInstance(final char delimiter, final IFlagConverter flagConverter) {
			return new Factory(delimiter, flagConverter);
		}

		@Override
		protected TypeAdapter<Pattern> createTypeAdapter(final Gson gson, final TypeToken<? super Pattern> typeToken) {
			return EdStylePatternTypeAdapter.getInstance(delimiter, flagConverter);
		}

	}

	private static final int[] knownFlags = {
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

}
