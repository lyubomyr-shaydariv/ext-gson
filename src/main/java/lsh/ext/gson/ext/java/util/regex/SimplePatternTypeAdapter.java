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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimplePatternTypeAdapter
		extends AbstractStringTypeAdapter<Pattern> {

	@Getter
	public static final TypeAdapter<Pattern> instance = new SimplePatternTypeAdapter()
			.nullSafe();

	@Override
	protected Pattern fromString(final String text) {
		return Pattern.compile(text);
	}

	@Override
	protected String toString(final Pattern pattern) {
		final int flags = pattern.flags();
		if ( flags != 0 ) {
			throw new UnsupportedOperationException("Pattern " + pattern + " has non-zero flags set: " + flags);
		}
		return pattern.pattern();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<Pattern> {

		@Getter
		public static final ITypeAdapterFactory<Pattern> instance = createFactory(SimplePatternTypeAdapter.instance);

		private final TypeAdapter<Pattern> typeAdapter;

		private Factory(final TypeAdapter<Pattern> typeAdapter) {
			super(Pattern.class);
			this.typeAdapter = typeAdapter;
		}

		private static ITypeAdapterFactory<Pattern> createFactory(final TypeAdapter<Pattern> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<Pattern> createTypeAdapter(final Gson gson, final TypeToken<? super Pattern> typeToken) {
			return typeAdapter;
		}

	}

}
