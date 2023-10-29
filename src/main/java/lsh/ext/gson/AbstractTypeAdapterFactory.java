package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

public abstract class AbstractTypeAdapterFactory<CT>
		implements ITypeAdapterFactory<CT> {

	@Nullable
	protected abstract TypeAdapter<CT> createTypeAdapter(Gson gson, TypeToken<?> typeToken);

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		@Nullable
		final TypeAdapter<CT> typeAdapter = createTypeAdapter(gson, typeToken);
		if ( typeAdapter == null ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) typeAdapter;
		return castTypeAdapter;
	}

}
