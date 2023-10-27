package lsh.ext.gson;

import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonFailSafeTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	private static final Gson gson = Gsons.getNormalized();

	@SuppressWarnings("unchecked")
	private static final TypeToken<List<String>> stringListTypeToken = (TypeToken<List<String>>) TypeToken.getParameterized(List.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<List<Integer>> integerListTypeToken = (TypeToken<List<Integer>>) TypeToken.getParameterized(List.class, Integer.class);

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						JsonFailSafeTypeAdapter.Factory.getInstance().create(gson, stringListTypeToken),
						"[\"foo\",\"bar\"]",
						List.of("foo", "bar")
				),
				makeTestCase(
						JsonFailSafeTypeAdapter.Factory.getInstance().create(gson, integerListTypeToken),
						"[1000]",
						List.of(1000)
				)
		);
	}

}
