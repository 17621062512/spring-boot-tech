package com.ray.tech.utils;


import com.ray.tech.exception.ArgumentException;

import java.util.regex.Pattern;

public final class PreconditionsUtil {
    private PreconditionsUtil() {
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及到调用方法的一个或多个参数的表达式的真实性。
     *
     * @param expression a boolean expression
     * @param parameter  错误参数名称
     */
    public static void checkArgument(boolean expression, String parameter) {
        if (!expression) {
            throw new ArgumentException(parameter);
        }
    }


    /**
     * <p>Validate that the specified argument character sequence matches the specified regular
     * expression pattern; otherwise throwing an exception with the specified message.</p>
     * <p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*", "%s does not match %s", "hi" "[a-z]*");</pre>
     *
     * <p>The syntax of the pattern is the one used in the {@link Pattern} class.</p>
     *
     * @param input     the character sequence to validate, not null
     * @param pattern   the regular expression pattern, not null
     * @param parameter 错误参数名称
     * @since 3.0
     */
    public static void matchesPattern(final CharSequence input, final String pattern, String parameter) {
        if (!Pattern.matches(pattern, input)) {
            throw new ArgumentException(parameter);
        }
    }
}

