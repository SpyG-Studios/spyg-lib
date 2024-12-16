package hu.spyg.spyglib.formatting;

/**
 * <p>NumberFormatting class.</p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class NumberFormatting {

    /**
     * Formats a number with a given format type.
     *
     * @param number a double
     * @param formatType a {@link hu.spyg.spyglib.formatting.NumberFormatting.NumberFormatTypes} object
     * @return formatted number
     */
    public static String format(double number, NumberFormatTypes formatType) {
        String formattedNumber = "";
        switch (formatType) {
        case SPACE_BETWEEN:
            formattedNumber = String.format("%,.0f", number);
            break;
        case DASH_BETWEEN:
            formattedNumber = String.format("%-,.0f", number);
            break;
        case DOT_BETWEEN:
            formattedNumber = String.format("%,.0f", number);
            break;
        case COMMA_BETWEEN:
            formattedNumber = String.format("%,.0f", number);
            break;
        default:
            formattedNumber = String.format("%,.0f", number);
            break;
        }
        return formattedNumber;
    }

    public enum NumberFormatTypes {
        SPACE_BETWEEN, DASH_BETWEEN, DOT_BETWEEN, COMMA_BETWEEN,
    }

}
