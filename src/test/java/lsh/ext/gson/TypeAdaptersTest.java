package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TypeAdaptersTest {

	@Test
	public void testGetTypeAdapterOfTypeAdapter() {
		final TypeAdapter<?> typeAdapter = TypeAdapters.ofConcrete(VoidTypeAdapter.class);
		Assertions.assertNotNull(typeAdapter);
	}

	@Test
	public void testGetTypeAdapterOfJsonSerializer() {
		final JsonSerializer<?> jsonSerializer = TypeAdapters.ofConcrete(VoidJsonSerializer.class);
		Assertions.assertNotNull(jsonSerializer);
	}

	@Test
	public void testGetTypeAdapterOfJsonDeserializer() {
		final JsonDeserializer<?> jsonDeserializer = TypeAdapters.ofConcrete(VoidJsonDeserializer.class);
		Assertions.assertNotNull(jsonDeserializer);
	}

	@Test
	public void testGetTypeAdapterOfInstanceCreator() {
		final InstanceCreator<?> instanceCreator = TypeAdapters.ofConcrete(VoidInstanceCreator.class);
		Assertions.assertNotNull(instanceCreator);
	}

	@Test
	public void testGetTypeAdapterOfIllegalClass() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> TypeAdapters.ofConcrete(Object.class));
	}

	@Test
	public void testGetTypeHierarchyAdapterOfTypeAdapter() {
		final TypeAdapter<?> typeAdapter = TypeAdapters.ofHierarchy(VoidTypeAdapter.class);
		Assertions.assertNotNull(typeAdapter);
	}

	@Test
	public void testGetTypeHierarchyAdapterOfJsonSerializer() {
		final JsonSerializer<?> jsonSerializer = TypeAdapters.ofHierarchy(VoidJsonSerializer.class);
		Assertions.assertNotNull(jsonSerializer);
	}

	@Test
	public void testGetTypeHierarchyAdapterOfJsonDeserializer() {
		final JsonDeserializer<?> jsonDeserializer = TypeAdapters.ofHierarchy(VoidJsonDeserializer.class);
		Assertions.assertNotNull(jsonDeserializer);
	}

	@Test
	public void testGetTypeHierarchyAdapterOfInstanceCreator() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> TypeAdapters.ofHierarchy(VoidInstanceCreator.class));
	}

	@Test
	public void testGetTypeHierarchyAdapterOfIllegalClass() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> TypeAdapters.ofHierarchy(Object.class));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class VoidTypeAdapter
			extends TypeAdapter<Void> {

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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class VoidJsonSerializer
			implements JsonSerializer<Void> {

		@Override
		public JsonElement serialize(final Void noValue, final Type type, final JsonSerializationContext context) {
			return JsonNull.INSTANCE;
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class VoidJsonDeserializer
			implements JsonDeserializer<Void> {

		@Override
		@Nullable
		public Void deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context) {
			return null;
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static class VoidInstanceCreator
			implements InstanceCreator<Void> {

		@Override
		@Nullable
		public Void createInstance(final Type type) {
			return null;
		}

	}

}
