package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;

public final class MultisetTypeAdapterTest
		extends AbstractTypeAdapterTest<Multiset<String>> {

	@Nonnull
	@Override
	protected TypeAdapter<Multiset<String>> createUnit(@Nonnull final Gson gson) {
		return MultisetTypeAdapter.get(gson.getAdapter(String.class));
	}

	@Nullable
	@Override
	protected Multiset<String> nullValue() {
		return null;
	}

	@Nonnull
	@Override
	protected Multiset<String> getValue() {
		return ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz");
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]";
	}

}
