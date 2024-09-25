package lsh.ext.gson;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class EncodedJsonStringTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testRead() {
		Assertions.assertEquals(new Wrapper(List.of(1, 2, 3)), gson.fromJson("{\"value\":\"[1,2,3]\"}", Wrapper.class));
	}

	@Test
	public void testWrite() {
		Assertions.assertEquals("{\"value\":\"[1,3]\"}", gson.toJson(new Wrapper(List.of(1, 3)), Wrapper.class));
	}

	private record Wrapper(
			@JsonAdapter(Factory.class) List<Integer> value
	) {
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		@Override
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			return EncodedJsonStringTypeAdapter.delegate(gson.getAdapter(typeToken));
		}

	}

}
