package lsh.ext.gson.adapters.jsonapi;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class JsonApiModule
		extends AbstractModule {

	private static final IModule instance = new JsonApiModule();

	private static final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
			.add(JsonValueTypeAdapterFactory.get())
			.build();

	private JsonApiModule() {
		super("Java JSON API");
	}

	public static IModule get() {
		return instance;
	}

	@Nonnull
	@Override
	protected Iterable<? extends TypeAdapterFactory> getTypeAdapterFactories() {
		return typeAdapterFactories;
	}

}
