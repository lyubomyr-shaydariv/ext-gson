package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Holds a {@link com.jayway.jsonpath.JsonPath} path.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonPathExpression {

	/**
	 * @return A JSON path expression supported in {@link com.jayway.jsonpath.JsonPath#getPath()}.
	 */
	String value();

}
