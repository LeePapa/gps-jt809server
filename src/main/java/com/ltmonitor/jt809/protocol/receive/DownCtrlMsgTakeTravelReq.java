package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.TerminalCommand;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.jt809.tool.Tools;
import com.ltmonitor.service.JT808Constants;

public class DownCtrlMsgTakeTravelReq implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownCtrlMsgTakeTravelReq.class);

	public String handle(JT809Message message) {
		String receiveContent = message.getMessageBody();

		MessageParser mp = new MessageParser(receiveContent, 28);
		// 行车记录仪 命令子 gb/t19056
		int cmdType = mp.getInt(1);
		TerminalCommand tc = new TerminalCommand();
		tc.setCmdType(JT808Constants.CMD_VEHICLE_RECORDER);

		tc.setCmd("" + cmdType);
		tc.setCmdData("" + cmdType);

		tc.setPlateNo(message.getPlateNo());
		tc.setOwner(TerminalCommand.FROM_GOV);
		try {
			ServiceLauncher.getTerminalCommandService().save(tc);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		/**
		byte result = 1;
		String txtContexnt = "1234455,223";
		//Tools.(result);
		byte[] tempBytes = new byte[20];
		T809Manager.UpCtrlMsgTakeTravelAck(message.getPlateNo(),
				message.getPlateColor(), (byte)cmdType, tempBytes);
		*/
		return "";
	}
}
