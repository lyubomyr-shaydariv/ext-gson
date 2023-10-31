package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.Stream;

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

public final class Java8UtilStreamModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private Java8UtilStreamModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Stream<?>> streamTypeAdapterFactory = StreamTypeAdapter.Factory.getInstance();

		public static Builder create() {
			return new Builder();
		}

		public IModule build() {
			return new Java8UtilStreamModule(UnmodifiableIterable.copyOf(
					streamTypeAdapterFactory
			));
		}

	}

}
