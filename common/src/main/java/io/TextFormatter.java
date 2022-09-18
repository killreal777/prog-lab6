package io;


/**
 * Class for text formatting
 */


public class TextFormatter {
    public static String format(String text, Format format) {
        return format.getTag() + text + "\033[0m";
    }


    /**
     * Enum with format patterns
     */
    public enum Format {
        RED("\033[0;91m"),
        YELLOW("\033[1;93m"),
        GREEN("\033[0;92m"),
        GRAY_ITALIC("\033[3;90m");

        private final String tag;

        Format(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }
}
