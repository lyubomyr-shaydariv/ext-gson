package lsh.ext.gson.adapters;

import java.util.List;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.params.provider.Arguments;

public final class AlwaysListTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public AlwaysListTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return AlwaysListTypeAdapterFactory.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(List.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Void.class))
		);
	}

}
