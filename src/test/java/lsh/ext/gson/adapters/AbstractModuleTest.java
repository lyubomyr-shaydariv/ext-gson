package lsh.ext.gson.adapters;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractModuleTest {

	private static final Gson gson = new Gson();

	private static final List<TypeToken<?>> foreignClassTypeTokens = ImmutableList.of(
			TypeToken.get(Foo.class),
			TypeToken.get(Bar.class),
			TypeToken.get(Baz.class)
	);

	@Nonnull
	protected abstract IModule createUnit();

	@Nonnull
	protected abstract String getExpectedName();

	@Nonnull
	protected abstract Collection<TypeToken<?>> getSupportedTypeTokens();

	@Test
	public final void testGetName() {
		final IModule unit = createUnit();
		MatcherAssert.assertThat(unit.getName(), CoreMatchers.is(getExpectedName()));
	}

	@Test
	public final void testCreate() {
		final TypeAdapterFactory unit = createUnit();
		final Collection<TypeToken<?>> supportedTypeTokens = getSupportedTypeTokens();
		final boolean mustSupportAllClasses = supportedTypeTokens
				.stream()
				.map(typeToken -> unit.create(gson, typeToken))
				.noneMatch(Objects::isNull);
		MatcherAssert.assertThat(mustSupportAllClasses, CoreMatchers.is(true));
		if ( !supportedTypeTokens.isEmpty() ) {
			final boolean mustNotSupportForeignClasses = foreignClassTypeTokens
					.stream()
					.map(typeToken -> unit.create(gson, typeToken))
					.noneMatch(Objects::nonNull);
			MatcherAssert.assertThat(mustNotSupportForeignClasses, CoreMatchers.is(true));
		}
	}

	private static final class Foo {
	}

	private static final class Bar {
	}

	private static final class Baz {
	}

}
