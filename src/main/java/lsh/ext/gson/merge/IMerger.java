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
	 * TODO
	 *
	 * @param instance  TODO
	 * @param extractor TODO
	 * @param <T>       TODO
	 *
	 * @return TODO
	 *
	 * @since 0-SNAPSHOT
	 */
	<T> T merge(T instance, @Nonnull Function<? super Gson, ? extends T> extractor);

}
