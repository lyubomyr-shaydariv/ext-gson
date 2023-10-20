package lsh.ext.gson.ext.java.time;

import javax.annotation.Nullable;

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
	@Nullable
	protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( typeToken.getRawType() != clazz ) {
			return null;
		}
		return typeAdapter;
	}

}
