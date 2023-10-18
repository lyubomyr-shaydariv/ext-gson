package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a pre processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IPreProcessorFactory<T> {

	/**
	 * @param typeToken
	 * 		Type token.
	 *
	 * @return {@code true} if the factory can create a processor for the type represented by the given type token, otherwise {@code false}.
	 */
	boolean supports(TypeToken<?> typeToken);

	/**
	 * @return A pre processor for the given type.
	 */
	IPreProcessor<T> createPreProcessor();

}
