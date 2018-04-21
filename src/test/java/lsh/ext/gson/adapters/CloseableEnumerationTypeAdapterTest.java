package lsh.ext.gson.adapters;

import java.util.Collections;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.CloseableEnumerations;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableEnumeration;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class CloseableEnumerationTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableEnumeration<?>> {

	@Parameterized.Parameters
	@SuppressWarnings("resource")
	public static Iterable<TestWith<? extends ICloseableEnumeration<?>>> parameters() {
		return ImmutableList.of(
				parameterize((Supplier<? extends ICloseableEnumeration<Integer>>) () -> CloseableEnumerations.from(CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator())), "[1,2,4,8]")
						.with(TypeToken.get(Integer.class))
						.get()
		);
	}

	public CloseableEnumerationTypeAdapterTest(final TestWith<? extends ICloseableEnumeration<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends ICloseableEnumeration<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		assert typeToken != null;
		return CloseableEnumerationTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ICloseableEnumeration<?> value) {
		return ImmutableList.copyOf(Collections.list(value));
	}

}
