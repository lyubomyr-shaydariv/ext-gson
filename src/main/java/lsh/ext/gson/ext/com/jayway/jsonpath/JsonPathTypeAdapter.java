package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonPathTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Configuration configuration;
	private final TypeAdapter<T> delegateAdapter;
	private final TypeAdapter<? extends JsonElement> jsonElementTypeAdapter;
	private final Item<?>[] items;

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		delegateAdapter.write(out, value);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final JsonElement superElement = jsonElementTypeAdapter.read(in);
		final T superValue = delegateAdapter.fromJsonTree(superElement);
		for ( final Item<?> item : items ) {
			try {
				final JsonElement foundSubElement = item.jsonPath.read(superElement, configuration);
				final TypeAdapter<?> typeAdapter = item.typeAdapter;
				final Object subValue = typeAdapter.fromJsonTree(foundSubElement);
				item.accessor.assign(superValue, subValue);
			} catch ( final JsonPathException ignored ) {
				// do nothing
			}
		}
		return superValue;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<S>
			implements ITypeAdapterFactory<Object> {

		private final Function<? super Gson, ? extends Configuration> provideConfiguration;
		private final Function<? super TypeToken<?>, ? extends Collection<? extends S>> provideSources;
		private final IAccessor.IFactory<S> accessorsFactory;

		public static <T> ITypeAdapterFactory<?> getInstance(
				final Function<? super Gson, ? extends Configuration> provideConfiguration,
				final Function<? super TypeToken<?>, ? extends Collection<? extends T>> provideSources,
				final IAccessor.IFactory<T> accessorsFactory
		) {
			return new Factory<>(provideConfiguration, provideSources, accessorsFactory);
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			final Collection<? extends S> sources = provideSources.apply(typeToken);
			final Collection<IAccessor<? super Object, ? super Object>> accessors = accessorsFactory.create(sources);
			if ( accessors.isEmpty() ) {
				return null;
			}
			return new JsonPathTypeAdapter<>(
					provideConfiguration.apply(gson),
					gson.getDelegateAdapter(this, typeToken),
					gson.getAdapter(JsonElement.class),
					accessors.stream()
							.map(accessor -> new Item<>(accessor, accessor.getJsonPath(), gson.getAdapter(TypeToken.get(accessor.getType()))))
							.toArray(Item[]::new)
			);
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Item<T> {

		private final IAccessor<? super Object, ? super Object> accessor;
		private final JsonPath jsonPath;
		private final TypeAdapter<? super T> typeAdapter;

	}

}
