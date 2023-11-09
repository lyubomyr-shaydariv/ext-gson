package lsh.ext.gson;

import javax.annotation.Nullable;

public interface INamingStrategy {

	@Nullable
	String translateName(String name);

}
