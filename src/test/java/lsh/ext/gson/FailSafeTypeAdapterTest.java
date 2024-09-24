package lsh.ext.gson;

import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class FailSafeTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final TypeAdapterFactory typeAdapterFactory = FailSafeTypeAdapter.Factory.getInstance(List.class, false, List::of);
		return List.of(
				makeTestCase(
						typeAdapterFactory.create(gson, Types.stringListTypeToken),
						"[\"foo\",\"bar\"]",
						List.of("foo", "bar")
				),
				makeTestCase(
						typeAdapterFactory.create(gson, Types.integerListTypeToken),
						"[1000]",
						List.of(1000)
				)
		);
	}

}
