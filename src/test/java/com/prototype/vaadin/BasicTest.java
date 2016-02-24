package com.prototype.vaadin;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prototype.vaadin.core.util.db.Restriction;

/**
 */
public class BasicTest {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");

	private Date startTime; // 起始時間
	private Map<String, Date> timeRangeMp = new HashMap<String, Date>();

	@Before
	public void before() {
		startTime = new Date();
		
		System.out.println("====================== initialization =====================");
		System.out.println("起始時間 = " + sdf.format(startTime));
		// 載入 spring 設定
	}
	
	@Test
	public void test() {
		System.out.println(Arrays.asList(Restriction.RestrictionType.values()));
	}

	@After
	public void after() {
		Date endTime = new Date();
		Long excuteTime = endTime.getTime() - startTime.getTime();

		System.out.println("======================== completed ========================");
		System.out.println("起始時間 : " + sdf.format(startTime));
		System.out.println("結束時間 : " + sdf.format(endTime));
		System.out.println("總共累計使用時間 : " + parseLongtime(excuteTime));
	}

	public void timeStop() {
		timingEnd("");
	}

	public void timeStart() {
		timingStart("");
	}
	
	public void timingStart(String str) {
		Date prevTime = new Date();
		timeRangeMp.put(str, prevTime);
		
		System.out.println("\t ======================== " + str + " START ========================");
	}
	
	public void timingEnd(String str) {
		Date prevTime = timeRangeMp.get(str);
		
		Date nowtime = new Date();
		Long duringTime = nowtime.getTime() - prevTime.getTime();
		Long excuteTime = nowtime.getTime() - startTime.getTime();
		
		System.out.println("\t " + str + " 計時開始 : " + sdf.format(prevTime));
		System.out.println("\t " + str + " 計時結束 : " + sdf.format(new Date()));
		System.out.println("\t " + str + " 累計使用時間 : " + parseLongtime(duringTime));
		System.out.println("\t 目前累計使用時間 : " + parseLongtime(excuteTime));
		System.out.println("\t ======================== " + str + " END ========================");
	}
	
	private static String parseLongtime(long longtime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(longtime - TimeZone.getDefault().getOffset(0));
        
        return sdf.format(c.getTime());
	}
	
}
