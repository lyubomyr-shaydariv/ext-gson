package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class GuavaModule
		extends AbstractModule {

	private static final IModule instance = new GuavaModule();

	private static final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
			.add(OptionalTypeAdapterFactory.get())
			.add(MultimapTypeAdapterFactory.get())
			.add(MultisetTypeAdapterFactory.get())
			.build();

	private GuavaModule() {
		super("Google Guava");
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
