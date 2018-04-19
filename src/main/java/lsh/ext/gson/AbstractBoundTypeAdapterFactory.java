package lsh.ext.gson;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * Provides a standard scenario type adapter without using {@code @SuppressWarnings("unchecked")}:
 * </p>
 *
 * <pre>
 * public class FooTypeAdapterFactory
 *     implements TypeAdapterFactory
 *
 *     public &lt;T&gt; TypeAdapter&lt;T&gt; create(Gson gson, TypeToken&lt;T&gt; typeToken) {
 *         if ( ... ) {
 *             final TypeAdapter&lt;Foo&gt; typeAdapter = ...;
 *            {@literal @}SuppressWarnings("unchecked")
 *             final TypeAdapter&lt;T&gt; castTypeAdapter = typeAdapter;
 *             return castTypeAdapter;
 *         }
 *         return null;
 *     }
 *
 * }
 * </pre>
 *
 * @param <CT> Concrete type
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public abstract class AbstractBoundTypeAdapterFactory<CT>
		implements TypeAdapterFactory {

	/**
	 * @param typeToken Type token
	 *
	 * @return {@code true} if the given type token hold type is supported, otherwise {@code false}.
	 *
	 * @since 0-SNAPSHOT
	 */
	protected abstract boolean isSupported(@Nonnull TypeToken<?> typeToken);

	/**
	 * @param gson      Actual Gson instance
	 * @param typeToken Type token
	 *
	 * @return A type adapter for the given concrete type.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	protected abstract TypeAdapter<CT> createTypeAdapter(final Gson gson, @Nonnull TypeToken<?> typeToken);

	@Override
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( !isSupported(typeToken) ) {
			return null;
		}
		final TypeAdapter<CT> typeAdapter = createTypeAdapter(gson, typeToken);
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) typeAdapter;
		return castTypeAdapter;
	}

}
