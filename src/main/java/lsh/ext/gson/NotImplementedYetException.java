package lsh.ext.gson;

public final class NotImplementedYetException
		extends UnsupportedOperationException {

	private NotImplementedYetException() {
		super("Not implemented yet");
	}

	public static RuntimeException create() {
		return new NotImplementedYetException();
	}

}
