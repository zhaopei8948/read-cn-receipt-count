package com.github.readcnreceiptcount.constant;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhaopei on 18/4/28.
 */
public enum CommonConstant {
    CHARSET("UTF-8"),
    ;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static final Charset ASCII = Charset.forName("US-ASCII");

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_TIME_WITH_MIS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static final BlockingQueue<String> ERROR_QUEUE = new LinkedBlockingQueue<String>();

    public static boolean LOG_HANDLE_MESSAGE = true;

    private String value;

    private CommonConstant(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
