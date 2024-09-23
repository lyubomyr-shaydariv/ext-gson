package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class StreamTypeAdapterFactory {

	public static <E> ITypeAdapterFactory<Stream<E>> forStream(final boolean useBeginEnd, final boolean autoClose) {
		return ITypeAdapterFactory.forClassHierarchy(Stream.class, typeResolver -> StreamTypeAdapter.forStream(typeResolver.getTypeAdapter(0), useBeginEnd, autoClose));
	}

	public static ITypeAdapterFactory<DoubleStream> defaultForDoubleStream = forDoubleStream(StreamTypeAdapter.forDoubleStream);

	public static ITypeAdapterFactory<DoubleStream> forDoubleStream(final TypeAdapter<DoubleStream> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(DoubleStream.class, typeResolver -> typeAdapter);
	}

	public static ITypeAdapterFactory<IntStream> defaultForIntStream = forIntStream(StreamTypeAdapter.forIntStream);

	public static ITypeAdapterFactory<IntStream> forIntStream(final TypeAdapter<IntStream> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(IntStream.class, typeResolver -> typeAdapter);
	}

	public static ITypeAdapterFactory<LongStream> defaultForLongStream = forLongStream(StreamTypeAdapter.forLongStream);

	public static ITypeAdapterFactory<LongStream> forLongStream(final TypeAdapter<LongStream> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(LongStream.class, typeResolver -> typeAdapter);
	}

}
