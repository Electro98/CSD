package FIOS.TextMenu;

public interface MenuItem {
    String toText();
    default void call(Object obj) { }
}
