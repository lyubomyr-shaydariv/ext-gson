package lsh.ext.gson;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractModuleTest {

	private static final Gson gson = Gsons.getNormalized();

	private static final TypeToken<Foo> fooTypeToken = TypeToken.get(Foo.class);
	private static final TypeToken<Bar> barTypeToken = TypeToken.get(Bar.class);
	private static final TypeToken<Baz> bazTypeToken = TypeToken.get(Baz.class);

	private static final Collection<TypeToken<?>> foreignClassTypeTokens = List.of(fooTypeToken, barTypeToken, bazTypeToken);

	private final IModule unit;

	protected abstract Stream<Arguments> supported();

	@ParameterizedTest
	@MethodSource("supported")
	public final void testCreate(final TypeToken<?> supportedTypeToken) {
		@Nullable
		final TypeAdapter<?> typeAdapter = unit.create(gson, supportedTypeToken);
		Assertions.assertNotNull(typeAdapter);
		final boolean supportsForeignClass = foreignClassTypeTokens.stream()
				.map(typeToken -> unit.create(gson, typeToken))
				.anyMatch(Objects::nonNull);
		Assertions.assertFalse(supportsForeignClass);
	}

	private static final class Foo {}

	private static final class Bar {}

	private static final class Baz {}

}
