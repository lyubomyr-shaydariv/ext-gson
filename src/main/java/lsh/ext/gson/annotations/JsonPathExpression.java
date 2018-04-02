package lsh.ext.gson.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Holds a {@link com.jayway.jsonpath.JsonPath} path.
 *
 * @author Lyubomyr Shaydariv
 * @see lsh.ext.gson.adapters.JsonPathTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonPathExpression {

	/**
	 * @return A JSON path expression supported in {@link com.jayway.jsonpath.JsonPath#getPath()}.
	 *
	 * @since 0-SNAPSHOT
	 */
	String value();

}
