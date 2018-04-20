package lsh.ext.gson.adapters.jsonapi;

import java.util.Arrays;
import java.util.Collections;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class JsonApiModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private JsonApiModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java JSON API", typeAdapterFactories);
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
					JsonValueTypeAdapterFactory.get()
			));
			return new JsonApiModule(typeAdapterFactories);
		}

	}

}
