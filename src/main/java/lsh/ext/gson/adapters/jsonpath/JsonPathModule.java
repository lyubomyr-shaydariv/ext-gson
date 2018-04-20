package lsh.ext.gson.adapters.jsonpath;

import java.util.Arrays;
import java.util.Collections;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class JsonPathModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private JsonPathModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Jayway JsonPath", typeAdapterFactories);
	}

	public static IModule get() {
		return instance;
	}

	public static Builder build() {
		return new Builder();
	}

	public static final class Builder {

		private Builder() {
		}

		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Collections.unmodifiableList(Arrays.asList(
					JsonPathTypeAdapterFactory.get()
			));
			return new JsonPathModule(typeAdapterFactories);
		}

	}

}
