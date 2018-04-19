package lsh.ext.gson.adapters;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a pre/post processor.
 *
 * @param <T> Type the processor is implemented for.
 *
 * @author Lyubomyr Shaydariv
 * @see IPrePostProcessor
 * @since 0-SNAPSHOT
 */
public interface IPrePostProcessorFactory<T> {

	/**
	 * @param typeToken Type token.
	 *
	 * @return {@code true} if the factory can create a processor for the type represented by the given type token, otherwise {@code false}.
	 *
	 * @since 0-SNAPSHOT
	 */
	boolean supports(TypeToken<?> typeToken);

	/**
	 * @return A pre/post processor for the given type.
	 *
	 * @since 0-SNAPSHOT
	 */
	IPrePostProcessor<T> createProcessor();

}
