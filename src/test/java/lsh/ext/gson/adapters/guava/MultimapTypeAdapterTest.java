package lsh.ext.gson.adapters.guava;

import java.util.Collection;
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
	public static Collection<TestWith<Multimap<String, ?>>> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(String.class),
						ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar"),
						"{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}"
				)
		);
	}

	public MultimapTypeAdapterTest(final TestWith<? extends Multimap<String, ?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Multimap<String, ?>> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MultimapTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multimap<String, ?> value) {
		return value;
	}

}
