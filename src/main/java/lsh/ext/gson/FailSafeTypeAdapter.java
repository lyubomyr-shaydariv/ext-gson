package lsh.ext.gson;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class FailSafeTypeAdapter<T>
		extends TypeAdapter<T> {

	private final TypeAdapter<T> typeAdapter;
	private final boolean ignoreRuntimeException;

	@Nullable
	private final T defaultValue;

	private static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter, final boolean ignoreRuntimeException, @Nullable final T defaultValue) {
		return new FailSafeTypeAdapter<T>(typeAdapter, ignoreRuntimeException, defaultValue)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		typeAdapter.write(out, value);
	}

	@Override
	@Nullable
	public T read(final JsonReader in)
			throws IOException {
		try {
			return typeAdapter.read(in);
		} catch ( final RuntimeException ex ) {
			if ( !ignoreRuntimeException ) {
				throw ex;
			}
			JsonReaders.skipValue(in);
			return defaultValue;
		} catch ( final MalformedJsonException ignored ) {
			JsonReaders.skipValue(in);
			return defaultValue;
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<T>
			implements ITypeAdapterFactory<T> {

		private static final Boolean defaultBoolean = false;
		private static final Byte defaultByte = (byte) 0;
		private static final Short defaultShort = (short) 0;
		private static final Integer defaultInteger = 0;
		private static final Long defaultLong = 0L;
		private static final Float defaultFloat = 0.0F;
		private static final Double defaultDouble = 0.0D;
		private static final Character defaultCharacter = '\u0000';

		private final Class<T> klass;
		@Nullable
		private final T defaultValue;
		private final boolean catchRuntimeException;

		public static <T> TypeAdapterFactory getInstance(final Class<T> klass, final boolean catchRuntimeException) {
			return new Factory<>(klass, getDefaultValue(klass), catchRuntimeException);
		}

		public static <T> TypeAdapterFactory getInstance(final Class<T> klass, final T defaultValue, final boolean catchRuntimeException) {
			return new Factory<>(klass, defaultValue, catchRuntimeException);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			if ( !klass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
			@SuppressWarnings("unchecked")
			final T castDefaultValue = (T) defaultValue;
			return FailSafeTypeAdapter.getInstance(typeAdapter, catchRuntimeException, castDefaultValue);
		}

		@Nullable
		@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:AvoidEscapedUnicodeCharacters", "IfStatementWithTooManyBranches", "ChainOfInstanceofChecks" })
		private static <T> T getDefaultValue(final Class<T> klass) {
			@Nullable
			final Object result;
			if ( klass == boolean.class || klass == Boolean.class ) {
				result = defaultBoolean;
			} else if ( klass == byte.class || klass == Byte.class ) {
				result = defaultByte;
			} else if ( klass == short.class || klass == Short.class ) {
				result = defaultShort;
			} else if ( klass == int.class || klass == Integer.class ) {
				result = defaultInteger;
			} else if ( klass == long.class || klass == Long.class ) {
				result = defaultLong;
			} else if ( klass == float.class || klass == Float.class ) {
				result = defaultFloat;
			} else if ( klass == double.class || klass == Double.class ) {
				result = defaultDouble;
			} else if ( klass == char.class || klass == Character.class ) {
				result = defaultCharacter;
			} else {
				return null;
			}
			@SuppressWarnings("unchecked")
			final T castResult = (T) result;
			return castResult;
		}

	}

}
