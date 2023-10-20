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

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractUnixTimeDateTypeAdapterFactory<T extends Date>
		extends AbstractTypeAdapterFactory<T> {

	private final Class<T> dateClass;
	private final TypeAdapter<T> dateTypeAdapter;

	@Override
	@Nullable
	protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( typeToken.getRawType() != dateClass ) {
			return null;
		}
		return dateTypeAdapter;
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	protected abstract static class AbstractAdapter<T extends Date>
			extends TypeAdapter<T> {

		protected abstract T createInstance(long date);

		@Override
		public final T read(final JsonReader in)
				throws IOException {
			return createInstance(in.nextLong() * 1000);
		}

		@Override
		public final void write(final JsonWriter out, final T value)
				throws IOException {
			out.value(value.getTime() / 1000);
		}

	}

}
