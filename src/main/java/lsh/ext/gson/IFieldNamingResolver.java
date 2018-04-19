package lsh.ext.gson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	 * @return A value corresponding to the given expression. A particular {@link com.google.gson.FieldNamingStrategy} may proceed with {@code null} in a
	 * special way depending on its implementation.
	 */
	@Nullable
	String resolveName(@Nonnull String value);

}
