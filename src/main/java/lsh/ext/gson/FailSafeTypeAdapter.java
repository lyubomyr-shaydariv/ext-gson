package lsh.ext.gson;

import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class FailSafeTypeAdapter<T>
		extends TypeAdapter<T> {

	private final TypeAdapter<T> typeAdapter;
	private final boolean ignoreRuntimeException;
	private final Supplier<? extends T> getDefault;

	public static <T> TypeAdapter<T> getInstance(final Class<T> klass, final TypeAdapter<T> typeAdapter, final boolean ignoreRuntimeException) {
		final T defaultValue = getDefaultValue(klass);
		return getInstance(typeAdapter, ignoreRuntimeException, () -> defaultValue);
	}

	public static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter, final boolean ignoreRuntimeException, final Supplier<? extends T> getDefault) {
		return new FailSafeTypeAdapter<T>(typeAdapter, ignoreRuntimeException, getDefault)
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
			return getDefault.get();
		} catch ( final MalformedJsonException ignored ) {
			JsonReaders.skipValue(in);
			return getDefault.get();
		}
	}

	@Nullable
	@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:AvoidEscapedUnicodeCharacters", "IfStatementWithTooManyBranches", "ChainOfInstanceofChecks" })
	private static <T> T getDefaultValue(final Class<T> klass) {
		@Nullable
		final Object result;
		if ( klass == boolean.class || klass == Boolean.class ) {
			result = false;
		} else if ( klass == byte.class || klass == Byte.class ) {
			result = (byte) 0;
		} else if ( klass == short.class || klass == Short.class ) {
			result = (short) 0;
		} else if ( klass == int.class || klass == Integer.class ) {
			result = 0;
		} else if ( klass == long.class || klass == Long.class ) {
			result = 0L;
		} else if ( klass == float.class || klass == Float.class ) {
			result = 0.0F;
		} else if ( klass == double.class || klass == Double.class ) {
			result = 0.0D;
		} else if ( klass == char.class || klass == Character.class ) {
			result = '\u0000';
		} else {
			return null;
		}
		@SuppressWarnings("unchecked")
		final T castResult = (T) result;
		return castResult;
	}

}
