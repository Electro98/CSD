package webapp.FIOS.TextMenu;

public interface MenuItem {
    String toText();
    default void call(Object obj) { }
}
