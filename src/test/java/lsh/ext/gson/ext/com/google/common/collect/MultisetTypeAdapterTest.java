package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<?>, Multiset<?>> {

	@Nullable
	@Override
	protected Multiset<?> finalize(@Nullable final Multiset<?> value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		return Stream.of(
				test(
						MultisetTypeAdapter.getInstance(gson.getAdapter(String.class)),
						"[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]",
						() -> ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz")
				)
		);
	}

}
