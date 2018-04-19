package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.AutoCloseableIterators;
import lsh.ext.gson.IAutoCloseableIterator;

public final class AutoCloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<IAutoCloseableIterator<Integer>> {

	@Nonnull
	@Override
	protected TypeAdapter<IAutoCloseableIterator<Integer>> createUnit(@Nonnull final Gson gson) {
		return AutoCloseableIteratorTypeAdapter.get(Integer.class, gson);
	}

	@Nullable
	@Override
	protected IAutoCloseableIterator<Integer> nullValue() {
		return null;
	}

	@Nonnull
	@Override
	protected IAutoCloseableIterator<Integer> getValue() {
		return AutoCloseableIterators.asAutoCloseable(ImmutableList.of(1, 2, 4, 8).iterator());
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final IAutoCloseableIterator<Integer> value) {
		return ImmutableList.copyOf(value);
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "[1,2,4,8]";
	}

}
