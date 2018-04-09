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
	 * Merges the given object with a deserialized object.
	 *
	 * @param instance  Object to merge into
	 * @param extractor A function to extract an object to merge into the given object
	 * @param <T>       Object type
	 *
	 * @return A merged object.
	 *
	 * @since 0-SNAPSHOT
	 */
	<T> T merge(T instance, @Nonnull Function<? super Gson, ? extends T> extractor);

}
