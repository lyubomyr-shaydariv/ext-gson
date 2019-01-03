package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<?>> {

	public MultisetTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multiset<?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						MultisetTypeAdapter.get(gson.getAdapter(String.class)),
						"[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]",
						() -> ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz")
				)
		);
	}

}
