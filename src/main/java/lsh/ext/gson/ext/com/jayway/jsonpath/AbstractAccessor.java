package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Type;

import com.jayway.jsonpath.JsonPath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractAccessor<O, I>
		implements IAccessor<O, I> {

	@Getter
	private final JsonPath jsonPath;

	@Getter
	private final Type type;

	@Override
	public final void assignNotFound(final O outerValue) {
		// do nothing
	}

}
