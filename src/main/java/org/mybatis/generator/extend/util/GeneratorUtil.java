package org.mybatis.generator.extend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bob Jiang on 2017/2/13.
 */
public class GeneratorUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String now() {
        return dateFormat.format(new Date());
    }

    public static String convertFieldName(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            char firstChar = str.charAt(0);
            return Character.isTitleCase(firstChar) ? str :
                    new StringBuilder(strLen).append(Character.toTitleCase(firstChar)).append(str.substring(1)).toString();
        } else {
            return str;
        }
    }
}
