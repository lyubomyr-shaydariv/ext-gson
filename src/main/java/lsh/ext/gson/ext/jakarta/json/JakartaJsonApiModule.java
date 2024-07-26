package lsh.ext.gson.ext.jakarta.json;

import com.google.gson.TypeAdapterFactory;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class JakartaJsonApiModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private JakartaJsonApiModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<JsonValue> jsonValueTypeAdapterFactory = JsonValueTypeAdapter.Factory.getInstance(JsonProvider.provider());

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new JakartaJsonApiModule(
					jsonValueTypeAdapterFactory
			);
		}

	}

}
