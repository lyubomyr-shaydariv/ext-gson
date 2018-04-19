package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;

public final class MultimapTypeAdapterTest
		extends AbstractTypeAdapterTest<Multimap<String, String>> {

	@Nonnull
	@Override
	protected TypeAdapter<Multimap<String, String>> createUnit(@Nonnull final Gson gson) {
		return MultimapTypeAdapter.get(gson.getAdapter(String.class));
	}

	@Nullable
	@Override
	protected Multimap<String, String> nullValue() {
		return null;
	}

	@Nonnull
	@Override
	protected Multimap<String, String> getValue() {
		return ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar");
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Multimap<String, String> value) {
		return value;
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}";
	}

}
