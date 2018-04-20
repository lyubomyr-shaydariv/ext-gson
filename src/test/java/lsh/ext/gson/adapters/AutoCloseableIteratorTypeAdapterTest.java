package lsh.ext.gson.adapters;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AutoCloseableIterators;
import lsh.ext.gson.IAutoCloseableIterator;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class AutoCloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<IAutoCloseableIterator<?>> {

	@Parameterized.Parameters
	@SuppressWarnings("resource")
	public static Iterable<TestWith<IAutoCloseableIterator<?>>> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(Integer.class),
						() -> AutoCloseableIterators.asAutoCloseable(ImmutableList.of(1, 2, 4, 8).iterator()),
						"[1,2,4,8]"
				)
		);
	}

	public AutoCloseableIteratorTypeAdapterTest(final TestWith<IAutoCloseableIterator<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends IAutoCloseableIterator<?>> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		assert typeToken != null;
		return AutoCloseableIteratorTypeAdapter.get(typeToken.getType(), gson);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final IAutoCloseableIterator<?> value) {
		return ImmutableList.copyOf(value);
	}

}
