package lsh.ext.gson.adapters.guava;

import java.util.Collection;
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
	public static Collection<TestWith<Multiset<?>>> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(String.class),
						ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz"),
						"[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]"
				)
		);
	}

	public MultisetTypeAdapterTest(final TestWith<? extends Multiset<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Multiset<?>> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MultisetTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multiset<?> value) {
		return value;
	}

}
