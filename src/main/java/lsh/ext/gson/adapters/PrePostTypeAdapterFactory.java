package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import static java.util.Collections.singleton;

/**
 * Represents a pre/post type adapter factory to perform pre/post checks for a serializable/deserializable values respectively.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class PrePostTypeAdapterFactory
		implements TypeAdapterFactory {

	private final Iterable<? extends IPrePostProcessorFactory<?>> processorFactories;

	private PrePostTypeAdapterFactory(final Iterable<? extends IPrePostProcessorFactory<?>> processorFactories) {
		this.processorFactories = processorFactories;
	}

	/**
	 * @param processorFactories A sequence of processor factories.
	 *
	 * @return A {@link PrePostTypeAdapterFactory} instance.
	 *
	 * @see #getPrePostTypeAdapterFactory(IPrePostProcessorFactory)
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory getPrePostTypeAdapterFactory(final Iterable<? extends IPrePostProcessorFactory<?>> processorFactories) {
		return new PrePostTypeAdapterFactory(processorFactories);
	}

	/**
	 * @param processorFactory A single processor factory.
	 *
	 * @return A {@link PrePostTypeAdapterFactory} instance.
	 *
	 * @see #getPrePostTypeAdapterFactory(Iterable)
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory getPrePostTypeAdapterFactory(final IPrePostProcessorFactory<?> processorFactory) {
		return new PrePostTypeAdapterFactory(singleton(processorFactory));
	}

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		Collection<IPrePostProcessor<?>> processors = null;
		for ( final IPrePostProcessorFactory<?> factory : processorFactories ) {
			if ( factory.supports(typeToken) ) {
				if ( processors == null ) {
					processors = new ArrayList<>();
				}
				processors.add(factory.createProcessor());
			}
		}
		if ( processors == null ) {
			return null;
		}
		final TypeAdapter<T> delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Iterable<? extends IPrePostProcessor<? super T>> castProcessors = (Iterable) processors;
		return new PrePostTypeAdapter<>(castProcessors, delegateTypeAdapter);
	}

	private static final class PrePostTypeAdapter<T>
			extends TypeAdapter<T> {

		private final Iterable<? extends IPrePostProcessor<? super T>> processors;
		private final TypeAdapter<T> delegateTypeAdapter;

		private PrePostTypeAdapter(final Iterable<? extends IPrePostProcessor<? super T>> processors,
				final TypeAdapter<T> delegateTypeAdapter) {
			this.processors = processors;
			this.delegateTypeAdapter = delegateTypeAdapter;
		}

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			for ( final IPrePostProcessor<? super T> processor : processors ) {
				processor.pre(value);
			}
			delegateTypeAdapter.write(out, value);
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			final T value = delegateTypeAdapter.read(in);
			for ( final IPrePostProcessor<? super T> processor : processors ) {
				processor.post(value);
			}
			return value;
		}

	}

}
