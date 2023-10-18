package lsh.ext.gson;

/**
 * Represents a pre/post processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 *
 * @author Lyubomyr Shaydariv
 */
public interface IPrePostProcessor<T> {

	/**
	 * Defines a routine to be executed before some process.
	 *
	 * @param value
	 * 		Any value
	 */
	default void pre(@SuppressWarnings("unused") final T value) {
	}

	/**
	 * Defines a routine to be executed after some process.
	 *
	 * @param value
	 * 		Any value
	 */
	default void post(@SuppressWarnings("unused") final T value) {
	}

}
