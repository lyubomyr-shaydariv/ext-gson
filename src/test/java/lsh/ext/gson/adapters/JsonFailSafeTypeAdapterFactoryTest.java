package lsh.ext.gson.adapters;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonFailSafeTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public JsonFailSafeTypeAdapterFactoryTest() {
		super(true);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonFailSafeTypeAdapterFactory.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(String.class))
		);
	}

	@Nonnull
	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(int.class))
		);
	}

}
