package lsh.ext.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * A special annotation that defines an expression to be evaluate to resolve JSON property names. This annotation can work along with
 * {@link DynamicSerializedNameFieldNamingStrategy} only.
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
 * @see DynamicSerializedNameFieldNamingStrategy
 * @see com.google.gson.annotations.SerializedName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicSerializedName {

	/**
	 * @return A string value to be resolved in {@link DynamicSerializedNameFieldNamingStrategy}.
	 */
	String value();

}
