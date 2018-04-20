package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class GuavaModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private final Iterable<? extends TypeAdapterFactory> typeAdapterFactories;

	private GuavaModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Google Guava");
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

		@Nullable
		private Supplier<? extends Multiset<Object>> newMultisetFactory;

		@Nullable
		private Supplier<? extends Multimap<String, Object>> newMultimapFactory;

		private Builder() {
		}

		public Builder withNewMultisetFactory(final Supplier<? extends Multiset<Object>> newMultisetFactory) {
			this.newMultisetFactory = newMultisetFactory;
			return this;
		}

		public Builder withNewMultimapFactory(final Supplier<? extends Multimap<String, Object>> newMultimapFactory) {
			this.newMultimapFactory = newMultimapFactory;
			return this;
		}

		public IModule done() {
			final Iterable<TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
					.add(OptionalTypeAdapterFactory.get())
					.add(MultimapTypeAdapterFactory.get(newMultimapFactory))
					.add(MultisetTypeAdapterFactory.get(newMultisetFactory))
					.build();
			return new GuavaModule(typeAdapterFactories);
		}

	}

}
