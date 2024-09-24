package lsh.ext.gson.ext.javax.json;

import javax.json.JsonValue;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class JavaJsonApiModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private JavaJsonApiModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends JsonValue> jsonValueTypeAdapterFactory = JavaJsonValueTypeAdapterFactory.defaultForJsonValue;

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new JavaJsonApiModule(
					jsonValueTypeAdapterFactory
			);
		}

	}

}