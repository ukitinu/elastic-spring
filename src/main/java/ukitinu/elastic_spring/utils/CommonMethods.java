package ukitinu.elastic_spring.utils;

import org.apache.commons.codec.digest.DigestUtils;

public final class CommonMethods
{
    private CommonMethods()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String getMd5(String string)
    {
        return DigestUtils.md5Hex(string);
    }
}
