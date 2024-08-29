package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRawClassTypeAdapterFactory<CT>
		implements ITypeAdapterFactory<CT> {

	private final Class<CT> klass;

	protected abstract TypeAdapter<CT> createTypeAdapter(final Gson gson, final TypeToken<? super CT> typeToken);

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( typeToken.getRawType() != klass ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeToken<CT> castTypeToken = (TypeToken<CT>) typeToken;
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter(gson, castTypeToken);
		return castTypeAdapter;
	}

}
