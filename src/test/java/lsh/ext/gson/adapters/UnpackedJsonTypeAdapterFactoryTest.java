package lsh.ext.gson.adapters;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.JsonAdapter;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.params.provider.Arguments;

public final class UnpackedJsonTypeAdapterFactoryTest
		extends AbstractSimpleTest {

	@EqualsAndHashCode
	private static final class Wrapper {

		@JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
		final List<Integer> value;

		private Wrapper(final List<Integer> value) {
			this.value = value;
		}

	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(Wrapper.class, "{\"value\":\"[1,2,3]\"}", new Wrapper(ImmutableList.of(1, 2, 3)))
		);
	}

}
