package lsh.ext.gson.merge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

/**
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public interface IMergeTypeAdapterMapper {

	/**
	 * TODO
	 *
	 * @param typeAdapter TODO
	 * @param instance    TODO
	 * @param gson        TODO
	 * @param typeToken   TODO
	 * @param <T>         TODO
	 *
	 * @return TODO
	 */
	@Nullable
	<T> TypeAdapter<T> map(@Nonnull TypeAdapter<?> typeAdapter, Object instance, @Nonnull Gson gson, @Nonnull TypeToken<T> typeToken);

}
