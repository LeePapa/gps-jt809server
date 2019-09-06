package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;


public class DownExgMsgReturnEnd implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownExgMsgReturnEnd.class);

	public String handle(JT809Message message) {
		String dataBody = message.getMessageBody();
		MessageParser mp = new MessageParser(dataBody);

		String plateNo = mp.getString(21);

		int plateColor = mp.getInt(1);

		int dataType = mp.getInt(2);

		int dataLength = mp.getInt(4);

		int reasonCode = mp.getInt(1);
		//1206
		T809Manager.UpExgMsgReturnEndAck(plateNo, plateColor);


		return "";
	}


}
