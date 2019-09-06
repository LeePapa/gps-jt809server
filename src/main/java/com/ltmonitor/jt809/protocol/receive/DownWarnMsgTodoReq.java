package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.WarnMsgUrgeTodoReq;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.util.DateUtil;

/**
 * 报警督办请求
 * 
 * @author DELL
 * 
 */
public class DownWarnMsgTodoReq implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownWarnMsgTodoReq.class);

	public String handle(JT809Message message) {
		String dataBody = message.getMessageBody();

		MessageParser mp = new MessageParser(dataBody);
		message.setPlateNo(mp.getString(21));

		message.setPlateColor(mp.getInt(1));

		message.setSubType(mp.getInt(2));
		int dataLength = mp.getInt(4);

		WarnMsgUrgeTodoReq wd = new WarnMsgUrgeTodoReq();
		wd.setWarnSrc(mp.getInt(1));
		wd.setWarnType(mp.getInt(2));
		wd.setWarnTime(mp.getUtcDate()); //报警时间
		wd.setSupervicsionId(mp.getLong(4));
		wd.setSupervisionEndtime(mp.getUtcDate());//督办截至时间
		wd.setSupervisionLevel((byte) mp.getInt(1));
		wd.setSupervisor(mp.getString(16));
		wd.setSupervisorTel(mp.getString(20));
		wd.setSupervisorEmail(mp.getString(30));
		
		wd.setPlateNo(message.getPlateNo());
		wd.setPlateColor(message.getPlateColor());
		String strDate = DateUtil.datetimeToString(wd.getWarnTime());
		message.setMsgDescr("督办Id:" + wd.getSupervicsionId() + "督办人:"
				+ wd.getSupervisor() + "督办电话:" + wd.getSupervisorTel()
				+ "报警时间:" + strDate);
		try {
			ServiceLauncher.getWarnMsgUrgeTodoReqService().save(wd);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
	
		return "";
	}

}
