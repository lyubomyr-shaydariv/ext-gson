package lsh.ext.gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
	private final Iterable<? extends IProcessor<? super T>> preProcessors;
	@Nullable
	private final Iterable<? extends IProcessor<? super T>> postProcessors;
	private final TypeAdapter<T> delegateTypeAdapter;

	public static <T> TypeAdapter<T> getInstance(
			@Nullable final Iterable<? extends IProcessor<? super T>> preProcessors,
			@Nullable final Iterable<? extends IProcessor<? super T>> postProcessors,
			final TypeAdapter<T> delegateTypeAdapter) {
		return new PrePostTypeAdapter<>(preProcessors, postProcessors, delegateTypeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		if ( preProcessors != null ) {
			for ( final IProcessor<? super T> processor : preProcessors ) {
				processor.process(value);
			}
		}
		delegateTypeAdapter.write(out, value);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final T value = delegateTypeAdapter.read(in);
		if ( postProcessors != null ) {
			for ( final IProcessor<? super T> processor : postProcessors ) {
				processor.process(value);
			}
		}
		return value;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		private final Iterable<? extends IProcessorFactory<?>> preProcessorFactories;
		private final Iterable<? extends IProcessorFactory<?>> postProcessorFactories;

		public static TypeAdapterFactory getInstance(final Iterable<? extends IProcessorFactory<?>> preProcessorFactories,
				final Iterable<? extends IProcessorFactory<?>> postProcessorFactories) {
			return new Factory(preProcessorFactories, postProcessorFactories);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			@Nullable
			final Iterable<? extends IProcessor<?>> preProcessors = getProcessors(typeToken, preProcessorFactories);
			@Nullable
			final Iterable<? extends IProcessor<?>> postProcessors = getProcessors(typeToken, postProcessorFactories);
			if ( preProcessors == null && postProcessors == null ) {
				return null;
			}
			final TypeAdapter<T> delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken);
			@SuppressWarnings("unchecked")
			@Nullable
			final Iterable<IProcessor<? super T>> castPreProcessors = (Iterable<IProcessor<? super T>>) preProcessors;
			@SuppressWarnings("unchecked")
			@Nullable
			final Iterable<IProcessor<? super T>> castPostProcessors = (Iterable<IProcessor<? super T>>) postProcessors;
			return PrePostTypeAdapter.getInstance(castPreProcessors, castPostProcessors, delegateTypeAdapter);
		}

		@Nullable
		private static Iterable<? extends IProcessor<?>> getProcessors(final TypeToken<?> typeToken, final Iterable<? extends IProcessorFactory<?>> factories) {
			@Nullable
			Collection<IProcessor<?>> processors = null;
			for ( final IProcessorFactory<?> factory : factories ) {
				@Nullable
				final IProcessor<?> processor = factory.createProcessor(typeToken);
				if ( processor == null ) {
					continue;
				}
				if ( processors == null ) {
					processors = new ArrayList<>();
				}
				processors.add(processor);
			}
			return processors;
		}

	}

}
