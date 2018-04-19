package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AbstractModuleTest {

	private static final Gson gson = new Gson();

	private static final TypeToken<Foo> fooTypeToken = TypeToken.get(Foo.class);
	private static final TypeToken<Bar> barTypeToken = TypeToken.get(Bar.class);
	private static final TypeToken<Baz> bazTypeToken = TypeToken.get(Baz.class);

	@Test
	public void testCreate() {
		final TypeAdapterFactory mockFooTypeAdapterFactory = Mockito.mock(TypeAdapterFactory.class);
		final TypeAdapter<Foo> stubFooTypeAdapter = stub(fooTypeToken);
		Mockito.when(mockFooTypeAdapterFactory.create(gson, fooTypeToken)).thenReturn(stubFooTypeAdapter);
		final TypeAdapterFactory mockBarTypeAdapterFactory = Mockito.mock(TypeAdapterFactory.class);
		final TypeAdapter<Bar> stubBarTypeAdapter = stub(barTypeToken);
		Mockito.when(mockBarTypeAdapterFactory.create(gson, barTypeToken)).thenReturn(stubBarTypeAdapter);
		final TypeAdapterFactory unit = new AbstractModule("test") {
			private final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = ImmutableList.of(mockFooTypeAdapterFactory, mockBarTypeAdapterFactory);

			@Nonnull
			@Override
			protected Iterable<? extends TypeAdapterFactory> getTypeAdapterFactories() {
				return typeAdapterFactories;
			}
		};
		final TypeAdapter<Foo> fooTypeAdapter = unit.create(gson, fooTypeToken);
		MatcherAssert.assertThat(fooTypeAdapter, CoreMatchers.sameInstance(fooTypeAdapter));
		Mockito.verify(mockFooTypeAdapterFactory).create(gson, fooTypeToken);
		final TypeAdapter<Bar> barTypeAdapter = unit.create(gson, barTypeToken);
		MatcherAssert.assertThat(barTypeAdapter, CoreMatchers.sameInstance(barTypeAdapter));
		Mockito.verify(mockBarTypeAdapterFactory).create(gson, barTypeToken);
		final TypeAdapter<Baz> bazTypeAdapter = unit.create(gson, bazTypeToken);
		MatcherAssert.assertThat(bazTypeAdapter, CoreMatchers.nullValue());
		Mockito.verify(mockFooTypeAdapterFactory, Mockito.atLeast(1)).create(gson, fooTypeToken);
		Mockito.verify(mockFooTypeAdapterFactory, Mockito.atLeast(1)).create(gson, barTypeToken);
		Mockito.verify(mockFooTypeAdapterFactory, Mockito.atLeast(1)).create(gson, bazTypeToken);
		Mockito.verify(mockBarTypeAdapterFactory, Mockito.never()).create(gson, fooTypeToken);
		Mockito.verify(mockBarTypeAdapterFactory, Mockito.atLeast(1)).create(gson, barTypeToken);
		Mockito.verify(mockBarTypeAdapterFactory, Mockito.atLeast(1)).create(gson, bazTypeToken);
		Mockito.verifyNoMoreInteractions(mockFooTypeAdapterFactory, mockBarTypeAdapterFactory);
	}

	private static final class Foo {
	}

	private static final class Bar {
	}

	private static final class Baz {
	}

	private static <T> TypeAdapter<T> stub(final TypeToken<T> typeToken) {
		return new TypeAdapter<T>() {
			@Override
			public void write(final JsonWriter out, final T value) {
				throw new AssertionError(typeToken);
			}

			@Override
			public T read(final JsonReader in) {
				throw new AssertionError(typeToken);
			}
		};
	}

}
