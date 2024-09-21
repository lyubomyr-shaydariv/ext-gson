package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class EncodedJsonStringTypeAdapter {

	public static <T> TypeAdapter<T> delegate(final TypeAdapter<T> typeAdapter) {
		return JsonStringTypeAdapter.getInstance(
				typeAdapter::toJson,
				json -> {
					try {
						return typeAdapter.fromJson(json);
					} catch ( final IOException ex ) {
						throw new RuntimeException(ex);
					}
				}
		);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		@Override
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			return delegate(gson.getAdapter(typeToken));
		}

	}

}
