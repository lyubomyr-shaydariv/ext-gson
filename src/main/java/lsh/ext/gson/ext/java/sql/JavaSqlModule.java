package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.UnmodifiableIterable;
import lsh.ext.gson.ext.java.util.UnixTimeDateTypeAdapter;

public final class JavaSqlModule
		extends AbstractModule {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final IModule instance = Builder.create()
			.build();

	private JavaSqlModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private TypeAdapterFactory dateTypeAdapterFactory = UnixTimeDateTypeAdapter.Factory.getInstance(Date.class, () -> new Date(0));

		@Setter
		private TypeAdapterFactory timeTypeAdapterFactory = UnixTimeDateTypeAdapter.Factory.getInstance(Time.class, () -> new Time(0));

		@Setter
		private TypeAdapterFactory timestampTypeAdapterFactory = UnixTimeDateTypeAdapter.Factory.getInstance(Timestamp.class, () -> new Timestamp(0));

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new JavaSqlModule(UnmodifiableIterable.copyOf(
					dateTypeAdapterFactory,
					timeTypeAdapterFactory,
					timestampTypeAdapterFactory
			));
		}

	}

}
