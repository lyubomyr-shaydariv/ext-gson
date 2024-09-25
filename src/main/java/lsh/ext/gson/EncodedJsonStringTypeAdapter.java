package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
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

}
