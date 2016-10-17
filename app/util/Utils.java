package util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import play.Logger;
import play.libs.WS;

public abstract class Utils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final String RANDOM_STRING_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Generate a string with the given <code>length</code> consisting of random character [A-Za-z]
     * 
     * @param length length of the generated random string
     * @return the generated string
     */
    public static String generateRandomString(int length) {
        Random rnd = new Random();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) RANDOM_STRING_ALPHABET.charAt(rnd.nextInt(RANDOM_STRING_ALPHABET.length()));
        }
        return new String(bytes);
    }


    public static String checksum(InputStream stream) {
        DigestInputStream is = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            is = new DigestInputStream(stream, md);
            byte[] buffer = new byte[8192];
            while (is.read(buffer) != -1) { }
            return new String(Hex.encode(md.digest()));
        } catch (Exception e) {
            return null;
        } finally {
            if (is != null) try { is.close(); } catch (IOException e) {}
        }
    }

    public static String join(Iterator<?> it, String glue) {
        StringBuilder sb = new StringBuilder(100);
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext())
                sb.append(glue);
        }
        return sb.toString();
    }
    public static String join(Iterator<?> it, String property, String glue) {
        if (property == null) {
            return join(it, glue);
        }
        StringBuilder sb = new StringBuilder(100);
        while (it.hasNext()) {
            Object o = it.next();
            String v;
            try {
                v = BeanUtils.getSimpleProperty(o, property);
            } catch (Exception e) {
                Logger.error("Couldn't get bean property '" + property + "' from bean '" + o + "'.");
                v = "";
            }
            sb.append(v);
            if (it.hasNext())
                sb.append(glue);
        }
        return sb.toString();
    }

    public static PeriodFormatter PERIOD_FORMATTER_EBAY = new PeriodFormatterBuilder().appendDays().appendSuffix("T&nbsp;").appendHours().appendSuffix("Std&nbsp;").appendMinutes().appendSuffix("Min").toFormatter();
    public static PeriodType PERIOD_TYPE_D_H_M = PeriodType.forFields(new DurationFieldType[] {
            DurationFieldType.days(),
            DurationFieldType.hours(),
            DurationFieldType.minutes()
    });
    public static String getPeriodFromNow(Date date) {
        Period period = new Period(new DateTime(), new DateTime(date), PERIOD_TYPE_D_H_M);
        return period.toString(PERIOD_FORMATTER_EBAY);
    }

    public static StringBuilder buildQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : params.entrySet()) {
            sb.append(WS.encode(e.getKey()));
            sb.append("=");
            sb.append(WS.encode(e.getValue().toString()));
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }

    public static Map<String, String> createMapOfString(String... args) {
        Map<String, String> m = new HashMap<String, String>();
        for (int i = 0; i < args.length; i += 2) {
            m.put(args[i], args[i + 1]);
        }
        return m;
    }
}
