package lsh.ext.gson.merge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Merger
		implements IMerger {

	private final Gson gson;
	private final Collection<IMergeTypeAdapterMapper> mappers;

	/**
	 * @param gson Gson instance
	 *
	 * @return An instance of {@link Merger}.
	 */
	public static IMerger getInstance(final Gson gson) {
		final Collection<IMergeTypeAdapterMapper> mappers = new ArrayList<>();
		mappers.add(MapMergeTypeAdapterMapper.getInstance());
		mappers.add(ReflectiveMergeTypeAdapterMapper.getInstance());
		return new Merger(gson, mappers);
	}

	@Override
	public <T> T merge(final T instance, final Function<? super Gson, ? extends T> extractor) {
		final Gson mergingGson = new GsonBuilder()
				.registerTypeAdapterFactory(MergingTypeAdapterFactory.getInstance(instance, gson, mappers))
				.create();
		return extractor.apply(mergingGson);
	}

	private static final class MergingTypeAdapterFactory
			implements TypeAdapterFactory {

		private final Object instance;
		private final Gson gson;
		private final Iterable<? extends IMergeTypeAdapterMapper> mappers;

		private MergingTypeAdapterFactory(final Object instance, final Gson gson, final Iterable<? extends IMergeTypeAdapterMapper> mappers) {
			this.instance = instance;
			this.gson = gson;
			this.mappers = mappers;
		}

		private static TypeAdapterFactory getInstance(final Object instance, final Gson gson, final Iterable<? extends IMergeTypeAdapterMapper> mappers) {
			return new MergingTypeAdapterFactory(instance, gson, mappers);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson mergingGson, final TypeToken<T> typeToken) {
			final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
			for ( final IMergeTypeAdapterMapper mapper : mappers ) {
				@Nullable
				final TypeAdapter<T> mappedTypeAdapter = mapper.map(typeAdapter, instance, gson, typeToken);
				if ( mappedTypeAdapter != null ) {
					@SuppressWarnings("unchecked")
					final T castInstance = (T) instance;
					return new BoundTypeAdapter<>(castInstance, mappedTypeAdapter);
				}
			}
			return null;
		}

	}

	private static final class BoundTypeAdapter<T>
			extends TypeAdapter<T> {

		private final T instanceToReturn;
		private final TypeAdapter<T> mappedTypeAdapter;

		private BoundTypeAdapter(final T instanceToReturn, final TypeAdapter<T> mappedTypeAdapter) {
			this.instanceToReturn = instanceToReturn;
			this.mappedTypeAdapter = mappedTypeAdapter;
		}

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			mappedTypeAdapter.write(out, value);
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			mappedTypeAdapter.read(in);
			return instanceToReturn;
		}

	}

}
