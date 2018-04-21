package lsh.ext.gson.adapters;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableIterator;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class CloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableIterator<?>> {

	@Parameterized.Parameters
	@SuppressWarnings("resource")
	public static Iterable<TestWith<? extends ICloseableIterator<?>>> parameters() {
		return ImmutableList.of(
				parameterize((Supplier<? extends ICloseableIterator<Integer>>) () -> CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator()), "[1,2,4,8]")
						.with(TypeToken.get(Integer.class))
						.get()
		);
	}

	public CloseableIteratorTypeAdapterTest(final TestWith<? extends ICloseableIterator<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends ICloseableIterator<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		assert typeToken != null;
		return CloseableIteratorTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ICloseableIterator<?> value) {
		return ImmutableList.copyOf(value);
	}

}
