package lsh.ext.gson.adapters;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.GsonBuilders;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonFailSafeTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		return Stream.of(
				makeTestCase(
						JsonFailSafeTypeAdapterFactory.getInstance().create(gson, TypeToken.getParameterized(List.class, String.class)),
						"[\"foo\",\"bar\"]",
						List.of("foo", "bar")
				),
				makeTestCase(
						JsonFailSafeTypeAdapterFactory.getInstance().create(gson, TypeToken.getParameterized(List.class, Integer.class)),
						"[1000]",
						List.of(1000)
				)
		);
	}

}
