package lsh.ext.gson.ext.java.util;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.AbstractElementCursorTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class IteratorTypeAdapterTest
		extends AbstractElementCursorTypeAdapterTest<Iterator<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Override
	@Nullable
	protected List<?> normalize(@Nullable final Iterator<?> value) {
		return value != null ? StreamSupport.stream(Spliterators.spliteratorUnknownSize(value, Spliterator.IMMUTABLE), false).toList() : null;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						IteratorTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						List.of(1, 2, 4, 8).iterator()
				)
		);
	}

}
