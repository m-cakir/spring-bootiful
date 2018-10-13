package com.bootiful.framework.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String SDF_DATE = "dd/MM/yyyy";

    public static final String SDF_DATE_TIME = "dd/MM/yyyy HH:mm";

    public static Date parseDate(String source, String format) {

        if (StringUtils.hasText(source) && StringUtils.hasText(format)) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat(format);

                return sdf.parse(source);

            } catch (Exception e) {
            }
        }

        return null;
    }

    public static String formatDate(Date date, String format) {

        if (date != null && StringUtils.hasText(format)) {

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }

        return null;
    }

    public static boolean hasUnrepeatedLettersOrDigits(String str) {

        String regex = "([A-Za-z0-9])\\1\\1";

        return !Pattern.compile(regex).matcher(str).find();

    }

    public static boolean hasKeyboardSequencing(String str) {

        if (!StringUtils.hasText(str))
            return false;

        String pwd = str.toLowerCase(Locale.ENGLISH);

        String sequence = "01234567890qwertyuiopasdfghjklzxcvbnm";

        int len = pwd.length();

        boolean result = false;

        for (int i = 0; i < len; i++) {

            if (i > (len - 3)) continue;

            String part = pwd.substring(i, i + 3);

            if (sequence.indexOf(part) != -1) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static String removeTurkishChars(String str) {
        String ret = str;
        char[] turkishChars = new char[]{0x131, 0x130, 0xFC, 0xDC, 0xF6, 0xD6, 0x15F, 0x15E, 0xE7, 0xC7, 0x11F, 0x11E};
//		char[] englishChars = new char[] {'i', 'I', 'u', 'U', 'o', 'O', 's', 'S', 'c', 'C', 'g', 'G'};
        for (int i = 0; i < turkishChars.length; i++) {
            ret = ret.replaceAll(new String(new char[]{turkishChars[i]}), new String(""));
        }
        return ret;
    }

    public static boolean isPasswordValid(String password) {

        // minimum length 5
        // upper-lower letters, digits or special characters
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z]).{5,}$";

        return Pattern.compile(regex).matcher(password).find();
    }

    public static boolean isEmail(String email) {

        if (!StringUtils.hasText(email))
            return false;

        boolean isValid = false;

    	/*
    	Email format: A valid email address will have following format:
   			[\\w\\.-]+: Begins with word characters, (may include periods and hypens).
    		@: It must have a '@' symbol after initial characters.
    		([\\w\\-]+\\.)+: '@' must follow by more alphanumeric characters (may include hypens.).
    	This part must also have a "." to separate domain and subdomain names.
    		[A-Z]{2,4}$ : Must end with two to four alaphabets.
    	(This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz)

    	Examples: Following email addresses will pass validation
    		abc@xyz.net; ab.c@tx.gov
    	*/

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        //Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())

        {
            isValid = true;
        }
        return isValid;
    }

}
