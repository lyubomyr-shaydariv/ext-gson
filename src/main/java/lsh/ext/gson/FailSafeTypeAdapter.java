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
import org.apache.commons.collections4.functors.FalsePredicate;

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
		private static <T> T getDefaultValue(final Class<?> clazz) {
			if ( !clazz.isPrimitive() || clazz == void.class ) {
				return null;
			}
			final Object result;
			if ( clazz == boolean.class ) {
				result = false;
			} else if ( clazz == byte.class ) {
				result = (byte) 0;
			} else if ( clazz == short.class ) {
				result = (short) 0;
			} else if ( clazz == int.class ) {
				result = 0;
			} else if ( clazz == long.class ) {
				result = 0L;
			} else if ( clazz == float.class ) {
				result = 0.0F;
			} else if ( clazz == double.class ) {
				result = 0.0D;
			} else if ( clazz == char.class ) {
				result = '\u0000';
			} else {
				throw new AssertionError(clazz);
			}
			@SuppressWarnings("unchecked")
			final T castResult = (T) result;
			return castResult;
		}

	}

}
