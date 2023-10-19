package lsh.ext.gson.ext.java.time;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractBaseTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<T> {

	private final Class<T> clazz;
	private final TypeAdapter<T> typeAdapter;

	@Override
	protected final boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == clazz;
	}

	@Override
	protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return typeAdapter;
	}

}
