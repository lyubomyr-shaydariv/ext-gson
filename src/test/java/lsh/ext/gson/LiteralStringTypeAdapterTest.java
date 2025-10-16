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

public final class LiteralStringTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testDelegateRead() {
		Assertions.assertEquals(new DelegateWrapper(List.of(1, 2, 3)), gson.fromJson("{\"value\":\"[1,2,3]\"}", DelegateWrapper.class));
	}

	@Test
	public void testDelegateWrite() {
		Assertions.assertEquals("{\"value\":\"[1,3]\"}", gson.toJson(new DelegateWrapper(List.of(1, 3)), DelegateWrapper.class));
	}

	private record DelegateWrapper(
			@JsonAdapter(DelegateTypeAdapterFactory.class) List<Integer> value
	) {
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class DelegateTypeAdapterFactory
			implements TypeAdapterFactory {

		@Override
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			return LiteralStringTypeAdapter.getInstance(gson.getAdapter(typeToken));
		}

	}

}
