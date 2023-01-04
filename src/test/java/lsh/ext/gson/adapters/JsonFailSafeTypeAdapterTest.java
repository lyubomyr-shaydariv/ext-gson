package lsh.ext.gson.adapters;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.ParameterizedTypes;
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
						JsonFailSafeTypeAdapterFactory.getInstance().create(gson, TypeToken.get(ParameterizedTypes.listOf(String.class))),
						"[\"foo\",\"bar\"]",
						() -> ImmutableList.of("foo", "bar")
				),
				makeTestCase(
						JsonFailSafeTypeAdapterFactory.getInstance().create(gson, TypeToken.get(ParameterizedTypes.listOf(Integer.class))),
						"[1000]",
						() -> ImmutableList.of(1000)
				)
		);
	}

}
