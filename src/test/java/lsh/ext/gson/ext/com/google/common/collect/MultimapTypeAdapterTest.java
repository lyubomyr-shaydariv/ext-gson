package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, ?>, Multimap<String, ?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected Multimap<String, ?> normalize(@Nullable final Multimap<String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						MultimapTypeAdapterFactory.Adapter.getInstance(gson.getAdapter(String.class), LinkedHashMultimap::create),
						"{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}",
						LinkedHashMultimap.create(ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar"))
				)
		);
	}

}
