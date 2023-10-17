package lsh.ext.gson;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class UnpackedJsonTypeAdapterFactoryTest {

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
			@JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
			List<Integer> value
	) {
	}

}
