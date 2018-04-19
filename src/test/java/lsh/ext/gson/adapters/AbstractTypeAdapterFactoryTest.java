package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.Test;
import org.mockito.Mockito;

public final class AbstractTypeAdapterFactoryTest {

	private static final TypeToken<?> voidTypeToken = new TypeToken<Void>() {
	};

	private static final Gson gson = new Gson();

	@Test
	public void testCreateIfSupports() {
		@SuppressWarnings("unchecked")
		final Predicate<? super TypeToken<?>> mockTypeTokenPredicate = Mockito.mock(Predicate.class);
		Mockito.when(mockTypeTokenPredicate.test(voidTypeToken)).thenReturn(true);
		@SuppressWarnings("unchecked")
		final BiFunction<? super Gson, ? super TypeToken<?>, TypeAdapter<Void>> mockTypeAdapterFactory = Mockito.mock(BiFunction.class);
		Mockito.when(mockTypeAdapterFactory.apply(gson, voidTypeToken)).thenReturn(new VoidTypeAdapter());
		final TypeAdapterFactory unit = new VoidTypeAdapterFactory(mockTypeTokenPredicate, mockTypeAdapterFactory);
		unit.create(gson, voidTypeToken);
		Mockito.verify(mockTypeTokenPredicate).test(voidTypeToken);
		Mockito.verify(mockTypeAdapterFactory).apply(gson, voidTypeToken);
		Mockito.verifyNoMoreInteractions(mockTypeTokenPredicate);
		Mockito.verifyNoMoreInteractions(mockTypeAdapterFactory);
	}

	@Test
	public void testCreateIfDoesNotSupport() {
		@SuppressWarnings("unchecked")
		final Predicate<? super TypeToken<?>> mockTypeTokenPredicate = Mockito.mock(Predicate.class);
		Mockito.when(mockTypeTokenPredicate.test(voidTypeToken)).thenReturn(false);
		@SuppressWarnings("unchecked")
		final BiFunction<? super Gson, ? super TypeToken<?>, TypeAdapter<Void>> mockTypeAdapterFactory = Mockito.mock(BiFunction.class);
		Mockito.when(mockTypeAdapterFactory.apply(gson, voidTypeToken)).thenReturn(new VoidTypeAdapter());
		final TypeAdapterFactory unit = new VoidTypeAdapterFactory(mockTypeTokenPredicate, mockTypeAdapterFactory);
		unit.create(gson, voidTypeToken);
		Mockito.verify(mockTypeTokenPredicate).test(voidTypeToken);
		Mockito.verifyNoMoreInteractions(mockTypeTokenPredicate);
		Mockito.verifyNoMoreInteractions(mockTypeAdapterFactory);
	}

	private static final class VoidTypeAdapterFactory
			extends AbstractTypeAdapterFactory<Void> {

		private final Predicate<? super TypeToken<?>> typeTokenPredicate;
		private final BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<Void>> typeAdapterFactory;

		private VoidTypeAdapterFactory(
				final Predicate<? super TypeToken<?>> typeTokenPredicate,
				final BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<Void>> typeAdapterFactory
		) {
			this.typeTokenPredicate = typeTokenPredicate;
			this.typeAdapterFactory = typeAdapterFactory;
		}

		@Override
		protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
			return typeTokenPredicate.test(typeToken);
		}

		@Nonnull
		@Override
		protected TypeAdapter<Void> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
			return typeAdapterFactory.apply(gson, typeToken);
		}

	}

	private static final class VoidTypeAdapter
			extends TypeAdapter<Void> {

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final Void value)
				throws IOException {
			out.nullValue();
		}

		@Override
		public Void read(final JsonReader in)
				throws IOException {
			in.nextNull();
			return null;
		}

	}

}
