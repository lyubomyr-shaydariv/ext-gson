package lsh.ext.gson;

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
 */
public abstract class AbstractTypeAdapterFactory<CT>
		implements TypeAdapterFactory {

	/**
	 * @param gson
	 * 		Actual Gson instance
	 * @param typeToken
	 * 		Type token
	 *
	 * @return A type adapter for the given concrete type.
	 */
	@Nullable
	protected abstract TypeAdapter<CT> createTypeAdapter(Gson gson, TypeToken<?> typeToken);

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		@Nullable
		final TypeAdapter<CT> typeAdapter = createTypeAdapter(gson, typeToken);
		if ( typeAdapter == null ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) typeAdapter;
		return castTypeAdapter;
	}

}
