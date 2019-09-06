package com.ltmonitor.jt809.protocol.receive;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.jt809.tool.Tools;

/**
 * 查岗请求
 * @author admin
 *
 */
public class DownPlatformMsgPostQueryReq implements IReceiveProtocol {
	private static Logger logger = Logger
			.getLogger(DownPlatformMsgPostQueryReq.class);

	public String handle(JT809Message message) {

		String mess = message.getMessageBody();

		//int dataType = mp.getInt(2);
		//int dataLength = mp.getInt(4);
		
		MessageParser mp = new MessageParser(mess,6);
		int objType = mp.getInt(1); //查岗对象类型
		String objId = mp.getString(12); //查岗对象ID
		int infoId = mp.getInt(4); //信息ID
		int infoLength = mp.getInt(4);  //信息内容长度
		String content = mp.getString(infoLength);
		/**
		CheckRecord cm = new CheckRecord();
		cm.setInfoId(infoId);
		cm.setObjId(objId);
		cm.setObjType(objType);
		cm.setMessage(content);
		*/
		
		StringBuilder sb = new StringBuilder();
		sb.append(infoId).append(';')
		.append(objId).append(';')
		.append(objType).append(';')
		.append(content);
		message.setMsgDescr(sb.toString());
		/**
		GovPlatformMsg gf = new GovPlatformMsg();
		gf.setMsg(sb.toString());
		gf.setMsgType(GovPlatformMsg.TYPE_POST_QUERY);
		try {
			//ServiceLauncher.getBaseDao().save(gf);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		*/
		
		
		

		//PlatformChaGangModel pm = new PlatformChaGangModel(cm);
		String backContent = "";
		/**
		if (objType == 1) {
			logger.warn("自动查岗查岗:objID:"+objId+"|infoid:"+infoId+"|content:" + content);
			backContent = "当前在线业户列表:=" + GlobalConfig.parModel.getLicenseNo() + "|"
					+ GlobalConfig.parModel.getUsername();
			String autoContent = Tools.TurnISN(backContent);
			//pm.setMessage(autoContent);
			cm.setAnswer("2");
			T809Manager.UpPlatFormMsgPostQueryAck(cm);
			
		} else {
			//logger.warn("人工查岗:" + content);
			//pm.setMessage("人工查岗:" + content);
			//T809Manager.UpPlatFormMsgPostQueryAck(cm);
		}*/

		return "";
	}


	private static Timestamp getTimestamp() {
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s = format2.format(new Date());
		Timestamp insTime = Tools.convertToTimestamp(s);
		System.out.println(insTime);
		return insTime;
	}

}
