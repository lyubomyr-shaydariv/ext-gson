package lsh.ext.gson.adapters;

/**
 * Represents a pre/post processor.
 *
 * @param <T> Type the processor is implemented for.
 *
 * @author Lyubomyr Shaydariv
 * @see IPrePostProcessorFactory
 * @see PrePostTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public interface IPrePostProcessor<T> {

	/**
	 * Defines a routine to be executed before some process.
	 *
	 * @param value Any value
	 *
	 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter, Object)
	 * @since 0-SNAPSHOT
	 */
	default void pre(@SuppressWarnings("unused") final T value) {
	}

	/**
	 * Defines a routine to be executed after some process.
	 *
	 * @param value Any value
	 *
	 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
	 * @since 0-SNAPSHOT
	 */
	default void post(@SuppressWarnings("unused") final T value) {
	}

}
