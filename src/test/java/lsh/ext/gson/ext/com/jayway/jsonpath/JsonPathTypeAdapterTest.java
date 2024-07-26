package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterTest
		extends AbstractTypeAdapterTest<Object, Object> {

	private static final Gson gson = Gsons.getNormalized();

	private static final TypeToken<FieldsWrapper> fieldsWrapperTypeToken = TypeToken.get(FieldsWrapper.class);
	private static final TypeToken<FieldsNoWrapper> fieldsNoWrapperTypeToken = TypeToken.get(FieldsNoWrapper.class);
	private static final TypeToken<MethodsWrapper> methodsWrapperTypeToken = TypeToken.get(MethodsWrapper.class);
	private static final TypeToken<MethodsNoWrapper> methodsNoWrapperTypeToken = TypeToken.get(MethodsNoWrapper.class);

	@Nullable
	@Override
	protected Object normalize(@Nullable final Object value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final TypeAdapterFactory declaredFieldsTypeAdapterFactory = JsonPathTypeAdapter.Factory.<Field>getInstance(Configurations::getDefault, Sources::toDeclaredFields, Accessors::getFieldAccessors);
		final TypeAdapterFactory methodsTypeAdapterFactory = JsonPathTypeAdapter.Factory.<Method>getInstance(Configurations::getDefault, Sources::toMethods, Accessors::getMethodsAccessors);
		return List.of(
				makeTestCase(
						declaredFieldsTypeAdapterFactory.create(gson, fieldsWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":\"Foo\",\"barRef\":\"A\",\"bazRef\":{\"k1\":\"v1\"}}",
						new FieldsWrapper("Foo", "A", Map.of("k1", "v1"))
				),
				makeTestCase(
						declaredFieldsTypeAdapterFactory.create(gson, fieldsNoWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new FieldsNoWrapper(null)
				),
				makeTestCase(
						declaredFieldsTypeAdapterFactory.create(gson, fieldsNoWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new FieldsNoWrapper(null)
				),
				makeTestCase(
						declaredFieldsTypeAdapterFactory.create(gson, fieldsNoWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"fooRef\":null}",
						new FieldsNoWrapper(null)
				),
				makeTestCase(
						methodsTypeAdapterFactory.create(gson, methodsWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{\"foo\":\"Foo!\",\"bar\":\"A\",\"baz\":{\"k1\":\"v1\"}}",
						new MethodsWrapper("Foo!", "A", Map.of("k1", "v1"))
				),
				makeTestCase(
						methodsTypeAdapterFactory.create(gson, methodsNoWrapperTypeToken),
						"{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\",\"bar\":[\"A\",\"B\",\"C\"],\"baz\":{\"k1\":\"v1\"}}}}}",
						"{}",
						new MethodsNoWrapper()
				)
		);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	private static final class FieldsWrapper {

		@JsonPathExpression("$.l1.l2.l3.foo")
		private final String fooRef;

		@JsonPathExpression("$.l1.l2.l3.bar[0]")
		private final String barRef;

		@JsonPathExpression("$.l1.l2.l3.baz")
		private final Map<String, ?> bazRef;

	}

	private record FieldsNoWrapper(
			@Nullable @JsonPathExpression("$.nowhere") String fooRef
	) {
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	private static final class MethodsWrapper {

		private String foo;
		private String bar;
		private Map<String, ?> baz;

		@JsonPathExpression("$.l1.l2.l3.foo")
		@SuppressWarnings("unused")
		public void setFoo(final String foo) {
			this.foo = foo;
		}

		@JsonPathExpression("$.l1.l2.l3.bar[0]")
		@SuppressWarnings("unused")
		public void setBar(final String bar) {
			this.bar = bar;
		}

		@JsonPathExpression("$.l1.l2.l3.baz")
		@SuppressWarnings("unused")
		public void setBaz(final Map<String, ?> baz) {
			this.baz = baz;
		}

	}

	private record MethodsNoWrapper(
	) {

		@JsonPathExpression("$.nowhere")
		@SuppressWarnings("unused")
		public void setNowhere(final Object o) {
			throw new AssertionError(o);
		}

	}

}
