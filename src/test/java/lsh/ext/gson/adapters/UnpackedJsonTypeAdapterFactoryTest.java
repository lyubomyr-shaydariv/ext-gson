package lsh.ext.gson.adapters;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.JsonAdapter;
import org.junit.jupiter.params.provider.Arguments;

public final class UnpackedJsonTypeAdapterFactoryTest
		extends AbstractSimpleTest {

	private static final class Wrapper {

		@JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
		final List<Integer> value;

		private Wrapper(final List<Integer> value) {
			this.value = value;
		}

		@Override
		public boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final Wrapper that = (Wrapper) o;
			return value != null ? value.equals(that.value) : that.value == null;
		}

		@Override
		public int hashCode() {
			return value != null ? value.hashCode() : 0;
		}

	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(Wrapper.class, "{\"value\":\"[1,2,3]\"}", new Wrapper(ImmutableList.of(1, 2, 3)))
		);
	}

}
