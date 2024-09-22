package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class StreamTypeAdapterFactory {

	public static ITypeAdapterFactory<DoubleStream> defaultForDoubleStream = forDoubleStream(DoubleStreamTypeAdapter.getInstance());

	public static ITypeAdapterFactory<DoubleStream> forDoubleStream(final TypeAdapter<DoubleStream> typeAdapter) {
		return ITypeAdapterFactory.forClass(DoubleStream.class, typeResolver -> typeAdapter);
	}

	public static ITypeAdapterFactory<IntStream> defaultForIntStream = forIntStream(IntStreamTypeAdapter.getInstance());

	public static ITypeAdapterFactory<IntStream> forIntStream(final TypeAdapter<IntStream> typeAdapter) {
		return ITypeAdapterFactory.forClass(IntStream.class, typeResolver -> typeAdapter);
	}

	public static ITypeAdapterFactory<LongStream> defaultForLongStream = forLongStream(LongStreamTypeAdapter.getInstance());

	public static ITypeAdapterFactory<LongStream> forLongStream(final TypeAdapter<LongStream> typeAdapter) {
		return ITypeAdapterFactory.forClass(LongStream.class, typeResolver -> typeAdapter);
	}

}
