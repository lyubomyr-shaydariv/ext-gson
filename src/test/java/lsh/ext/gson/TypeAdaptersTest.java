package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public final class TypeAdaptersTest {

	@Test
	public void testGetTypeAdapterOfTypeAdapter()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final TypeAdapter<?> typeAdapter = TypeAdapters.getTypeAdapterOf(VoidTypeAdapter.class);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.instanceOf(TypeAdapter.class));
		MatcherAssert.assertThat(typeAdapter, canBeRegisteredAsTypeAdapter());
	}

	@Test
	public void testGetTypeAdapterOfJsonSerializer()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final JsonSerializer<?> jsonSerializer = TypeAdapters.getTypeAdapterOf(VoidJsonSerializer.class);
		MatcherAssert.assertThat(jsonSerializer, CoreMatchers.instanceOf(JsonSerializer.class));
		MatcherAssert.assertThat(jsonSerializer, canBeRegisteredAsTypeAdapter());
	}

	@Test
	public void testGetTypeAdapterOfJsonDeserializer()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final JsonDeserializer<?> jsonDeserializer = TypeAdapters.getTypeAdapterOf(VoidJsonDeserializer.class);
		MatcherAssert.assertThat(jsonDeserializer, CoreMatchers.instanceOf(JsonDeserializer.class));
		MatcherAssert.assertThat(jsonDeserializer, canBeRegisteredAsTypeAdapter());
	}

	@Test
	public void testGetTypeAdapterOfInstanceCreator()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		final InstanceCreator<?> instanceCreator = TypeAdapters.getTypeAdapterOf(VoidInstanceCreator.class);
		MatcherAssert.assertThat(instanceCreator, CoreMatchers.instanceOf(InstanceCreator.class));
		MatcherAssert.assertThat(instanceCreator, canBeRegisteredAsTypeAdapter());
	}

	@Test(expected = InvocationTargetException.class)
	public void testGetTypeAdapterOfTypeAdapterWithInvocationTargetException()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeAdapterOf(VoidTypeAdapterWithInvocationTargetException.class);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGetTypeAdapterOfTypeAdapterWithNoSuchMethodException()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeAdapterOf(VoidTypeAdapterWithNoSuchMethodException.class);
	}

	@Test(expected = InstantiationException.class)
	public void testGetTypeAdapterOfTypeAdapterWithInstantiationException()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeAdapterOf(VoidTypeAdapterWithInstantiationException.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeAdapterOfIllegalClass()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeAdapterOf(Object.class);
	}

	@Test
	public void testGetTypeHierarchyAdapterOfTypeAdapter()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final TypeAdapter<?> typeAdapter = TypeAdapters.getTypeHierarchyAdapterOf(VoidTypeAdapter.class);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.instanceOf(TypeAdapter.class));
		MatcherAssert.assertThat(typeAdapter, canBeRegisteredAsTypeHierarchyAdapter());
	}

	@Test
	public void testGetTypeHierarchyAdapterOfJsonSerializer()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final JsonSerializer<?> jsonSerializer = TypeAdapters.getTypeHierarchyAdapterOf(VoidJsonSerializer.class);
		MatcherAssert.assertThat(jsonSerializer, CoreMatchers.instanceOf(JsonSerializer.class));
		MatcherAssert.assertThat(jsonSerializer, canBeRegisteredAsTypeHierarchyAdapter());
	}

	@Test
	public void testGetTypeHierarchyAdapterOfJsonDeserializer()
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final JsonDeserializer<?> jsonDeserializer = TypeAdapters.getTypeHierarchyAdapterOf(VoidJsonDeserializer.class);
		MatcherAssert.assertThat(jsonDeserializer, CoreMatchers.instanceOf(JsonDeserializer.class));
		MatcherAssert.assertThat(jsonDeserializer, canBeRegisteredAsTypeHierarchyAdapter());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeHierarchyAdapterOfInstanceCreator()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeHierarchyAdapterOf(VoidInstanceCreator.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeHierarchyAdapterOfIllegalClass()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		TypeAdapters.getTypeHierarchyAdapterOf(Object.class);
	}

	private static <T> Matcher<T> canBeRegisteredAsTypeAdapter() {
		return new AbstractTypeAdapterMatcher<T>(Arrays.asList(TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class, InstanceCreator.class)) {
			@Override
			protected void register(@Nonnull final GsonBuilder builder, @Nonnull final T typeAdapter) {
				builder.registerTypeAdapter(Void.class, typeAdapter);
			}
		};
	}

	private static <T> Matcher<T> canBeRegisteredAsTypeHierarchyAdapter() {
		return new AbstractTypeAdapterMatcher<T>(Arrays.asList(TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class)) {
			@Override
			protected void register(@Nonnull final GsonBuilder builder, @Nonnull final T typeAdapter) {
				builder.registerTypeHierarchyAdapter(Void.class, typeAdapter);
			}
		};
	}

	private abstract static class AbstractTypeAdapterMatcher<T>
			extends TypeSafeMatcher<T> {

		private final Iterable<Class<?>> supportedClasses;

		protected AbstractTypeAdapterMatcher(final Iterable<Class<?>> supportedClasses) {
			this.supportedClasses = supportedClasses;
		}

		protected abstract void register(@Nonnull GsonBuilder builder, @Nonnull T typeAdapter);

		@Override
		protected final boolean matchesSafely(final T typeAdapter) {
			try {
				final GsonBuilder gsonBuilder = new GsonBuilder();
				register(gsonBuilder, typeAdapter);
				gsonBuilder.create();
				return true;
			} catch ( Exception ex ) {
				return false;
			}
		}

		@Override
		public final void describeTo(final Description description) {
			description.appendText("an instance of " + supportedClasses);
		}

	}

	private static final class VoidTypeAdapter
			extends TypeAdapter<Void> {

		private VoidTypeAdapter() {
		}

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final Void value)
				throws IOException {
			out.nullValue();
		}

		@Override
		@Nullable
		public Void read(final JsonReader in)
				throws IOException {
			in.nextNull();
			return null;
		}

	}

	private static final class VoidTypeAdapterWithInvocationTargetException
			extends TypeAdapter<Void> {

		private VoidTypeAdapterWithInvocationTargetException() {
			throw new AssertionError();
		}

		@Override
		public void write(final JsonWriter out, final Void value) {
			throw new AssertionError();
		}

		@Override
		public Void read(final JsonReader in) {
			throw new AssertionError();
		}

	}

	private static final class VoidTypeAdapterWithNoSuchMethodException
			extends TypeAdapter<Void> {

		private VoidTypeAdapterWithNoSuchMethodException(@SuppressWarnings("unused") final Void value) {
			throw new AssertionError();
		}

		@Override
		public void write(final JsonWriter out, final Void value) {
			throw new AssertionError();
		}

		@Override
		public Void read(final JsonReader in) {
			throw new AssertionError();
		}

	}

	private abstract static class VoidTypeAdapterWithInstantiationException
			extends TypeAdapter<Void> {
	}

	private static final class VoidJsonSerializer
			implements JsonSerializer<Void> {

		private VoidJsonSerializer() {
		}

		@Override
		public JsonElement serialize(final Void noValue, final Type type, final JsonSerializationContext context) {
			return JsonNull.INSTANCE;
		}

	}

	private static final class VoidJsonDeserializer
			implements JsonDeserializer<Void> {

		private VoidJsonDeserializer() {
		}

		@Override
		@Nullable
		public Void deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context) {
			return null;
		}

	}

	private static class VoidInstanceCreator
			implements InstanceCreator<Void> {

		private VoidInstanceCreator() {
		}

		@Override
		@Nullable
		public Void createInstance(final Type type) {
			return null;
		}

	}

}
