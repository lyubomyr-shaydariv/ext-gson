package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, ?>, Multimap<String, ?>> {

	private static final Gson gson = Gsons.getNormalized();

	@SuppressWarnings("unchecked")
	private static final TypeToken<Multimap<String, String>> stringToStringMultimapType = (TypeToken<Multimap<String, String>>) TypeToken.getParameterized(BiMap.class, String.class, String.class);

	@Nullable
	@Override
	protected Multimap<String, ?> normalize(@Nullable final Multimap<String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						MultimapTypeAdapter.getInstance(gson.getAdapter(String.class), () -> MultimapTypeAdapter.Factory.createBuilder(stringToStringMultimapType, typeToken -> LinkedHashMultimap::create)),
						"{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}",
						LinkedHashMultimap.create(ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar"))
				)
		);
	}

}
