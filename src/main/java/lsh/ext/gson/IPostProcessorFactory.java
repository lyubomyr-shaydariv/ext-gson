package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a post processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IPostProcessorFactory<T> {

	/**
	 * @param typeToken
	 * 		Type token.
	 *
	 * @return {@code true} if the factory can create a processor for the type represented by the given type token, otherwise {@code false}.
	 */
	boolean supports(TypeToken<?> typeToken);

	/**
	 * @return A post processor for the given type.
	 */
	IPostProcessor<T> createPostProcessor();

}
