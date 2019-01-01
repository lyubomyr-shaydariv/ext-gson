package lsh.ext.gson.adapters;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractModuleTest {

	private static final Gson gson = new Gson();

	private static final List<TypeToken<?>> foreignClassTypeTokens = ImmutableList.of(
			TypeToken.get(Foo.class),
			TypeToken.get(Bar.class),
			TypeToken.get(Baz.class)
	);

	private final String expectedName;

	protected AbstractModuleTest(final String expectedName) {
		this.expectedName = expectedName;
	}

	@Nonnull
	protected abstract IModule createUnit();

	@Nonnull
	protected abstract Stream<Arguments> supported();

	@Test
	public final void testGetName() {
		final IModule unit = createUnit();
		MatcherAssert.assertThat(unit.getName(), CoreMatchers.is(expectedName));
	}

	@ParameterizedTest
	@MethodSource("supported")
	public final void testCreate(final TypeToken<?> supportedTypeToken) {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, supportedTypeToken);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.notNullValue());
		final boolean mustNotSupportForeignClasses = foreignClassTypeTokens
				.stream()
				.map(typeToken -> unit.create(gson, typeToken))
				.noneMatch(Objects::nonNull);
		MatcherAssert.assertThat(mustNotSupportForeignClasses, CoreMatchers.is(true));
	}

	private static final class Foo {
	}

	private static final class Bar {
	}

	private static final class Baz {
	}

}
