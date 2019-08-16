package com.flexibleemployment.enums;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @Description
 * @Author:daizhiyue
 * @Date:23:07 2018/12/15
 */
@Getter
public enum RealTimeRedisHourEnum {
    Hour_0("0", "0小时时段", new BigDecimal("1.7"), new BigDecimal("1.7"), new BigDecimal("0.9"), new BigDecimal("0.9")),
    Hour_1("1", "1小时时段", new BigDecimal("0.8"), new BigDecimal("2.4"), new BigDecimal("0.4"), new BigDecimal("1.3")),
    Hour_2("2", "2小时时段", new BigDecimal("0.4"), new BigDecimal("2.9"), new BigDecimal("0.4"), new BigDecimal("1.7")),
    Hour_3("3", "3小时时段", new BigDecimal("0.2"), new BigDecimal("3.0"), new BigDecimal("0.1"), new BigDecimal("1.8")),
    Hour_4("4", "4小时时段", new BigDecimal("0.3"), new BigDecimal("3.3"), new BigDecimal("0.2"), new BigDecimal("2.0")),
    Hour_5("5", "5小时时段", new BigDecimal("0.4"), new BigDecimal("3.8"), new BigDecimal("0.4"), new BigDecimal("2.4")),
    Hour_6("6", "6小时时段", new BigDecimal("1.5"), new BigDecimal("5.3"), new BigDecimal("1.0"), new BigDecimal("3.4")),
    Hour_7("7", "7小时时段", new BigDecimal("4.4"), new BigDecimal("9.7"), new BigDecimal("2.5"), new BigDecimal("5.8")),
    Hour_8("8", "8小时时段", new BigDecimal("4.1"), new BigDecimal("13.8"), new BigDecimal("3.8"), new BigDecimal("9.7")),
    Hour_9("9", "9小时时段", new BigDecimal("6.3"), new BigDecimal("20.0"), new BigDecimal("6.3"), new BigDecimal("16.0")),
    Hour_10("10", "10小时时段", new BigDecimal("7.3"), new BigDecimal("27.3"), new BigDecimal("7.8"), new BigDecimal("23.8")),
    Hour_11("11", "11小时时段", new BigDecimal("9.8"), new BigDecimal("37.1"), new BigDecimal("8.3"), new BigDecimal("32.1")),
    Hour_12("12", "12小时时段", new BigDecimal("5.8"), new BigDecimal("42.9"), new BigDecimal("6.7"), new BigDecimal("38.7")),
    Hour_13("13", "13小时时段", new BigDecimal("5.4"), new BigDecimal("48.3"), new BigDecimal("6.3"), new BigDecimal("45.1")),
    Hour_14("14", "14小时时段", new BigDecimal("6.4"), new BigDecimal("54.8"), new BigDecimal("6.9"), new BigDecimal("51.9")),
    Hour_15("15", "15小时时段", new BigDecimal("7.5"), new BigDecimal("62.2"), new BigDecimal("7.7"), new BigDecimal("59.6")),
    Hour_16("16", "16小时时段", new BigDecimal("6.6"), new BigDecimal("68.9"), new BigDecimal("6.8"), new BigDecimal("66.4")),
    Hour_17("17", "17小时时段", new BigDecimal("6.3"), new BigDecimal("75.2"), new BigDecimal("8.0"), new BigDecimal("74.4")),
    Hour_18("18", "18小时时段", new BigDecimal("4.2"), new BigDecimal("79.4"), new BigDecimal("6.5"), new BigDecimal("80.9")),
    Hour_19("19", "19小时时段", new BigDecimal("4.8"), new BigDecimal("84.2"), new BigDecimal("4.3"), new BigDecimal("85.2")),
    Hour_20("20", "20小时时段", new BigDecimal("4.4"), new BigDecimal("88.6"), new BigDecimal("4.6"), new BigDecimal("89.7")),
    Hour_21("21", "21小时时段", new BigDecimal("4.6"), new BigDecimal("93.1"), new BigDecimal("4.2"), new BigDecimal("93.9")),
    Hour_22("22", "22小时时段", new BigDecimal("3.9"), new BigDecimal("97.1"), new BigDecimal("3.5"), new BigDecimal("97.4")),
    Hour_23("23", "23小时时段", new BigDecimal("2.9"), new BigDecimal("100"), new BigDecimal("2.6"), new BigDecimal("100")),;

    String hour;

    String desc;
    /**
     * 节假日-日均分时占比(百分比)
     */
    BigDecimal holidayDayHourRate;

    /**
     * 节假日-日均累计占比(百分比)
     */
    BigDecimal holidayDayHourTotalRate;
    /**
     * 工作日-日均分时占比(百分比)
     */
    BigDecimal workdayDayHourRate;

    /**
     * 工作日-日均累计占比(百分比)
     */
    BigDecimal workdayDayHourTotalRate;


    RealTimeRedisHourEnum(String hour, String desc,
                          BigDecimal holidayDayHourRate, BigDecimal holidayDayHourTotalRate,
                          BigDecimal workdayDayHourRate, BigDecimal workdayDayHourTotalRate) {
        this.hour = hour;
        this.desc = desc;
        this.holidayDayHourRate = holidayDayHourRate;
        this.holidayDayHourTotalRate = holidayDayHourTotalRate;
        this.workdayDayHourRate = workdayDayHourRate;
        this.workdayDayHourTotalRate = workdayDayHourTotalRate;


    }

    public static RealTimeRedisHourEnum getRealTimeRedisHourEnumByHour(String hour) {
        for (RealTimeRedisHourEnum realTimeRedisHourEnum : RealTimeRedisHourEnum.values()) {
            if (realTimeRedisHourEnum.getHour().equals(hour)) {
                return realTimeRedisHourEnum;
            }
        }
        return null;
    }

}
