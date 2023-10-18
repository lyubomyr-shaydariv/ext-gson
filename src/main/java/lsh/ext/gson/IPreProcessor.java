package lsh.ext.gson;

/**
 * Represents a pre processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IPreProcessor<T> {

	/**
	 * Defines a routine to be executed before some process.
	 *
	 * @param value
	 * 		Any value
	 */
	void pre(T value);

}
