package com.hansson.rento;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hansson.rento.dao.AuditEventDAO;
import com.hansson.rento.dao.DailyAuditEventDAO;
import com.hansson.rento.dao.DailyAuditEventDAOBean;
import com.hansson.rento.dao.MockDailyAuditEventDAOBean;
import com.hansson.rento.entities.AuditEvent;
import com.hansson.rento.entities.DailyAuditEvent;

@Service
public class AuditService {

	private static final Logger mLog = LoggerFactory.getLogger("rento");

	@Autowired
	private AuditEventDAO mAuditEventDAO;

	@Autowired
	private DailyAuditEventDAO mDailyAuditEventDAO;

	public void putAuditEvent(String event, String extra) {
		AuditEvent auditEvent = new AuditEvent(event, extra);
		mAuditEventDAO.create(auditEvent);
	}

	@Scheduled(cron = "0 0 0 * * * *")
	public void dailyAuditAggregation() {
		long timeSpent = new Date().getTime();
		List<AuditEvent> auditEvents = mAuditEventDAO.findAll();
		List<DailyAuditEvent> dailyEvents = mDailyAuditEventDAO.findAll();
		for (AuditEvent event : auditEvents) {
			DailyAuditEvent dailyAuditEvent = new DailyAuditEvent(event.getEvent(), event.getExtra());
			Date date = DateUtils.truncate(event.getDate(), Calendar.DAY_OF_MONTH);
			dailyAuditEvent.setDate(date);
			int indexOf = dailyEvents.indexOf(dailyAuditEvent);
			if(indexOf > -1) {
				dailyAuditEvent = dailyEvents.get(indexOf);
				dailyAuditEvent.add();
			}  else {
				dailyEvents.add(dailyAuditEvent);
			}
		}
		for(DailyAuditEvent event : dailyEvents) {
			mDailyAuditEventDAO.create(event);
		}
		timeSpent = new Date().getTime() - timeSpent;
		timeSpent = timeSpent / 1000;
		mLog.info("Daliy audit aggregation: " + timeSpent + " seconds");
	}

	@Scheduled(cron = "0 10 0 * * 1 *")
	public void weeklyAuditAggregation() {

	}

	@Scheduled(cron = "0 15 0 1 * * *")
	public void monthlyAuditAggregation() {

	}

	public void setAuditEventDAO(AuditEventDAO auditEventDAO) {
		mAuditEventDAO = auditEventDAO;
	}

	public void setDailyAuditEventDAO(DailyAuditEventDAO dailyAuditEventDAO) {
		mDailyAuditEventDAO = dailyAuditEventDAO;
	}

}
