package lsh.ext.gson;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("unused")
public interface ITypeAdapterFactory<CT>
		extends TypeAdapterFactory {

	@Override
	@Nullable
	<T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);

	static <CT> ITypeAdapterFactory<CT> forClass(final Class<CT> klass, final Supplier<? extends TypeAdapter<CT>> createTypeAdapter) {
		return new ITypeAdapterFactory<>() {
			@Nullable
			@Override
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				if ( typeToken.getRawType() != klass ) {
					return null;
				}
				@SuppressWarnings("unchecked")
				final TypeToken<CT> castTypeToken = (TypeToken<CT>) typeToken;
				@SuppressWarnings("unchecked")
				final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter.get();
				return castTypeAdapter;
			}
		};
	}

}
