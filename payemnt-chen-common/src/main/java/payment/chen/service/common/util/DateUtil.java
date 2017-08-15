package payment.chen.service.common.util;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author <a href="jxdong@Ctrip.com">jeff</a>
 * @version 2015/7/02 11:13
 */
public class DateUtil {

    public static final int YEAR_RETURN = 0;

    public static final int MONTH_RETURN = 1;

    public static final int DAY_RETURN = 2;

    public static final int HOUR_RETURN = 3;

    public static final int MINUTE_RETURN = 4;

    public static final int SECOND_RETURN = 5;
    public static final int MILL_SECOND_RETURN = 6;

    public static final String YYYY = "yyyy";

    public static final String YYYYMM = "yyyy-MM";

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
    public static final String YYYYMMDD_CHINESE = "yyyy年MM月dd日";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static long getBetween(String beginTime, String endTime, String formatPattern, int returnPattern) {
        if(beginTime == null || endTime == null || formatPattern == null){
            return -1;
        }
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
            Date beginDate = simpleDateFormat.parse(beginTime);
            Date endDate = simpleDateFormat.parse(endTime);

            Calendar beginCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            beginCalendar.setTime(beginDate);
            endCalendar.setTime(endDate);
            switch (returnPattern) {
                case YEAR_RETURN:
                    return DateUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR);
                case MONTH_RETURN:
                    return DateUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12 + DateUtil.getByField(beginCalendar, endCalendar, Calendar.MONTH);
                case DAY_RETURN:
                    return DateUtil.getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
                case HOUR_RETURN:
                    return DateUtil.getTime(beginDate, endDate) / (60 * 60 * 1000);
                case MINUTE_RETURN:
                    return DateUtil.getTime(beginDate, endDate) / (60 * 1000);
                case SECOND_RETURN:
                    return DateUtil.getTime(beginDate, endDate) / 1000;
                default:
                    return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  -1;
    }

    public static long getBetween(Date beginTime, Date endTime, int returnPattern) {
        if(beginTime == null || endTime == null){
            return -1;
        }
        try{
            Calendar beginCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            beginCalendar.setTime(beginTime);
            endCalendar.setTime(endTime);
            switch (returnPattern) {
                case YEAR_RETURN:
                    return DateUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR);
                case MONTH_RETURN:
                    return DateUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12 + DateUtil.getByField(beginCalendar, endCalendar, Calendar.MONTH);
                case DAY_RETURN:
                    return DateUtil.getTime(beginTime, endTime) / (24 * 60 * 60 * 1000);
                case HOUR_RETURN:
                    return DateUtil.getTime(beginTime, endTime) / (60 * 60 * 1000);
                case MINUTE_RETURN:
                    return DateUtil.getTime(beginTime, endTime) / (60 * 1000);
                case SECOND_RETURN:
                    return DateUtil.getTime(beginTime, endTime) / 1000;
                default:
                    return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  -1;
    }

    private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
        return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
    }

    private static long getTime(Date beginDate, Date endDate) {
        return endDate.getTime() - beginDate.getTime();
    }

    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long between_days = 0;
        try {
            cal.setTime(sdf.parse(sdf.format(smdate)));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(sdf.format(bdate)));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.getStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getGMTDate(Date date, String formate) {
        if(date == null || StringUtils.isBlank(formate)){
            return null;
        }
        String cstDate = null;
        try{
            SimpleDateFormat matter = new SimpleDateFormat(formate);
            matter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            cstDate = matter.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cstDate;
    }

    public static Date convertStr2Date(String strDate, String formate){
        Date result = null;
        if(strDate != null && strDate.trim().length()>0 && formate != null && formate.length() >0){
            try{
                SimpleDateFormat sf = new SimpleDateFormat(formate);
                result = sf.parse(strDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Date toDate(XMLGregorianCalendar cal) {
        if (cal == null) {
            return null;
        }
        return cal.toGregorianCalendar().getTime();
    }

    public static String formateDate(Date date){
        if(date != null){
            SimpleDateFormat sf = new SimpleDateFormat(YYYYMMDDHHMMSS);
            return sf.format(date);
        }
        return "";
    }

    public static String formateDate(Date date, String formate){
        if(date != null && formate != null){
            SimpleDateFormat sf = new SimpleDateFormat(formate);
            return sf.format(date);
        }
        return "";
    }

    /**
     * 功能：当前时间增加天数。
     * May 29, 2013 11:26:27 AM
     * @param days 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addDays(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE)+days);
        return new Date(c.getTimeInMillis());
    }

    public static Date addDays(Date beginDate, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE)+days);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加分钟数
     * @param minutes 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addMinute(int minutes){
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
    	return new Date(c.getTimeInMillis());
    }

    //得到某个时间的 某天数据，不带时分秒
    public static Date getDayDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static void main(String args[]){
//
        System.out.println(DateUtil.convertStr2Date("2016-06-03", DateUtil.YYYYMMDD).getTime());
        System.out.println(DateUtil.convertStr2Date("2016-06-04", DateUtil.YYYYMMDD).getTime());
//
//        System.out.println(DateUtil.getDayDate(new Date()).getTime());

    }

}
