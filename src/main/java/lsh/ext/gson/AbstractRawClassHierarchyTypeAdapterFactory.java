package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRawClassHierarchyTypeAdapterFactory<K>
		implements ITypeAdapterFactory<K> {

	private final Class<K> klass;

	protected abstract TypeAdapter<K> createTypeAdapter(final Gson gson, final TypeToken<? super K> typeToken);

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( !klass.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeToken<K> castTypeToken = (TypeToken<K>) typeToken;
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter(gson, castTypeToken);
		return castTypeAdapter;
	}

}
