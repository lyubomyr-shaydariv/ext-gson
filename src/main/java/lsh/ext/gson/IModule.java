package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public interface IModule
		extends TypeAdapterFactory {

	@Override
	@Nullable
	<T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);

	static IModule empty() {
		return new IModule() {
			@Override
			@Nullable
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				return null;
			}
		};
	}

	static IModule from(final TypeAdapterFactory... typeAdapterFactories) {
		if ( typeAdapterFactories.length == 0 ) {
			return empty();
		}
		return new IModule() {
			private final TypeAdapterFactory[] typeAdapterFactoriesCopy = typeAdapterFactories.clone();

			@Override
			@Nullable
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				for ( final TypeAdapterFactory typeAdapterFactory : typeAdapterFactoriesCopy ) {
					@Nullable
					final TypeAdapter<T> typeAdapter = typeAdapterFactory.create(gson, typeToken);
					if ( typeAdapter != null ) {
						return typeAdapter;
					}
				}
				return null;
			}
		};
	}

}
