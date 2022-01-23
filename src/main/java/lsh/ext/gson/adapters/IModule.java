package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * A type adapter factory that can add multiple {@link TypeAdapterFactory} instances at once.
 *
 * @author Lyubomyr Shaydariv
 */
public interface IModule
		extends TypeAdapterFactory {

	@Override
	@Nullable
	<T> TypeAdapter<T> create(@Nonnull Gson gson, @Nonnull TypeToken<T> typeToken);

	@Nonnull
	String getName();

}
