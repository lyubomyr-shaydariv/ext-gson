package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Stream}.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class StreamTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	private static final TypeAdapterFactory instance = new StreamTypeAdapterFactory<>();

	private StreamTypeAdapterFactory() {
		super(Stream.class);
	}

	/**
	 * @return An instance of {@link StreamTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Nonnull
	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(@Nonnull final TypeAdapter<?> elementTypeAdapter) {
		return StreamTypeAdapter.get(elementTypeAdapter);
	}

}
