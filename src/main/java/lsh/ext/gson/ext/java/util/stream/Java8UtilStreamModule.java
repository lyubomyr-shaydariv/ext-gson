package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class Java8UtilStreamModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private Java8UtilStreamModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends Stream<?>> streamTypeAdapterFactory = StreamTypeAdapterFactory.forStream(false, false);

		@Setter
		private ITypeAdapterFactory<? extends IntStream> intStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForIntStream;

		@Setter
		private ITypeAdapterFactory<? extends LongStream> longStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForLongStream;

		@Setter
		private ITypeAdapterFactory<? extends DoubleStream> doubleStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForDoubleStream;

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new Java8UtilStreamModule(
					streamTypeAdapterFactory,
					intStreamTypeAdapterFactory,
					longStreamTypeAdapterFactory,
					doubleStreamTypeAdapterFactory
			);
		}

	}

}
