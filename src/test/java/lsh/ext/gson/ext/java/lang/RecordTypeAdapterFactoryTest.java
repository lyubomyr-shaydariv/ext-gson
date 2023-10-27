package lsh.ext.gson.ext.java.lang;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.TypeTokens;
import org.junit.jupiter.params.provider.Arguments;

public final class RecordTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final TypeToken<FooBar> fooBarTypeToken = TypeToken.get(FooBar.class);

	public RecordTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return RecordTypeAdapterFactory.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(fooBarTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeTokens.voidTypeToken)
		);
	}

	private record FooBar(
			String foo,
			String bar
	) {
	}

}
