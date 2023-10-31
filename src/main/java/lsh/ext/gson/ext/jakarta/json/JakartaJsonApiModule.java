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
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

public final class JakartaJsonApiModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = builder()
			.build();

	private JakartaJsonApiModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<JsonValue> jsonValueTypeAdapterFactory = JsonValueTypeAdapter.Factory.getInstance(JsonProvider.provider());

		public IModule build() {
			return new JakartaJsonApiModule(UnmodifiableIterable.copyOf(
					jsonValueTypeAdapterFactory
			));
		}

	}

}
