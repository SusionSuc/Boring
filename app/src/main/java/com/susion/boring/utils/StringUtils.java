package com.susion.boring.utils;

/**
 * Created by susion on 17/3/14.
 */
public class StringUtils {

    public static String getCssLinkString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
        builder.append(FileUtils.TEMP_CSS_FILE);
        builder.append("\" />");
        return builder.toString();
    }

    public static String getHTMLHead(String... links) {
        StringBuilder builder = new StringBuilder();
        builder.append("<head>");
        for (String link : links) {
            builder.append(link);
        }
        builder.append("<meta name=\\\"viewport\\\" content=\\\"width=device-width, user-scalable=yes\\\" />");
        builder.append("</head>");
        return builder.toString();
    }

    public static String getCSSStyle(String tempCssString) {
        StringBuilder builder = new StringBuilder();
        builder.append("<style type=\"text/css\">");
        builder.append(tempCssString);
        builder.append("</style> ");
        return builder.toString();
    }

    public static String getHtmlString(String head, String body) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append(head);
        builder.append("<body>");
        builder.append(body);
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    public static String adjustEsssayHtmlStyle(String htmlString) {
        return htmlString.replace("class=\"img-place-holder\"", "");
    }
}
