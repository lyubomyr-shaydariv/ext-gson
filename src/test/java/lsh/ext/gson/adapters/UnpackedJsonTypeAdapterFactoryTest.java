package lsh.ext.gson.adapters;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.JsonAdapter;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class UnpackedJsonTypeAdapterFactoryTest
		extends AbstractSimpleTest {

	@Parameterized.Parameters
	public static Iterable<TestWith<?>> parameters() {
		return ImmutableList.of(
		);
	}

	public UnpackedJsonTypeAdapterFactoryTest(final TestWith<?> testWith) {
		super(testWith);
	}

	private static final class Wrapper {

		@JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
		final List<Integer> value;

		private Wrapper(final List<Integer> value) {
			this.value = value;
		}

	}

}
