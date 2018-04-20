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
				testWith(
						new TypeToken<Map<String, Integer>>() {},
						ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2)),
						"{\"foo\":1,\"bar\":2}"
				),
				testWith(
						TypeToken.get(String.class),
						ImmutableList.of("foo"),
						"\"foo\""
				),
				testWith(
						TypeToken.get(Integer.class),
						ImmutableList.of(39),
						"39"
				),
				testWith(
						TypeToken.get(Boolean.class),
						ImmutableList.of(true),
						"true"
				),
				testWith(
						new TypeToken<Map<String, Integer>>() {},
						ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4)),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]"
				),
				testWith(
						TypeToken.get(String.class),
						ImmutableList.of("foo", "bar", "baz"),
						"[\"foo\",\"bar\",\"baz\"]"
				),
				testWith(
						TypeToken.get(Integer.class),
						ImmutableList.of(39, 42, 100),
						"[39,42,100]"
				),
				testWith(
						TypeToken.get(Boolean.class),
						ImmutableList.of(true, false, true),
						"[true,false,true]"
				)
		);
	}

	public AlwaysListTypeAdapterTest(final TestWith<? extends List<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends List<?>> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return AlwaysListTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final List<?> value) {
		return value;
	}

}
