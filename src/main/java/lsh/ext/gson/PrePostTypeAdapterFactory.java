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

/**
 * Represents a pre/post type adapter factory to perform pre/post checks for a serializable/deserializable values respectively.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrePostTypeAdapterFactory
		implements TypeAdapterFactory {

	private final Iterable<? extends IProcessorFactory<?>> preProcessorFactories;
	private final Iterable<? extends IProcessorFactory<?>> postProcessorFactories;

	/**
	 * @param preProcessorFactories
	 * 		A sequence of pre processor factories.
	 * @param postProcessorFactories
	 * 		A sequence of post processor factories.
	 *
	 * @return A {@link PrePostTypeAdapterFactory} instance.
	 */
	public static TypeAdapterFactory getInstance(final Iterable<? extends IProcessorFactory<?>> preProcessorFactories,
			final Iterable<? extends IProcessorFactory<?>> postProcessorFactories) {
		return new PrePostTypeAdapterFactory(preProcessorFactories, postProcessorFactories);
	}

	@Override
	@Nullable
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		@Nullable
		Collection<IProcessor<?>> preProcessors = null;
		for ( final IProcessorFactory<?> factory : preProcessorFactories ) {
			@Nullable
			final IProcessor<?> processor = factory.createProcessor(typeToken);
			if ( processor == null ) {
				continue;
			}
			if ( preProcessors == null ) {
				preProcessors = new ArrayList<>();
			}
			preProcessors.add(processor);
		}
		@Nullable
		Collection<IProcessor<?>> postProcessors = null;
		for ( final IProcessorFactory<?> factory : postProcessorFactories ) {
			@Nullable
			final IProcessor<?> processor = factory.createProcessor(typeToken);
			if ( processor == null ) {
				continue;
			}
			if ( postProcessors == null ) {
				postProcessors = new ArrayList<>();
			}
			postProcessors.add(processor);
		}
		if ( preProcessors == null && postProcessors == null ) {
			return null;
		}
		final TypeAdapter<T> delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Iterable<? extends IProcessor<? super T>> castPreProcessors = (Iterable) preProcessors;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Iterable<? extends IProcessor<? super T>> castPostProcessors = (Iterable) postProcessors;
		return new PrePostTypeAdapter<>(castPreProcessors, castPostProcessors, delegateTypeAdapter);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class PrePostTypeAdapter<T>
			extends TypeAdapter<T> {

		private final Iterable<? extends IProcessor<? super T>> preProcessors;
		private final Iterable<? extends IProcessor<? super T>> postProcessors;
		private final TypeAdapter<T> delegateTypeAdapter;

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			for ( final IProcessor<? super T> processor : preProcessors ) {
				processor.process(value);
			}
			delegateTypeAdapter.write(out, value);
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			final T value = delegateTypeAdapter.read(in);
			for ( final IProcessor<? super T> processor : postProcessors ) {
				processor.process(value);
			}
			return value;
		}

	}

}
