package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Type;

import com.jayway.jsonpath.JsonPath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class AbstractAccessor<SP, SB>
		implements IAccessor<SP, SB> {

	@Getter
	private final JsonPath jsonPath;

	@Getter
	private final Type type;

}
