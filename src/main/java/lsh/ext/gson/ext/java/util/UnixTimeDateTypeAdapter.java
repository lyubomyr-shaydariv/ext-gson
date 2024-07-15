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
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnixTimeDateTypeAdapter<T extends Date>
		extends TypeAdapter<T> {

	private static final int MS_IN_S = 1000;

	private final IBuilder0<? extends T> instanceFactory;

	public static <T extends Date> TypeAdapter<T> getInstance(final IBuilder0<? extends T> instanceFactory) {
		return new UnixTimeDateTypeAdapter<T>(instanceFactory)
				.nullSafe();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final long time = in.nextLong() * MS_IN_S;
		final T value = instanceFactory.build();
		value.setTime(time);
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

		private final IBuilder0.IFactory<? extends T> dateFactoryFactory;

		public static <T extends Date> ITypeAdapterFactory<T> getInstance(
				final IBuilder0.IFactory<? extends T> dateFactoryFactory
		) {
			return new Factory<>(dateFactoryFactory);
		}

		public static IBuilder0<Date> createFactory(@SuppressWarnings("unused") final TypeToken<Date> typeToken) {
			return Date::new;
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
			final IBuilder0.IFactory<T> castDateFactoryFactory = (IBuilder0.IFactory<T>) dateFactoryFactory;
			return UnixTimeDateTypeAdapter.getInstance(castDateFactoryFactory.create(castTypeToken));
		}

	}

}
