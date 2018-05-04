package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class BiMapTypeAdapterTest
		extends AbstractTypeAdapterTest<BiMap<String, ?>> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends BiMap<String, ?>>> parameters() {
		return ImmutableList.of(
				parameterize(ImmutableBiMap.of("1", "foo", "2", "bar", "3", "baz"), "{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}")
						.with(TypeToken.get(String.class))
						.get()
		);
	}

	public BiMapTypeAdapterTest(final TestWith<? extends BiMap<String, ?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends BiMap<String, ?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return BiMapTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final BiMap<String, ?> value) {
		return value;
	}

}
