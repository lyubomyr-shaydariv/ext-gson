package lsh.ext.gson.adapters;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Provides a standard scenario type adapter without using {@code @SuppressWarnings("unchecked")}:
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
 * @param <CT>
 * 		Concrete type
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractTypeAdapterFactory<CT>
		implements TypeAdapterFactory {

	/**
	 * @param typeToken
	 * 		Type token
	 *
	 * @return {@code true} if the given type token hold type is supported, otherwise {@code false}.
	 */
	protected abstract boolean isSupported(TypeToken<?> typeToken);

	/**
	 * @param gson
	 * 		Actual Gson instance
	 * @param typeToken
	 * 		Type token
	 *
	 * @return A type adapter for the given concrete type.
	 */
	protected abstract TypeAdapter<CT> createTypeAdapter(Gson gson, TypeToken<?> typeToken);

	@Override
	@Nullable
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
