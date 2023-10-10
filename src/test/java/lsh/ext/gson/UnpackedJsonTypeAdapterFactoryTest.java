package lsh.ext.gson;

import java.util.List;
import java.util.stream.Stream;

import com.google.gson.annotations.JsonAdapter;
import org.junit.jupiter.params.provider.Arguments;

public final class UnpackedJsonTypeAdapterFactoryTest
		extends AbstractSimpleTest {

	private record Wrapper(
			@JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
			List<Integer> value
	) {
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(Wrapper.class, "{\"value\":\"[1,2,3]\"}", new Wrapper(List.of(1, 2, 3)))
		);
	}

}
