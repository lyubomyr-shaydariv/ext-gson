package lsh.ext.gson.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * A special annotation that defines an expression to be evaluate to resolve JSON property names. This annotation can work along with
 * {@link lsh.ext.gson.stategies.DynamicSerializedNameFieldNamingStrategy} only.
 * </p>
 *
 * <p>
 * Example of use:
 * </p>
 *
 * <pre>
 * final class User {
 *
 *    {@literal @}DynamicSerializedName("gson.names.user.username")
 *     final String username = null;
 *
 * }
 * </pre>
 *
 * <pre>
 * static final Gson gson = new GsonBuilder()
 * 		.setFieldNamingStrategy(getDynamicSerializedNameFieldNamingStrategy(System::getProperty))
 * 		.create();
 * </pre>
 *
 * In this case the {@code User.username} JSON property name will be looked up by the {@code gson.names.user.username} key in system properties.
 *
 * @author Lyubomyr Shaydariv
 * @see lsh.ext.gson.stategies.DynamicSerializedNameFieldNamingStrategy
 * @see com.google.gson.annotations.SerializedName
 * @since 0-SNAPSHOT
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface DynamicSerializedName {

	/**
	 * @return A string value to be resolved in {@link lsh.ext.gson.stategies.DynamicSerializedNameFieldNamingStrategy}.
	 *
	 * @since 0-SNAPSHOT
	 */
	String value();

}
