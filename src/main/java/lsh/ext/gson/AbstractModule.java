package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public abstract class AbstractModule
		implements IModule {

	private static final TypeAdapterFactory[] emptyTypeAdapterFactoryArray = {};

	private final TypeAdapterFactory[] typeAdapterFactories;

	protected AbstractModule(final TypeAdapterFactory... typeAdapterFactories) {
		if ( typeAdapterFactories.length == 0 ) {
			this.typeAdapterFactories = emptyTypeAdapterFactoryArray;
		} else {
			this.typeAdapterFactories = typeAdapterFactories.clone();
		}
	}

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		for ( final TypeAdapterFactory typeAdapterFactory : typeAdapterFactories ) {
			@Nullable
			final TypeAdapter<T> typeAdapter = typeAdapterFactory.create(gson, typeToken);
			if ( typeAdapter != null ) {
				return typeAdapter;
			}
		}
		return null;
	}

}
