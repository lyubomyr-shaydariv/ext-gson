package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Type;
import java.util.Collection;

import com.jayway.jsonpath.JsonPath;

public interface IAccessor<SP, SB> {

	JsonPath getJsonPath();

	Type getType();

	void assign(SP superValue, SB innerValue);

	interface IFactory<T> {

		Collection<IAccessor<? super Object, ? super Object>> create(Collection<? extends T> sources);

	}

}
