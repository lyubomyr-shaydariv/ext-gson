package lsh.ext.gson.merge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

/**
 * Represents a strategy to create a special type adapter to use for merging objects.
 *
 * @author Lyubomyr Shaydariv
 */
public interface IMergeTypeAdapterMapper {

	/**
	 * @param typeAdapter Type adapter to use.
	 * @param instance    An object to map type adapter for.
	 * @param gson        Gson instance
	 * @param typeToken   Type token to describe type for the returned type adapter.
	 * @param <T>         Type parameter of the type token.
	 *
	 * @return A type adapter to map with.
	 */
	@Nullable
	<T> TypeAdapter<T> map(@Nonnull TypeAdapter<?> typeAdapter, Object instance, @Nonnull Gson gson, @Nonnull TypeToken<T> typeToken);

}
