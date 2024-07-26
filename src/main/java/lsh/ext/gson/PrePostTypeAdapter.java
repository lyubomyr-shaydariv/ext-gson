package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
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
	private final Iterable<? extends IPreProcessor<? super T>> preProcessors;
	@Nullable
	private final Iterable<? extends IPostProcessor<? super T>> postProcessors;
	private final TypeAdapter<T> delegateTypeAdapter;

	public static <T> TypeAdapter<T> getInstance(
			@Nullable final Iterable<? extends IPreProcessor<? super T>> preProcessors,
			@Nullable final Iterable<? extends IPostProcessor<? super T>> postProcessors,
			final TypeAdapter<T> delegateTypeAdapter
	) {
		return new PrePostTypeAdapter<>(preProcessors, postProcessors, delegateTypeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		if ( preProcessors != null ) {
			for ( final IPreProcessor<? super T> processor : preProcessors ) {
				processor.preProcess(value);
			}
		}
		delegateTypeAdapter.write(out, value);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final T value = delegateTypeAdapter.read(in);
		if ( postProcessors != null ) {
			for ( final IPostProcessor<? super T> processor : postProcessors ) {
				processor.postProcess(value);
			}
		}
		return value;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		private final Iterable<? extends IPreProcessor.IFactory<?>> preProcessorFactories;
		private final Iterable<? extends IPostProcessor.IFactory<?>> postProcessorFactories;

		public static TypeAdapterFactory getInstance(
				final Iterable<? extends IPreProcessor.IFactory<?>> preProcessorFactories,
				final Iterable<? extends IPostProcessor.IFactory<?>> postProcessorFactories
		) {
			return new Factory(preProcessorFactories, postProcessorFactories);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			final Type type = typeToken.getType();
			@Nullable
			final Collection<? extends IPreProcessor<?>> preProcessors = getPreProcessors(type, preProcessorFactories);
			@Nullable
			final Collection<? extends IPostProcessor<?>> postProcessors = getPostProcessors(type, postProcessorFactories);
			if ( preProcessors == null && postProcessors == null ) {
				return null;
			}
			final TypeAdapter<T> delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken);
			@Nullable
			@SuppressWarnings("unchecked")
			final Iterable<IPreProcessor<? super T>> castPreProcessors = preProcessors != null ? (Iterable<IPreProcessor<? super T>>) preProcessors : null;
			@Nullable
			@SuppressWarnings("unchecked")
			final Iterable<IPostProcessor<? super T>> castPostProcessors = postProcessors != null ? (Iterable<IPostProcessor<? super T>>) postProcessors : null;
			return PrePostTypeAdapter.getInstance(castPreProcessors, castPostProcessors, delegateTypeAdapter);
		}

		@Nullable
		private static Collection<IPreProcessor<?>> getPreProcessors(final Type type, @Nullable final Iterable<? extends IPreProcessor.IFactory<?>> factories) {
			if ( factories == null ) {
				return null;
			}
			Collection<IPreProcessor<?>> preProcessors = null;
			for ( final IPreProcessor.IFactory<?> factory : factories ) {
				@Nullable
				final IPreProcessor<?> preProcessor = factory.createPreProcessor(type);
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

		@Nullable
		private static Collection<IPostProcessor<?>> getPostProcessors(final Type type, @Nullable final Iterable<? extends IPostProcessor.IFactory<?>> factories) {
			if ( factories == null ) {
				return null;
			}
			@Nullable
			Collection<IPostProcessor<?>> postProcessors = null;
			for ( final IPostProcessor.IFactory<?> factory : factories ) {
				@Nullable
				final IPostProcessor<?> postProcessor = factory.createPostProcessor(type);
				if ( postProcessor == null ) {
					continue;
				}
				if ( postProcessors == null ) {
					postProcessors = new ArrayList<>();
				}
				postProcessors.add(postProcessor);
			}
			return postProcessors;
		}

	}

	public interface IPreProcessor<T> {

		void preProcess(T input);

		interface IFactory<T> {

			@Nullable
			IPreProcessor<T> createPreProcessor(Type type);

		}

	}

	public interface IPostProcessor<T> {

		void postProcess(T input);

		interface IFactory<T> {

			@Nullable
			IPostProcessor<T> createPostProcessor(Type type);

		}

	}

}
