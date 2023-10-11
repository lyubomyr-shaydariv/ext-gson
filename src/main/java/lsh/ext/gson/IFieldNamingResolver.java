package lsh.ext.gson;

import javax.annotation.Nullable;

public interface IFieldNamingResolver {

	@Nullable
	String resolveName(String value);

}
