package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, ?>, Multimap<String, ?>> {

	@Nullable
	@Override
	protected Multimap<String, ?> finalize(@Nullable final Multimap<String, ?> value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						MultimapTypeAdapter.getInstance(gson.getAdapter(String.class)),
						"{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}",
						() -> ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar")
				)
		);
	}

}
