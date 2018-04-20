package lsh.ext.gson.adapters;

import java.util.Collections;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AutoCloseableEnumerations;
import lsh.ext.gson.AutoCloseableIterators;
import lsh.ext.gson.IAutoCloseableEnumeration;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class AutoCloseableEnumerationTypeAdapterTest
		extends AbstractTypeAdapterTest<IAutoCloseableEnumeration<?>> {

	@Parameterized.Parameters
	@SuppressWarnings("resource")
	public static Iterable<TestWith<? extends IAutoCloseableEnumeration<?>>> parameters() {
		return ImmutableList.of(
				parameterize((Supplier<? extends IAutoCloseableEnumeration<Integer>>) () -> AutoCloseableEnumerations.from(AutoCloseableIterators.asAutoCloseable(ImmutableList.of(1, 2, 4, 8).iterator())), "[1,2,4,8]")
						.with(TypeToken.get(Integer.class))
						.get()
		);
	}

	public AutoCloseableEnumerationTypeAdapterTest(final TestWith<? extends IAutoCloseableEnumeration<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends IAutoCloseableEnumeration<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		assert typeToken != null;
		return AutoCloseableEnumerationTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final IAutoCloseableEnumeration<?> value) {
		return ImmutableList.copyOf(Collections.list(value));
	}

}
