package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterTest
		extends AbstractTypeAdapterTest<BiMap<String, ?>> {

	public BiMapTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final BiMap<String, ?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						BiMapTypeAdapter.get(gson.getAdapter(String.class)),
						"{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}",
						() -> ImmutableBiMap.of("1", "foo", "2", "bar", "3", "baz")
				)
		);
	}

}
