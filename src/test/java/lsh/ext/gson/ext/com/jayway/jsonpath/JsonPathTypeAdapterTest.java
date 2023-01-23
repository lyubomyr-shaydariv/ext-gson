package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		final TypeAdapterFactory typeAdapterFactory = JsonPathTypeAdapterFactory.getInstance(JsonPathTypeAdapterTest::getJsonPathConfiguration);
		return Stream.of(
				makeTestCase(
						typeAdapterFactory.create(gson, TypeToken.get(Wrapper.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":\"Foo\",\"barRef\":\"A\",\"bazRef\":{\"k1\":\"v1\"}}",
						new Wrapper("Foo", "A", Map.of("k1", "v1"))
				),
				makeTestCase(
						typeAdapterFactory.create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				),
				makeTestCase(
						JsonPathTypeAdapterFactory.getInstance(JsonPathTypeAdapterTest::getJsonPathConfiguration).create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				),
				makeTestCase(
						JsonPathTypeAdapterFactory.getWithGlobalDefaults().create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new WrapperWithNotExistingPath(null)
				)
		);
	}

	private static Configuration getJsonPathConfiguration(final Gson gson) {
		return Configuration.builder()
				.jsonProvider(new GsonJsonProvider(gson))
				.mappingProvider(new GsonMappingProvider(gson))
				.build();
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
			@JsonPathExpression("$.nowhere")
			String fooRef
	) {
	}

}
