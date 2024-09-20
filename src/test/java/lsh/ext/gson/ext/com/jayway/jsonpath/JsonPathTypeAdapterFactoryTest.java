package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public JsonPathTypeAdapterFactoryTest() {
		super(true);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonPathTypeAdapter.Factory.getInstance(ReflectiveAccessors.getDeclaredFieldAccessors());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Dummy.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.objectTypeToken),
				Arguments.of(Types.primitiveVoidTypeToken)
		);
	}

	@SuppressWarnings("unused")
	private static final class Dummy {

		@JsonPathExpression("$.whatever")
		@SuppressWarnings("FieldMayBeStatic")
		private final String whatever = null;

	}

}
