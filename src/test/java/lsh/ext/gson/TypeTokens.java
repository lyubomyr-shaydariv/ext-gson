package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TypeTokens {

	public static final TypeToken<Integer> primitiveIntTypeToken = TypeToken.get(int.class);

	public static final TypeToken<Boolean> booleanTypeToken = TypeToken.get(Boolean.class);
	public static final TypeToken<Integer> integerTypeToken = TypeToken.get(Integer.class);
	public static final TypeToken<Object> objectTypeToken = TypeToken.get(Object.class);
	public static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);
	public static final TypeToken<Void> voidTypeToken = TypeToken.get(Void.class);

}
