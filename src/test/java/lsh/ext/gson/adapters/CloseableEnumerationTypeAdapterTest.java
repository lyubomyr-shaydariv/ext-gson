package lsh.ext.gson.adapters;

import java.util.Collections;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.CloseableEnumerations;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableEnumeration;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableEnumerationTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableEnumeration<?>> {

	public CloseableEnumerationTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ICloseableEnumeration<?> value) {
		return ImmutableList.copyOf(Collections.list(value));
	}

	@Nonnull
	@Override
	@SuppressWarnings("resource")
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						CloseableEnumerationTypeAdapter.get(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						() -> CloseableEnumerations.from(CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator()))
				)
		);
	}

}
