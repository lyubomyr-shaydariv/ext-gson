package lsh.ext.gson;

import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterFactoryTest {

	private static final Gson gson = Gsons.getNormalized();

	private final boolean supportsAll;

	protected AbstractTypeAdapterFactoryTest(final boolean supportsAll) {
		this.supportsAll = supportsAll;
	}

	protected abstract TypeAdapterFactory createUnit();

	protected abstract Stream<Arguments> supported();

	protected abstract Stream<Arguments> unsupported();

	@ParameterizedTest
	@MethodSource("supported")
	public final void testCreateIfSupports(final TypeToken<?> supportedTypeToken) {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, supportedTypeToken);
		Assertions.assertNotNull(typeAdapter);
	}

	@ParameterizedTest
	@MethodSource("unsupported")
	public final void testCreateIfDoesNotSupport(final TypeToken<?> unsupportedTypeToken) {
		if ( supportsAll ) {
			return;
		}
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, unsupportedTypeToken);
		Assertions.assertNull(typeAdapter);
	}

}
