package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import org.junit.Test;

public abstract class AbstractTypeAdapterTest<T> {

	@Nonnull
	protected abstract TypeAdapter<T> createUnit();

	@Test
	public final void testWrite() {
		throw new AssertionError("TODO");
	}

	@Test
	public final void testRead() {
		throw new AssertionError("TODO");
	}

}
