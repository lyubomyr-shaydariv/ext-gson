package lsh.ext.gson;

/**
 * Represents a post processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IPostProcessor<T> {

	/**
	 * Defines a routine to be executed after some process.
	 *
	 * @param value
	 * 		Any value
	 */
	void post(T value);

}
