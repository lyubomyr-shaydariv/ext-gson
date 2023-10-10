package lsh.ext.gson;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;

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
public abstract class AbstractModuleTest {

	private static final Gson gson = Gsons.getNormalized();

	private static final Collection<TypeToken<?>> foreignClassTypeTokens = List.of(
			TypeToken.get(Foo.class),
			TypeToken.get(Bar.class),
			TypeToken.get(Baz.class)
	);

	protected abstract IModule createUnit();

	protected abstract Stream<Arguments> supported();

	@ParameterizedTest
	@MethodSource("supported")
	public final void testCreate(final TypeToken<?> supportedTypeToken) {
		final TypeAdapterFactory unit = createUnit();
		@Nullable
		final TypeAdapter<?> typeAdapter = unit.create(gson, supportedTypeToken);
		Assertions.assertNotNull(typeAdapter);
		final boolean mustNotSupportForeignClasses = foreignClassTypeTokens.stream()
				.map(typeToken -> unit.create(gson, typeToken))
				.noneMatch(Objects::nonNull);
		Assertions.assertTrue(mustNotSupportForeignClasses);
	}

	private static final class Foo {
	}

	private static final class Bar {
	}

	private static final class Baz {
	}

}
