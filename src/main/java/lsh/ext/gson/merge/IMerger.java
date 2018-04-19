package lsh.ext.gson.merge;

import java.util.function.Function;
import javax.annotation.Nonnull;

import com.google.gson.Gson;

/**
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public interface IMerger {

	/**
	 * @param instance  Object to merge into
	 * @param extractor A function to extract an object to merge into the given object
	 * @param <T>       Object type
	 *
	 * @return An instance of then given object merged with a deserialized instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	<T> T merge(T instance, @Nonnull Function<? super Gson, ? extends T> extractor);

}
