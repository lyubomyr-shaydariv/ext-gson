package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IProcessorFactory<T> {

	/**
	 * @param typeToken
	 * 		Type token.
	 *
	 * @return A processor for the given type.
	 */
	@Nullable
	IProcessor<T> createProcessor(TypeToken<?> typeToken);

}
