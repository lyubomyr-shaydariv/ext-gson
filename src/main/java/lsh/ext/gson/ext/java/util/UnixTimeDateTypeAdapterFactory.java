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
import lsh.ext.gson.IFactory0;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnixTimeDateTypeAdapterFactory<T extends Date>
		extends AbstractTypeAdapterFactory<T>
		implements ITypeAdapterFactory<T> {

	private final IFactory0.IFactory<? extends T> dateFactoryFactory;

	public static <T extends Date> ITypeAdapterFactory<T> getInstance(
			final IFactory0.IFactory<? extends T> dateFactoryFactory
	) {
		return new UnixTimeDateTypeAdapterFactory<>(dateFactoryFactory);
	}

	public static IFactory0<Date> createFactory(final TypeToken<Date> typeToken) {
		return () -> new Date(0);
	}

	@Override
	@Nullable
	protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Date.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeToken<T> castTypeToken = (TypeToken<T>) typeToken;
		@SuppressWarnings("unchecked")
		final IFactory0.IFactory<T> castDateFactoryFactory = (IFactory0.IFactory<T>) dateFactoryFactory;
		return new Adapter<>(castDateFactoryFactory.create(castTypeToken));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<T extends Date>
			extends TypeAdapter<T> {

		private static final int MS_IN_S = 1000;

		private final IFactory0<? extends T> instanceFactory;

		public static <T extends Date> TypeAdapter<T> getInstance(final IFactory0<? extends T> instanceFactory) {
			return new Adapter<T>(instanceFactory)
					.nullSafe();
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			final long time = in.nextLong() * MS_IN_S;
			final T value = instanceFactory.create();
			value.setTime(time);
			return value;
		}

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			out.value(value.getTime() / MS_IN_S);
		}

	}

}
