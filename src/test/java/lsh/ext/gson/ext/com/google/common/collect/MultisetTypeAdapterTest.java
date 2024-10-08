package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<?>, Multiset<?>> {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Multiset<String>> stringMultisetTypeToken = (TypeToken<Multiset<String>>) TypeToken.getParameterized(LinkedHashMultiset.class, String.class);

	@Nullable
	@Override
	protected Multiset<?> normalize(@Nullable final Multiset<?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						GuavaCollectTypeAdapter.forMultiset(TestTypeAdapters.stringTypeAdapter, GuavaCollectTypeAdapterFactory.defaultBuilderFactoryForMultiset(stringMultisetTypeToken)),
						"[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]",
						ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz")
				)
		);
	}

}
