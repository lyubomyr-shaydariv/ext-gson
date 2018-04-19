package lsh.ext.gson;

/**
 * A strategy to resolve a name by the given value.
 *
 * @author Lyubomyr Shaydariv
 * @see DynamicSerializedNameFieldNamingStrategy
 * @since 0-SNAPSHOT
 */
public interface IFieldNamingResolver {

	/**
	 * Resolves a name by the given value.
	 *
	 * @param value Any string value.
	 *
	 * @return A value corresponding to the given expression. A particular {@link com.google.gson.FieldNamingStrategy} may proceed with nulls in a special way
	 * depending on its implementation.
	 */
	String resolveName(String value);

}
