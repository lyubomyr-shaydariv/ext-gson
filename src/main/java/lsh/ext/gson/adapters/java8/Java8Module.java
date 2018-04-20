package lsh.ext.gson.adapters.java8;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class Java8Module
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private final Iterable<? extends TypeAdapterFactory> typeAdapterFactories;

	private Java8Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java 8");
		this.typeAdapterFactories = typeAdapterFactories;
	}

	public static IModule get() {
		return instance;
	}

	public static Builder build() {
		return new Builder();
	}

	@Nonnull
	@Override
	protected Iterable<? extends TypeAdapterFactory> getTypeAdapterFactories() {
		return typeAdapterFactories;
	}

	public static final class Builder {

		private Builder() {
		}

		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
					.add(OptionalTypeAdapterFactory.get())
					.build();
			return new Java8Module(typeAdapterFactories);
		}

	}

}
