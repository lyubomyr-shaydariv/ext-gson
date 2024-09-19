package lsh.ext.gson.ext.java.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class UnmodifiableBuilderTest {

	@Test
	public void testForUnmodifiableCollection() {
		@SuppressWarnings("unchecked")
		final Collection<String> collectionMock = Mockito.mock(Collection.class);
		final IBuilder1<String, Collection<? extends String>> unit = UnmodifiableBuilder.forUnmodifiableCollection(collectionMock);
		unit.accept("foo");
		unit.accept("bar");
		unit.accept("baz");
		Mockito.verify(collectionMock)
				.add("foo");
		Mockito.verify(collectionMock)
				.add("bar");
		Mockito.verify(collectionMock)
				.add("baz");
		final Collection<? extends String> unmodifiableCollection = unit.build();
		@SuppressWarnings("unchecked")
		final Collection<String> uncheckedCollection = (Collection<String>) unmodifiableCollection;
		Assertions.assertThrows(UnsupportedOperationException.class, () -> uncheckedCollection.add("qux"));
		Mockito.verifyNoMoreInteractions(collectionMock);
	}

	@Test
	public void testForUnmodifiableList() {
		@SuppressWarnings("unchecked")
		final List<String> listMock = Mockito.mock(List.class);
		final IBuilder1<String, List<? extends String>> unit = UnmodifiableBuilder.forUnmodifiableList(listMock);
		unit.accept("foo");
		unit.accept("bar");
		unit.accept("baz");
		Mockito.verify(listMock)
				.add("foo");
		Mockito.verify(listMock)
				.add("bar");
		Mockito.verify(listMock)
				.add("baz");
		final Collection<? extends String> unmodifiableList = unit.build();
		@SuppressWarnings("unchecked")
		final List<String> uncheckedList = (List<String>) unmodifiableList;
		Assertions.assertThrows(UnsupportedOperationException.class, () -> uncheckedList.add("qux"));
		Mockito.verifyNoMoreInteractions(listMock);
	}

	@Test
	public void testForUnmodifiableMap() {
		@SuppressWarnings("unchecked")
		final Map<String, Integer> mapMock = Mockito.mock(Map.class);
		final IBuilder2<String, Integer, Map<? extends String, ? extends Integer>> unit = UnmodifiableBuilder.forUnmodifiableMap(mapMock);
		unit.accept("foo", 1);
		unit.accept("bar", 2);
		unit.accept("baz", 3);
		Mockito.verify(mapMock)
				.put("foo", 1);
		Mockito.verify(mapMock)
				.put("bar", 2);
		Mockito.verify(mapMock)
				.put("baz", 3);
		final Map<? extends String, ? extends Integer> unmodifiableMap = unit.build();
		@SuppressWarnings("unchecked")
		final Map<String, Integer> uncheckedMap = (Map<String, Integer>) unmodifiableMap;
		Assertions.assertThrows(UnsupportedOperationException.class, () -> uncheckedMap.put("qux", 4));
		Mockito.verifyNoMoreInteractions(mapMock);
	}

}