package lsh.ext.gson.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.LiteralStringTypeAdapter;

@UtilityClass
public final class EncodedJsonStringTypeAdapter {

	public static <T> TypeAdapter<T> delegate(final TypeAdapter<T> typeAdapter) {
		return LiteralStringTypeAdapter.getInstance(
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
