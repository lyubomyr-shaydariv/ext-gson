package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<?>> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Multiset<?>>> parameters() {
		return ImmutableList.of(
				parameterize(ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz"), "[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]")
						.with(TypeToken.get(String.class))
						.get()
		);
	}

	public MultisetTypeAdapterTest(final TestWith<? extends Multiset<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Multiset<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MultisetTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multiset<?> value) {
		return value;
	}

}
