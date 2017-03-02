package lsh.ext.gson;

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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public final class AbstractBoundTypeAdapterFactoryTest {

	private static final TypeToken<?> voidTypeToken = new TypeToken<Void>() {
	};

	private static final Gson gson = new Gson();

	@Test
	public void testCreateIfSupports() {
		@SuppressWarnings("unchecked")
		final Predicate<? super TypeToken<?>> mockTypeTokenPredicate = mock(Predicate.class);
		when(mockTypeTokenPredicate.test(voidTypeToken)).thenReturn(true);
		@SuppressWarnings("unchecked")
		final BiFunction<? super Gson, ? super TypeToken<?>, TypeAdapter<Void>> mockTypeAdapterFactory = mock(BiFunction.class);
		when(mockTypeAdapterFactory.apply(gson, voidTypeToken)).thenReturn(new VoidTypeAdapter());
		final TypeAdapterFactory unit = new VoidTypeAdapterFactory(mockTypeTokenPredicate, mockTypeAdapterFactory);
		unit.create(gson, voidTypeToken);
		verify(mockTypeTokenPredicate).test(voidTypeToken);
		verify(mockTypeAdapterFactory).apply(gson, voidTypeToken);
		verifyNoMoreInteractions(mockTypeTokenPredicate);
		verifyNoMoreInteractions(mockTypeAdapterFactory);
	}

	@Test
	public void testCreateIfDoesNotSupport() {
		@SuppressWarnings("unchecked")
		final Predicate<? super TypeToken<?>> mockTypeTokenPredicate = mock(Predicate.class);
		when(mockTypeTokenPredicate.test(voidTypeToken)).thenReturn(false);
		@SuppressWarnings("unchecked")
		final BiFunction<? super Gson, ? super TypeToken<?>, TypeAdapter<Void>> mockTypeAdapterFactory = mock(BiFunction.class);
		when(mockTypeAdapterFactory.apply(gson, voidTypeToken)).thenReturn(new VoidTypeAdapter());
		final TypeAdapterFactory unit = new VoidTypeAdapterFactory(mockTypeTokenPredicate, mockTypeAdapterFactory);
		unit.create(gson, voidTypeToken);
		verify(mockTypeTokenPredicate).test(voidTypeToken);
		verifyNoMoreInteractions(mockTypeTokenPredicate);
		verifyNoMoreInteractions(mockTypeAdapterFactory);
	}

	private static final class VoidTypeAdapterFactory
			extends AbstractBoundTypeAdapterFactory<Void> {

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
		protected TypeAdapter<Void> createTypeAdapter(final Gson gson, @Nonnull final TypeToken<?> typeToken) {
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
