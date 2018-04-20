package lsh.ext.gson.adapters.jsonpath;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@Ignore
@RunWith(Parameterized.class)
public final class JsonPathTypeAdapterTest
		extends AbstractTypeAdapterTest<Object> {

	@Parameterized.Parameters
	public static Iterable<TestWith<?>> parameters() {
		return ImmutableList.of(
				parameterize(new Wrapper("Foo", "A", ImmutableMap.of("k1", "v1")), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(Wrapper.class))
						.get(),
				parameterize(new WrapperWithNotExistingPath(null), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(WrapperWithNotExistingPath.class))
						.get(),
				parameterize(new Wrapper("Foo", "A", ImmutableMap.of("k1", "v1")), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(Wrapper.class))
						.with(JsonPathTypeAdapterFactory.get(JsonPathTypeAdapterTest::getJsonPathConfiguration)::create)
						.get(),
				parameterize(new WrapperWithNotExistingPath(null), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(WrapperWithNotExistingPath.class))
						.with(JsonPathTypeAdapterFactory.get(JsonPathTypeAdapterTest::getJsonPathConfiguration)::create)
						.get(),
				parameterize(new Wrapper("Foo", "A", ImmutableMap.of("k1", "v1")), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(Wrapper.class))
						.with(JsonPathTypeAdapterFactory.getWithGlobalDefaults()::create)
						.get(),
				parameterize(new WrapperWithNotExistingPath(null), "{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}")
						.with(TypeToken.get(WrapperWithNotExistingPath.class))
						.with(JsonPathTypeAdapterFactory.getWithGlobalDefaults()::create)
						.get()
		);
	}

	public JsonPathTypeAdapterTest(final TestWith<?> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<?> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return JsonPathTypeAdapterFactory.get().create(gson, typeToken);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Object value) {
		return value;
	}

	private static Configuration getJsonPathConfiguration(final Gson gson) {
		return Configuration.builder()
				.jsonProvider(new GsonJsonProvider(gson))
				.mappingProvider(new GsonMappingProvider(gson))
				.build();
	}

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

		@Override
		public boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final Wrapper that = (Wrapper) o;
			return Objects.equals(fooRef, that.fooRef)
					&& Objects.equals(barRef, that.barRef)
					&& Objects.equals(bazRef, that.bazRef);
		}

		@Override
		public int hashCode() {
			return Objects.hash(fooRef, barRef, bazRef);
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("fooRef", fooRef)
					.add("barRef", barRef)
					.add("bazRef", bazRef)
					.toString();
		}

	}

	private static final class WrapperWithNotExistingPath {

		@JsonPathExpression("$.nowhere")
		private final String fooRef;

		private WrapperWithNotExistingPath(final String fooRef) {
			this.fooRef = fooRef;
		}

		@Override
		public boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final WrapperWithNotExistingPath that = (WrapperWithNotExistingPath) o;
			return Objects.equals(fooRef, that.fooRef);
		}

		@Override
		public int hashCode() {
			return Objects.hash(fooRef);
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("fooRef", fooRef)
					.toString();
		}

	}

}
