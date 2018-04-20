package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, ?>> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Multimap<String, ?>>> parameters() {
		return ImmutableList.of(
				parameterize(ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar"), "{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}")
						.with(TypeToken.get(String.class))
						.get()
		);
	}

	public MultimapTypeAdapterTest(final TestWith<? extends Multimap<String, ?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Multimap<String, ?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MultimapTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multimap<String, ?> value) {
		return value;
	}

}
