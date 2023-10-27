package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.TypeTokens;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public JsonPathTypeAdapterFactoryTest() {
		super(true);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonPathTypeAdapter.Factory.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeTokens.objectTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeTokens.voidTypeToken)
		);
	}

}
