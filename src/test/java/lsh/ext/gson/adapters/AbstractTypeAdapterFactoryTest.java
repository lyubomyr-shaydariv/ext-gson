package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import org.junit.Test;

public abstract class AbstractTypeAdapterFactoryTest {

	@Nonnull
	protected abstract TypeAdapterFactory createUnit();

	@Test
	public final void testCreateIfSupports() {
		throw new AssertionError("TODO");
	}

	@Test
	public final void testCreateIfDoesNotSupport() {
		throw new AssertionError("TODO");
	}

}
