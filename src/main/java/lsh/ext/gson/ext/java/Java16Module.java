package lsh.ext.gson.ext.java;

import java.util.List;

import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.java.lang.RecordTypeAdapterFactory;

/**
 * Implements a Java 16 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link RecordTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 * @version 0-SNAPSHOT
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
	public static final class Builder {

		private static final Iterable<? extends TypeAdapterFactory> defaultTypeAdapterFactories = List.of(
						RecordTypeAdapterFactory.getInstance()
				);

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java16Module(defaultTypeAdapterFactories);
		}

	}

}
