package lsh.ext.gson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillCloseWhenClosed;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

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

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	public abstract static class AbstractFactory<K>
			implements ITypeAdapterFactory<K> {

		private final Class<K> klass;
		private final Iterable<Class<? extends K>> classes;
		private final String property;
		private final Function<? super Class<? extends K>, String> classToDiscriminator;

		protected abstract TypeAdapter<K> createTypeAdapter(
				Gson gson,
				TypeToken<? super K> typeToken,
				Iterable<Class<? extends K>> classes,
				String property,
				Function<? super Class<? extends K>, String> classToDiscriminator
		);

		@Override
		@Nullable
		public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			if ( typeToken.getRawType() != klass ) {
				return null;
			}
			@SuppressWarnings("unchecked")
			final TypeToken<K> castTypeToken = (TypeToken<K>) typeToken;
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter(gson, castTypeToken, classes, property, classToDiscriminator);
			return castTypeAdapter;
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

		private static final TypeAdapter<? super JsonElement> jsonElementTypeAdapter = TypeAdapters.getJsonElementTypeAdapter();

		private JsonTree(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> propertyToTypeAdapter
		) {
			super(property, classToDiscriminator, classToTypeAdapter, propertyToTypeAdapter);
		}

		public static <T> TypeAdapter<T> getInstance(
				final String property,
				final Function<? super Class<? extends T>, String> classToDiscriminator,
				final Function<? super Class<? extends T>, ? extends TypeAdapter<? super T>> classToTypeAdapter,
				final Function<? super String, ? extends TypeAdapter<? extends T>> discriminatorToTypeAdapter
		) {
			return new JsonTree<>(property, classToDiscriminator, classToTypeAdapter, discriminatorToTypeAdapter)
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
				final Map<Class<? extends T>, TypeAdapter<? super T>> classToTypeAdapterMap = new HashMap<>();
				final Map<String, TypeAdapter<? extends T>> discriminatorToTypeAdapterMap = new HashMap<>();
				AbstractPolymorphicTypeAdapter.<T>fill(gson, this, classes, classToDiscriminator, classToTypeAdapterMap::put, discriminatorToTypeAdapterMap::put);
				return JsonTree.getInstance(property, classToDiscriminator, classToTypeAdapterMap::get, discriminatorToTypeAdapterMap::get);
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

	@SuppressWarnings({ "AbstractClassExtendsConcreteClass", "AbstractClassWithOnlyOneDirectInheritor", "ClassWithoutNoArgConstructor" })
	private abstract static class AbstractDelegateJsonReader
			extends JsonReader {

		private static final Reader neverReader = new Reader() {
			// @formatter:off
			@Override public int read(@Nonnull final char[] buffer, final int offset, final int length) { throw new AssertionError(); }
			@Override public void close() { throw new AssertionError(); }
			// @formatter:on
		};

		private final JsonReader in;

		@SuppressWarnings("WeakerAccess")
		private AbstractDelegateJsonReader(@WillCloseWhenClosed final JsonReader in) {
			super(neverReader);
			this.in = in;
		}

		// @formatter:off
		@Override @SuppressWarnings("all") public void beginArray() throws IOException { in.beginArray(); }
		@Override @SuppressWarnings("all") public void endArray() throws IOException { in.endArray(); }
		@Override @SuppressWarnings("all") public void beginObject() throws IOException { in.beginObject(); }
		@Override @SuppressWarnings("all") public void endObject() throws IOException { in.endObject(); }
		@Override @SuppressWarnings("all") public boolean hasNext() throws IOException { return in.hasNext(); }
		@Override @SuppressWarnings("all") public JsonToken peek() throws IOException { return in.peek(); }
		@Override @SuppressWarnings("all") public String nextName() throws IOException { return in.nextName(); }
		@Override @SuppressWarnings("all") public String nextString() throws IOException { return in.nextString(); }
		@Override @SuppressWarnings("all") public boolean nextBoolean() throws IOException { return in.nextBoolean(); }
		@Override @SuppressWarnings("all") public void nextNull() throws IOException { in.nextNull(); }
		@Override @SuppressWarnings("all") public double nextDouble() throws IOException { return in.nextDouble(); }
		@Override @SuppressWarnings("all") public long nextLong() throws IOException { return in.nextLong(); }
		@Override @SuppressWarnings("all") public int nextInt() throws IOException { return in.nextInt(); }
		@Override @SuppressWarnings("all") public void close() throws IOException { in.close(); }
		@Override @SuppressWarnings("all") public void skipValue() throws IOException { in.skipValue(); }
		@Override @SuppressWarnings("all") public String toString() { return in.toString(); }
		@Override @SuppressWarnings("all") public String getPath() { return in.getPath(); }
		@Override @SuppressWarnings("all") public String getPreviousPath() { return in.getPreviousPath(); }
		// @formatter:on

	}

	@SuppressWarnings({ "AbstractClassExtendsConcreteClass", "AbstractClassWithOnlyOneDirectInheritor", "ClassWithoutNoArgConstructor" })
	private abstract static class AbstractDelegateJsonWriter
			extends JsonWriter {

		private static final Writer neverWriter = new Writer() {
			// @formatter:off
			@Override public void write(@Nonnull final char[] buffer, final int offset, final int length) { throw new AssertionError(); }
			@Override public void flush() { throw new AssertionError(); }
			@Override public void close() { throw new AssertionError(); }
			// @formatter:on
		};

		private final JsonWriter out;

		@SuppressWarnings("WeakerAccess")
		private AbstractDelegateJsonWriter(@WillCloseWhenClosed final JsonWriter out) {
			super(neverWriter);
			this.out = out;
		}

		// @formatter:off
		@Override @SuppressWarnings("all") public boolean isLenient() { return out.isLenient(); }
		@Override @SuppressWarnings("all") public JsonWriter beginArray() throws IOException { return out.beginArray(); }
		@Override @SuppressWarnings("all") public JsonWriter endArray() throws IOException { return out.endArray(); }
		@Override @SuppressWarnings("all") public JsonWriter beginObject() throws IOException { return out.beginObject(); }
		@Override @SuppressWarnings("all") public JsonWriter endObject() throws IOException { return out.endObject(); }
		@Override @SuppressWarnings("all") public JsonWriter name(final String name) throws IOException { return out.name(name); }
		@Override @SuppressWarnings("all") public JsonWriter value(final String value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final boolean value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final Boolean value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final float value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final double value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final long value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter value(final Number value) throws IOException { return out.value(value); }
		@Override @SuppressWarnings("all") public JsonWriter nullValue() throws IOException { return out.nullValue(); }
		@Override @SuppressWarnings("all") public JsonWriter jsonValue(final String value) throws IOException { return out.jsonValue(value); }
		@Override @SuppressWarnings("all") public void flush() throws IOException { out.flush(); }
		@Override @SuppressWarnings("all") public void close() throws IOException { out.close(); }
		// @formatter:on

	}

}
