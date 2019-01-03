package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<?>, Multiset<?>> {

	@Nullable
	@Override
	protected Multiset<?> finalize(@Nullable final Multiset<?> value) {
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
