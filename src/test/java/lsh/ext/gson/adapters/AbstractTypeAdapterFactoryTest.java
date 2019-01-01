package lsh.ext.gson.adapters;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterFactoryTest {

	private static final Gson gson = new Gson();

	private final boolean supportsAll;

	protected AbstractTypeAdapterFactoryTest(final boolean supportsAll) {
		this.supportsAll = supportsAll;
	}

	@Nonnull
	protected abstract TypeAdapterFactory createUnit();

	@Nonnull
	protected abstract Stream<Arguments> supported();

	@Nonnull
	protected abstract Stream<Arguments> unsupported();

	@ParameterizedTest
	@MethodSource("supported")
	public final void testCreateIfSupports(final TypeToken<?> supportedTypeToken) {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, supportedTypeToken);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.notNullValue());
	}

	@ParameterizedTest
	@MethodSource("unsupported")
	public final void testCreateIfDoesNotSupport(final TypeToken<?> unsupportedTypeToken) {
		if ( !supportsAll ) {
			final TypeAdapterFactory unit = createUnit();
			final TypeAdapter<?> typeAdapter = unit.create(gson, unsupportedTypeToken);
			MatcherAssert.assertThat(typeAdapter, CoreMatchers.nullValue());
		}
	}

}
