package lsh.ext.gson.ext.java.lang;

import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

/**
 * Implements a Java 16 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link RecordTypeAdapterFactory}</li>
 * </ul>
 */
public final class Java16Module
		extends AbstractModule {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final IModule defaultInstance = builder()
			.build();

	private Java16Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<Record> recordTypeAdapterFactory = RecordTypeAdapterFactory.getInstance();

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java16Module(UnmodifiableIterable.copyOf(
					recordTypeAdapterFactory
			));
		}

	}

}
