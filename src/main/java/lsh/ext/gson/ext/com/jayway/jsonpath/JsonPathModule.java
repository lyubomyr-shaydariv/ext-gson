package lsh.ext.gson.ext.com.jayway.jsonpath;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class JsonPathModule
		extends AbstractModule {

	public static IModule getInstance(final IAccessor.IFactory accessorsFactory) {
		return Builder.create(accessorsFactory)
				.build();
	}

	private JsonPathModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<?> jsonPathTypeAdapterFactory;

		public static Builder create(final IAccessor.IFactory accessorsFactory) {
			return new Builder()
					.jsonPathTypeAdapterFactory(JsonPathTypeAdapter.Factory.getInstance(accessorsFactory));
		}

		@Override
		public IModule build() {
			return new JsonPathModule(
					jsonPathTypeAdapterFactory
			);
		}

	}

}
