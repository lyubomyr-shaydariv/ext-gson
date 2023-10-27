package lsh.ext.gson.ext.java.util;

import java.util.Optional;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

public final class Java8UtilModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = builder()
			.build();

	private Java8UtilModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Optional<?>> optionalTypeAdapterFactory = OptionalTypeAdapter.Factory.getInstance();

		public IModule build() {
			return new Java8UtilModule(UnmodifiableIterable.copyOf(
					optionalTypeAdapterFactory
			));
		}

	}

}
