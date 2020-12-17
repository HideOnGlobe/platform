package com.elison.platform.commons.constants;

import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: monitor
 * @Package: com.elison.platform.commons.constants
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/10/27 16:48
 * @UpdateDate: 2020/10/27 16:48
 **/
public class DateFormatterConstants {

    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DETAIL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
}
