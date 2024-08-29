package lsh.ext.gson.test;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Gsons;

@UtilityClass
public final class TypeAdapters {

	private static final Gson typeAdapterSource = Gsons.getNormalized();

	@Getter
	private static final TypeAdapter<Integer> intTypeAdapter = typeAdapterSource.getAdapter(int.class);

}
