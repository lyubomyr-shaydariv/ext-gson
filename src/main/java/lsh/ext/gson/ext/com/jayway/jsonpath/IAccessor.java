package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Type;

import com.jayway.jsonpath.JsonPath;

public interface IAccessor<O, I> {

	JsonPath getJsonPath();

	Type getType();

	void assignFound(O outerValue, I innerValue);

	void assignNotFound(O outerValue);

}
