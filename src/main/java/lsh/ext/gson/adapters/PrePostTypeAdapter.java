package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrePostTypeAdapter<T>
		extends TypeAdapter<T> {

	@Nullable
	private final Iterable<? extends Consumer<? super T>> preProcessors;
	@Nullable
	private final Iterable<? extends Consumer<? super T>> postProcessors;
	private final TypeAdapter<T> delegateTypeAdapter;

	public static <T> TypeAdapter<T> getInstance(
			@Nullable final Iterable<? extends Consumer<? super T>> preProcessors,
			@Nullable final Iterable<? extends Consumer<? super T>> postProcessors,
			final TypeAdapter<T> delegateTypeAdapter
	) {
		return new PrePostTypeAdapter<>(preProcessors, postProcessors, delegateTypeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		if ( preProcessors != null ) {
			for ( final Consumer<? super T> processor : preProcessors ) {
				processor.accept(value);
			}
		}
		delegateTypeAdapter.write(out, value);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final T value = delegateTypeAdapter.read(in);
		if ( postProcessors != null ) {
			for ( final Consumer<? super T> processor : postProcessors ) {
				processor.accept(value);
			}
		}
		return value;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<TT>
			implements TypeAdapterFactory {

		private final Iterable<? extends Function<Type, Consumer<TT>>> preProcessorFactories;
		private final Iterable<? extends Function<Type, Consumer<TT>>> postProcessorFactories;

		public static <TT> TypeAdapterFactory getInstance(
				final Iterable<? extends Function<Type, Consumer<TT>>> preProcessorFactories,
				final Iterable<? extends Function<Type, Consumer<TT>>> postProcessorFactories
		) {
			return new Factory<>(preProcessorFactories, postProcessorFactories);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			final Type type = typeToken.getType();
			@Nullable
			final Collection<? extends Consumer<TT>> preProcessors = getProcessors(type, preProcessorFactories);
			@Nullable
			final Collection<? extends Consumer<TT>> postProcessors = getProcessors(type, postProcessorFactories);
			if ( preProcessors == null && postProcessors == null ) {
				return null;
			}
			final TypeAdapter<T> delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken);
			@Nullable
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Iterable<Consumer<? super T>> castPreProcessors = preProcessors != null ? (Iterable) preProcessors : null;
			@Nullable
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Iterable<Consumer<? super T>> castPostProcessors = postProcessors != null ? (Iterable) postProcessors : null;
			return PrePostTypeAdapter.getInstance(castPreProcessors, castPostProcessors, delegateTypeAdapter);
		}

		@Nullable
		private static <TT> Collection<Consumer<TT>> getProcessors(final Type type, @Nullable final Iterable<? extends Function<Type, Consumer<TT>>> factories) {
			if ( factories == null ) {
				return null;
			}
			Collection<Consumer<TT>> preProcessors = null;
			for ( final Function<Type, Consumer<TT>> factory : factories ) {
				@Nullable
				final Consumer<TT> preProcessor = factory.apply(type);
				if ( preProcessor == null ) {
					continue;
				}
				if ( preProcessors == null ) {
					preProcessors = new ArrayList<>();
				}
				preProcessors.add(preProcessor);
			}
			return preProcessors;
		}

	}

}
