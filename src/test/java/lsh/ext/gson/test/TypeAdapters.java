package lsh.ext.gson.test;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Gsons;

@UtilityClass
public final class TypeAdapters {

	private static final Gson typeAdapterSource = Gsons.getNormalized();

	public static final TypeAdapter<Integer> intTypeAdapter = typeAdapterSource.getAdapter(int.class);

}
