package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Implements an abstract module with modules found in linear manner (from first to last).
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public abstract class AbstractModule
		implements IModule {

	private final String name;
	private final Iterable<? extends TypeAdapterFactory> typeAdapterFactories;

	protected AbstractModule(final String name, final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		this.name = name;
		this.typeAdapterFactories = typeAdapterFactories;
	}

	@Nonnull
	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final <T> TypeAdapter<T> create(@Nonnull final Gson gson, @Nonnull final TypeToken<T> typeToken) {
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
