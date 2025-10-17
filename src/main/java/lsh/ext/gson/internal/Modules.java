package lsh.ext.gson.internal;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IModule;

@UtilityClass
public final class Modules {

	// this only exists because we cannot make a lambda
	public static final IModule empty = new IModule() {
		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			return null;
		}
	};

}
