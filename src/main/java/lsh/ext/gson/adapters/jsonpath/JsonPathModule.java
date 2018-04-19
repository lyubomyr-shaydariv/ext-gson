package lsh.ext.gson.adapters.jsonpath;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class JsonPathModule
		extends AbstractModule {

	private static final IModule instance = new JsonPathModule();

	private static final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
			.add(JsonPathTypeAdapterFactory.get())
			.build();

	private JsonPathModule() {
		super("Jayway JsonPath");
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
