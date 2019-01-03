package lsh.ext.gson.adapters;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonFailSafeTypeAdapterTest
		extends AbstractTypeAdapterTest<Object> {

	public JsonFailSafeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Object value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						JsonFailSafeTypeAdapterFactory.get().create(gson, TypeToken.get(ParameterizedTypes.listOf(String.class))),
						"[\"foo\",\"bar\"]",
						() -> ImmutableList.of("foo", "bar")
				),
				test(
						JsonFailSafeTypeAdapterFactory.get().create(gson, TypeToken.get(ParameterizedTypes.listOf(Integer.class))),
						"[1000]",
						() -> ImmutableList.of(1000)
				)
		);
	}

}
