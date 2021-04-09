package extraction;

public class PageContentException extends Exception {

    public PageContentException(String message) {
        super(message);
    }

    public PageContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
