package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	private static final Gson gson = Gsons.getNormalized();

	private static final TypeToken<Wrapper> wrapperTypeToken = TypeToken.get(Wrapper.class);
	private static final TypeToken<WrapperWithNotExistingPath> wrapperWithNotExistingPathTypeToken = TypeToken.get(WrapperWithNotExistingPath.class);

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final TypeAdapterFactory typeAdapterFactory = JsonPathTypeAdapter.Factory.getInstance();
		return List.of(
				makeTestCase(
						typeAdapterFactory.create(gson, wrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":\"Foo\",\"barRef\":\"A\",\"bazRef\":{\"k1\":\"v1\"}}",
						new Wrapper("Foo", "A", Map.of("k1", "v1"))
				),
				makeTestCase(
						typeAdapterFactory.create(gson, wrapperWithNotExistingPathTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				),
				makeTestCase(
						JsonPathTypeAdapter.Factory.getInstance().create(gson, wrapperWithNotExistingPathTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				),
				makeTestCase(
						JsonPathTypeAdapter.Factory.getInstanceWithGlobalDefaults().create(gson, wrapperWithNotExistingPathTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				)
		);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	private static final class Wrapper {

		@JsonPathExpression("$.l1.l2.l3.foo")
		private final String fooRef;

		@JsonPathExpression("$.l1.l2.l3.bar[0]")
		private final String barRef;

		@JsonPathExpression("$.l1.l2.l3.baz")
		private final Map<String, ?> bazRef;

	}

	private record WrapperWithNotExistingPath(
			@Nullable @JsonPathExpression("$.nowhere") String fooRef
	) {
	}

}
