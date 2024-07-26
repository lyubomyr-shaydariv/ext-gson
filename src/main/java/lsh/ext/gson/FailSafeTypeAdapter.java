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
	public static final class Factory
			implements TypeAdapterFactory {

		private static final TypeAdapterFactory doNotCatchRuntimeExceptionInstance = new Factory(false);
		private static final TypeAdapterFactory doCatchRuntimeExceptionInstance = new Factory(true);

		private static final Boolean defaultBoolean = false;
		private static final Byte defaultByte = (byte) 0;
		private static final Short defaultShort = (short) 0;
		private static final Integer defaultInteger = 0;
		private static final Long defaultLong = 0L;
		private static final Float defaultFloat = 0.0F;
		private static final Double defaultDouble = 0.0D;
		private static final Character defaultCharacter = '\u0000';

		private final boolean catchRuntimeException;

		public static TypeAdapterFactory getInstance(final boolean catchRuntimeException) {
			if ( !catchRuntimeException ) {
				return doNotCatchRuntimeExceptionInstance;
			}
			return doCatchRuntimeExceptionInstance;
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			if ( typeToken.getRawType().isPrimitive() ) {
				return null;
			}
			final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
			final T defaultValue = getDefaultValue(typeToken.getRawType());
			return FailSafeTypeAdapter.getInstance(typeAdapter, catchRuntimeException, defaultValue);
		}

		@Nullable
		@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:AvoidEscapedUnicodeCharacters", "IfStatementWithTooManyBranches", "ChainOfInstanceofChecks" })
		private static <T> T getDefaultValue(final Class<?> clazz) {
			if ( !clazz.isPrimitive() ) {
				return null;
			}
			final Object result;
			if ( clazz == boolean.class ) {
				result = defaultBoolean;
			} else if ( clazz == byte.class ) {
				result = defaultByte;
			} else if ( clazz == short.class ) {
				result = defaultShort;
			} else if ( clazz == int.class ) {
				result = defaultInteger;
			} else if ( clazz == long.class ) {
				result = defaultLong;
			} else if ( clazz == float.class ) {
				result = defaultFloat;
			} else if ( clazz == double.class ) {
				result = defaultDouble;
			} else if ( clazz == char.class ) {
				result = defaultCharacter;
			} else {
				throw new AssertionError(clazz);
			}
			@SuppressWarnings("unchecked")
			final T castResult = (T) result;
			return castResult;
		}

	}

}
