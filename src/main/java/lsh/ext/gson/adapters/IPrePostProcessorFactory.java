package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a pre/post processor.
 *
 * @param <T> Type the processor is implemented for.
 *
 * @author Lyubomyr Shaydariv
 * @see IPrePostProcessor
 */
public interface IPrePostProcessorFactory<T> {

	/**
	 * @param typeToken Type token.
	 *
	 * @return {@code true} if the factory can create a processor for the type represented by the given type token, otherwise {@code false}.
	 */
	boolean supports(@Nonnull TypeToken<?> typeToken);

	/**
	 * @return A pre/post processor for the given type.
	 */
	@Nonnull
	IPrePostProcessor<T> createProcessor();

}
