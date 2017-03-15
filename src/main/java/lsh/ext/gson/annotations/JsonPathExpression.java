package lsh.ext.gson.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Holds a {@link com.jayway.jsonpath.JsonPath} path.
 *
 * @author Lyubomyr Shaydariv
 * @see lsh.ext.gson.adapters.JsonPathTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface JsonPathExpression {

	/**
	 * @return A JSON path expression supported in {@link com.jayway.jsonpath.JsonPath#getPath()}.
	 *
	 * @since 0-SNAPSHOT
	 */
	String value();

}
