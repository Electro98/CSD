package webapp.FIOS;

import java.util.EnumSet;
import java.util.Scanner;

public final class InputHandler {
    private static final String NOT_STRING_DELIMITER = "\\p{javaWhitespace}+\\n*";
    private final Scanner in;

    public InputHandler(Scanner in) {
        this.in = in.useDelimiter(NOT_STRING_DELIMITER);
    }

    public String getString(String textRequest) {
        System.out.print(textRequest);
        return in.next() + in.nextLine();
    }

    public int getInt(String textRequest, int from, int to) {
        if (from >= to)
            return 0;
        int input;
        System.out.print(textRequest);
        while (!in.hasNextInt() || (input = in.nextInt()) < from || input > to) {
            in.nextLine();
            System.out.print(textRequest);
        }
        in.nextLine();
        return input;
    }

    public int getInt(String textRequest, int from) {
        return getInt(textRequest, from, Integer.MAX_VALUE);
    }

    public int getInt(String textRequest) {
        return getInt(textRequest, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public double getDouble(String textRequest, double from, double to) {
        if (from >= to)
            return 0;
        System.out.print(textRequest);
        double input;
        while (!in.hasNextDouble() || (input = in.nextDouble()) < from || input > to) {
            in.nextLine();
            System.out.print(textRequest);
        }
        in.nextLine();
        return input;
    }

    public double getDouble(String textRequest, double from) {
        return getDouble(textRequest, from, Double.MAX_VALUE);
    }

    public double getDouble(String textRequest) {
        return getDouble(textRequest, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public boolean getBoolean(String textRequest) {
        System.out.print(textRequest);
        String input;
        while (!(input = in.next()).matches("[YyNn]")) {
            System.out.print(textRequest);
        }
        return input.matches("[Yy]");
    }

    public <T extends Enum<T>> T getEnum(String textRequest, Class<T> aEnum) {
        System.out.println(textRequest);
        int i = 0;
        for (T variant: EnumSet.allOf(aEnum))
            System.out.printf(" %d - %s%n", i++, variant);
        System.out.print("Your choose: ");
        int input;
        while (!in.hasNextInt() || (input = in.nextInt()) < 0 || input > EnumSet.allOf(aEnum).size()) {
            in.nextLine();
            System.out.print("Your choose: ");
        }
        return (T)EnumSet.allOf(aEnum).toArray()[input];
    }
}
