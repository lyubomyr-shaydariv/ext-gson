package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonPathTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public JsonPathTypeAdapterFactoryTest() {
		super(true);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonPathTypeAdapterFactory.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Object.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Void.class))
		);
	}

}
