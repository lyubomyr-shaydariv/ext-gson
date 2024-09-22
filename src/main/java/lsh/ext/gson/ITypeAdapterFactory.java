package lsh.ext.gson;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("unused")
public interface ITypeAdapterFactory<K>
		extends TypeAdapterFactory {

	@Override
	@Nullable
	<T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);

	static <K> ITypeAdapterFactory<K> forClass(final Class<K> klass, final Supplier<? extends TypeAdapter<K>> createTypeAdapter) {
		return new ITypeAdapterFactory<>() {
			@Nullable
			@Override
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				if ( typeToken.getRawType() != klass ) {
					return null;
				}
				@SuppressWarnings("unchecked")
				final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter.get();
				return castTypeAdapter;
			}
		};
	}

}
