package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("UseOfObsoleteDateTimeApi")
public final class UnixTimeDateTypeAdapter<T extends Date>
		extends TypeAdapter<T> {

	private static final int MS_IN_S = 1000;

	private final IFactory<? extends T> instanceFactory;

	public static <T extends Date> TypeAdapter<T> getInstance(final IFactory<? extends T> instanceFactory) {
		return new UnixTimeDateTypeAdapter<T>(instanceFactory)
				.nullSafe();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final long time = in.nextLong();
		final T value = instanceFactory.create();
		value.setTime(time * MS_IN_S);
		return value;
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(value.getTime() / MS_IN_S);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<T extends Date>
			extends AbstractTypeAdapterFactory<T> {

		private final Class<T> klass;
		private final IFactory<? extends T> instanceBuilder;

		public static <T extends Date> ITypeAdapterFactory<T> getInstance(
				final Class<T> klass,
				final IFactory<? extends T> instanceFactory
		) {
			return new Factory<>(klass, instanceFactory);
		}

		@Override
		@Nullable
		protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !klass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			return UnixTimeDateTypeAdapter.getInstance(instanceBuilder);
		}

	}

}
