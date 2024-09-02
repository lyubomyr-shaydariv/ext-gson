package lsh.ext.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPolymorphicTypeAdapter<T>
		extends TypeAdapter<T> {

	private final String property;
	private final Function<? super Class<? extends T>, String> classToDiscriminator;
	private final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter;
	private final Function<? super String, ? extends TypeAdapter<? extends T>> discriminatorToTypeAdapter;

	protected abstract void write(JsonWriter out, T value, TypeAdapter<? super T> typeAdapter, String property, String discriminator)
			throws IOException;

	protected abstract T read(JsonReader in, String property)
			throws IOException;

	@Nullable
	protected final TypeAdapter<? extends T> discriminatorToTypeAdapter(final String discriminator) {
		return discriminatorToTypeAdapter.apply(discriminator);
	}

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		@SuppressWarnings("unchecked")
		final Class<? extends T> klass = (Class<? extends T>) value.getClass();
		@Nullable
		final String discriminator = classToDiscriminator.apply(klass);
		if ( discriminator == null ) {
			throw new IllegalArgumentException("No discriminator specified for " + klass);
		}
		@Nullable
		final TypeAdapter<? super T> typeAdapter = classToTypeAdapter.apply(klass);
		if ( typeAdapter == null ) {
			throw new IllegalArgumentException(String.format("No type adapter found by discriminator %s and class %s", discriminator, klass));
		}
		write(out, value, typeAdapter, property, discriminator);
	}

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		return read(in, property);
	}

	public abstract static class AbstractFactory<T>
			extends AbstractRawClassTypeAdapterFactory<T> {

		private final Iterable<Class<? extends T>> classes;
		private final String property;
		private final Function<? super Class<? extends T>, String> classToDiscriminator;

		protected AbstractFactory(
				final Class<T> klass,
				final Iterable<Class<? extends T>> classes,
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator
		) {
			super(klass);
			this.classes = classes;
			this.property = property;
			this.classToDiscriminator = classToDiscriminator;
		}

		protected abstract TypeAdapter<T> createTypeAdapter(
				Gson gson,
				TypeToken<? super T> typeToken,
				Iterable<Class<? extends T>> classes,
				String property,
				Function<? super Class<? extends T>, String> classToDiscriminator
		);

		@Override
		protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<? super T> typeToken) {
			return createTypeAdapter(gson, typeToken, classes, property, classToDiscriminator);
		}

	}

	public static final class Streamed<T>
			extends AbstractPolymorphicTypeAdapter<T> {

		private Streamed(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> discriminatorToTypeAdapter
		) {
			super(property, classToDiscriminator, classToTypeAdapter, discriminatorToTypeAdapter);
		}

		public static <T> TypeAdapter<T> getInstance(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> discriminatorToTypeAdapter
		) {
			return new Streamed<>(property, classToDiscriminator, classToTypeAdapter, discriminatorToTypeAdapter)
					.nullSafe();
		}

		@Override
		protected void write(final JsonWriter out, final T value, final TypeAdapter<? super T> typeAdapter, final String property, final String discriminator)
				throws IOException {
			final JsonWriter outDelegate = new AbstractDelegateJsonWriter(out) {
				private boolean isPropertyWritten;

				@Override
				@SuppressWarnings("resource")
				public JsonWriter beginObject()
						throws IOException {
					super.beginObject();
					if ( !isPropertyWritten ) {
						name(property);
						value(discriminator);
						isPropertyWritten = true;
					}
					return this;
				}
			};
			typeAdapter.write(outDelegate, value);
		}

		@Override
		protected T read(final JsonReader in, final String property)
				throws IOException {
			in.beginObject();
			final String actualProperty = in.nextName();
			if ( !actualProperty.equals(property) ) {
				throw new JsonParseException(String.format("Expected %s but was %s", property, actualProperty));
			}
			final String discriminator = in.nextString();
			@Nullable
			final TypeAdapter<? extends T> typeAdapter = discriminatorToTypeAdapter(discriminator);
			if ( typeAdapter == null ) {
				throw new JsonParseException("Type adapter not found for discriminator: " + discriminator);
			}
			final JsonReader delegateIn = new AbstractDelegateJsonReader(in) {
				private boolean isPropertyRead;

				@Override
				public void beginObject()
						throws IOException {
					if ( !isPropertyRead ) {
						isPropertyRead = true;
						return;
					}
					super.beginObject();
				}
			};
			return typeAdapter.read(delegateIn);
		}

		public static final class Factory<T>
				extends AbstractFactory<T> {

			private Factory(
					final Class<T> klass,
					final Iterable<Class<? extends T>> classes,
					final String property,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				super(klass, classes, property, classToDiscriminator);
			}

			public static <T> ITypeAdapterFactory<T> getInstance(
					final Class<T> klass,
					final Iterable<Class<? extends T>> classes,
					final String property,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				return new Factory<>(klass, classes, property, classToDiscriminator);
			}

			@Override
			protected TypeAdapter<T> createTypeAdapter(
					final Gson gson,
					final TypeToken<? super T> typeToken,
					final Iterable<Class<? extends T>> classes,
					final String property,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				final Map<Class<? extends T>, TypeAdapter<? super T>> classToTypeAdapterMap = new HashMap<>();
				final Map<String, TypeAdapter<? extends T>> discriminatorToTypeAdapterMap = new HashMap<>();
				AbstractPolymorphicTypeAdapter.<T>fill(gson, this, classes, classToDiscriminator, classToTypeAdapterMap::put, discriminatorToTypeAdapterMap::put);
				return Streamed.getInstance(property, classToDiscriminator, classToTypeAdapterMap::get, discriminatorToTypeAdapterMap::get);
			}

		}

	}

	public static final class JsonTree<T>
			extends AbstractPolymorphicTypeAdapter<T> {

		private final TypeAdapter<? super JsonElement> jsonElementTypeAdapter;

		private JsonTree(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> propertyToTypeAdapter,
				final TypeAdapter<? super JsonElement> jsonElementTypeAdapter
		) {
			super(property, classToDiscriminator, classToTypeAdapter, propertyToTypeAdapter);
			this.jsonElementTypeAdapter = jsonElementTypeAdapter;
		}

		public static <T> TypeAdapter<T> getInstance(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> discriminatorToTypeAdapter,
				final TypeAdapter<? super JsonElement> jsonElementTypeAdapter
		) {
			return new JsonTree<>(property, classToDiscriminator, classToTypeAdapter, discriminatorToTypeAdapter, jsonElementTypeAdapter)
					.nullSafe();
		}

		@Override
		protected void write(final JsonWriter out, final T value, final TypeAdapter<? super T> typeAdapter, final String property, final String discriminator)
				throws IOException {
			final JsonObject jsonObject = (JsonObject) typeAdapter.toJsonTree(value);
			final JsonObject jsonObjectWithDiscriminator = new JsonObject(); // this is needed to interop between the buffered and the streamed implementations
			jsonObjectWithDiscriminator.addProperty(property, discriminator);
			jsonObjectWithDiscriminator.asMap().putAll(jsonObject.asMap());
			jsonElementTypeAdapter.write(out, jsonObjectWithDiscriminator);
		}

		@Override
		protected T read(final JsonReader in, final String property)
				throws IOException {
			final JsonObject jsonObjectWithDiscriminator = (JsonObject) jsonElementTypeAdapter.read(in);
			if ( !jsonObjectWithDiscriminator.has(property) ) {
				throw new IllegalArgumentException("Discriminator property not found: " + property);
			}
			final JsonElement propertyElement = jsonObjectWithDiscriminator.get(property);
			if ( propertyElement.isJsonNull() ) {
				throw new IllegalArgumentException("Discriminator property not found: " + property);
			}
			final String discriminator = propertyElement.getAsString();
			@Nullable
			final TypeAdapter<? extends T> typeAdapter = discriminatorToTypeAdapter(discriminator);
			if ( typeAdapter == null ) {
				throw new IllegalArgumentException("Type adapter not found for discriminator " + discriminator);
			}
			return typeAdapter.fromJsonTree(jsonObjectWithDiscriminator);
		}

		public static final class Factory<T>
				extends AbstractFactory<T> {

			private Factory(
					final Class<T> klass,
					final Iterable<Class<? extends T>> classes,
					final String propertyProperty,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				super(klass, classes, propertyProperty, classToDiscriminator);
			}

			public static <T> ITypeAdapterFactory<T> getInstance(
					final Class<T> klass,
					final Iterable<Class<? extends T>> classes,
					final String property,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				return new Factory<>(klass, classes, property, classToDiscriminator);
			}

			@Override
			protected TypeAdapter<T> createTypeAdapter(
					final Gson gson,
					final TypeToken<? super T> typeToken,
					final Iterable<Class<? extends T>> classes,
					final String property,
					final Function<? super Class<? extends T>, String> classToDiscriminator
			) {
				final TypeAdapter<JsonElement> jsonElementTypeAdapter = gson.getAdapter(JsonElement.class);
				final Map<Class<? extends T>, TypeAdapter<? super T>> classToTypeAdapterMap = new HashMap<>();
				final Map<String, TypeAdapter<? extends T>> discriminatorToTypeAdapterMap = new HashMap<>();
				AbstractPolymorphicTypeAdapter.<T>fill(gson, this, classes, classToDiscriminator, classToTypeAdapterMap::put, discriminatorToTypeAdapterMap::put);
				return JsonTree.getInstance(property, classToDiscriminator, classToTypeAdapterMap::get, discriminatorToTypeAdapterMap::get, jsonElementTypeAdapter);
			}

		}

	}

	private static <T> void fill(
			final Gson gson,
			final TypeAdapterFactory skipPast,
			final Iterable<Class<? extends T>> classes,
			final Function<? super Class<? extends T>, String> classToDiscriminator,
			final BiConsumer<? super Class<? extends T>, ? super TypeAdapter<? super T>> putClassToTypeAdapter,
			final BiConsumer<? super String, ? super TypeAdapter<? extends T>> putDiscriminatorToTypeAdapter
	) {
		for ( final Class<? extends T> klass : classes ) {
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> delegateTypeAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(skipPast, TypeToken.get(klass));
			putClassToTypeAdapter.accept(klass, delegateTypeAdapter);
			final String discriminator = classToDiscriminator.apply(klass);
			putDiscriminatorToTypeAdapter.accept(discriminator, delegateTypeAdapter);
		}
	}

}
