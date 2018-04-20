package lsh.ext.gson.adapters;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class AlwaysListTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends List<?>>> parameters() {
		return ImmutableList.of(
				parameterize(ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2)), "{\"foo\":1,\"bar\":2}")
						.with(new TypeToken<Map<String, Integer>>() {})
						.get(),
				parameterize(ImmutableList.of("foo"), "\"foo\"")
						.with(TypeToken.get(String.class))
						.get(),
				parameterize(ImmutableList.of(39), "39")
						.with(TypeToken.get(Integer.class))
						.get(),
				parameterize(ImmutableList.of(true), "true")
						.with(TypeToken.get(Boolean.class))
						.get(),
				parameterize(ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4)), "[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]")
						.with(new TypeToken<Map<String, Integer>>() {})
						.get(),
				parameterize(ImmutableList.of("foo", "bar", "baz"), "[\"foo\",\"bar\",\"baz\"]")
						.with(TypeToken.get(String.class))
						.get(),
				parameterize(ImmutableList.of(39, 42, 100), "[39,42,100]")
						.with(TypeToken.get(Integer.class))
						.get(),
				parameterize(ImmutableList.of(true, false, true), "[true,false,true]")
						.with(TypeToken.get(Boolean.class))
						.get()
		);
	}

	public AlwaysListTypeAdapterTest(final TestWith<? extends List<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends List<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return AlwaysListTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final List<?> value) {
		return value;
	}

}
