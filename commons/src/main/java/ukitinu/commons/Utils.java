package ukitinu.commons;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    private Utils()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String getMd5(String string)
    {
        return DigestUtils.md5Hex(string);
    }
}
