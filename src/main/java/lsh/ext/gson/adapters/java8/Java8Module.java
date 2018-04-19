package lsh.ext.gson.adapters.java8;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class Java8Module
		extends AbstractModule {

	private static final IModule instance = new Java8Module();

	private static final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
			.add(OptionalTypeAdapterFactory.get())
			.build();

	private Java8Module() {
		super("Java 8");
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
