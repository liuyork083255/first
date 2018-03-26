package com.sumscope.cdh.webbond.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.*;

public class Utils
{
    static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Collator chineseCollator = Collator.getInstance(Locale.CHINA);

    public static InputStream getResourceAsStream(String file) throws IOException
    {
        File f = new File(file);
        if (f.isFile())
        {
            return new FileInputStream(f);
        }
        else
        {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (is != null)
            {
                return is;
            }
            else
            {
                throw new FileNotFoundException(file);
            }
        }
    }

    public static LocalDate dateFromString(String s)
    {
        try
        {
            return LocalDate.parse(s, DATE_FORMATTER);
        }
        catch (DateTimeParseException e)
        {
            return null;
        }
    }

    public static String dateToString(LocalDate d)
    {
        return DATE_FORMATTER.format(d);
    }

    public static String escape(String s)
    {
        return StringUtils.replacePattern(s, "[\\\'\\\"]", "\\\\\\\'");
    }

    public static String getLocalHost()
    {
        try
        {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements())
            {
                NetworkInterface ni = nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements())
                {
                    InetAddress ia = ias.nextElement();
                    if (ia instanceof Inet4Address && !ia.isLoopbackAddress() && !ia.isLinkLocalAddress())
                    {
                        return ia.getHostAddress();
                    }
                }
            }
        }
        catch (SocketException e)
        {
            /* ignore */
        }
        return "";
    }

    public static int compareChinese(String str1, String str2)
    {
        if (StringUtils.equals(str1, str2))
        {
            return 0;
        }
        if (null == str1 || str1.equals("--"))
        {
            return -1;
        }
        if (null == str2 || str2.equals("--"))
        {
            return 1;
        }
        return chineseCollator.compare(str1, str2);
    }

    private static int getValueFromExpectionStr(String expection)
    {
        switch (expection.trim())
        {
            case "稳定":
                return 4;
            case "正面":
                return 3;
            case "负面":
                return 2;
            case "列入观察名单":
                return 1;
            default:
                return 0;
        }
    }

    public static int compareExpection(String e1, String e2)
    {
        return getValueFromExpectionStr(e1) - getValueFromExpectionStr(e2);
    }

    public static void executeWithTimeout(Callable callable, int timeout, TimeUnit unit)
    {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final FutureTask task = new FutureTask<>(callable);
        executor.submit(task);
        try
        {
            task.get(timeout, unit);
        }
        catch (InterruptedException e)
        {
            logger.error("", e);
        }
        catch (ExecutionException e)
        {
            logger.error("", e);
        }
        catch (TimeoutException e)
        {
            logger.error("", e);
        }
        task.cancel(true);
        executor.shutdownNow();
    }

}
