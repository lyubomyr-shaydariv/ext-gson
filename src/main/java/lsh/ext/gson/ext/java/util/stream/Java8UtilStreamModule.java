package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class Java8UtilStreamModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends Stream<?>> streamTypeAdapterFactory = StreamTypeAdapterFactory.forStream(false, false);

	@Setter
	private ITypeAdapterFactory<? extends IntStream> intStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForIntStream;

	@Setter
	private ITypeAdapterFactory<? extends LongStream> longStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForLongStream;

	@Setter
	private ITypeAdapterFactory<? extends DoubleStream> doubleStreamTypeAdapterFactory = StreamTypeAdapterFactory.defaultForDoubleStream;

	@Override
	public IModule build() {
		return IModule.from(
				streamTypeAdapterFactory,
				intStreamTypeAdapterFactory,
				longStreamTypeAdapterFactory,
				doubleStreamTypeAdapterFactory
		);
	}

}
