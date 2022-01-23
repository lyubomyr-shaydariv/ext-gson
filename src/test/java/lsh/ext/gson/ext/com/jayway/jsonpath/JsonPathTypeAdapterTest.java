package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	@Nullable
	@Override
	protected Object finalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new GsonBuilder()
				.create();
		final TypeAdapterFactory typeAdapterFactory = JsonPathTypeAdapterFactory.getInstance(JsonPathTypeAdapterTest::getJsonPathConfiguration);
		return Stream.of(
				test(
						typeAdapterFactory.create(gson, TypeToken.get(Wrapper.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":\"Foo\",\"barRef\":\"A\",\"bazRef\":{\"k1\":\"v1\"}}",
						() -> new Wrapper("Foo", "A", ImmutableMap.of("k1", "v1"))
				),
				test(
						typeAdapterFactory.create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						() -> new WrapperWithNotExistingPath(null)
				),
				test(
						JsonPathTypeAdapterFactory.getInstance(JsonPathTypeAdapterTest::getJsonPathConfiguration).create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						() -> new WrapperWithNotExistingPath(null)
				),
				test(
						JsonPathTypeAdapterFactory.getWithGlobalDefaults().create(gson, TypeToken.get(WrapperWithNotExistingPath.class)),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						() -> new WrapperWithNotExistingPath(null)
				)
		);
	}

	private static Configuration getJsonPathConfiguration(final Gson gson) {
		return Configuration.builder()
				.jsonProvider(new GsonJsonProvider(gson))
				.mappingProvider(new GsonMappingProvider(gson))
				.build();
	}

	@EqualsAndHashCode
	@ToString
	private static final class Wrapper {

		@JsonPathExpression("$.l1.l2.l3.foo")
		private final String fooRef;

		@JsonPathExpression("$.l1.l2.l3.bar[0]")
		private final String barRef;

		@JsonPathExpression("$.l1.l2.l3.baz")
		private final Map<String, ?> bazRef;

		private Wrapper(final String fooRef, final String barRef, final Map<String, ?> bazRef) {
			this.fooRef = fooRef;
			this.barRef = barRef;
			this.bazRef = bazRef;
		}

	}

	@EqualsAndHashCode
	@ToString
	private static final class WrapperWithNotExistingPath {

		@JsonPathExpression("$.nowhere")
		private final String fooRef;

		private WrapperWithNotExistingPath(final String fooRef) {
			this.fooRef = fooRef;
		}

	}

}
