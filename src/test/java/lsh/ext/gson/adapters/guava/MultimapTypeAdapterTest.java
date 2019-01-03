package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, ?>> {

	public MultimapTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multimap<String, ?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						MultimapTypeAdapter.get(gson.getAdapter(String.class)),
						"{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}",
						() -> ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar")
				)
		);
	}

}
