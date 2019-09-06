package com.ltmonitor.jt809.protocol.send;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.EWayBill;
import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.ISendProtocol;
import com.ltmonitor.jt809.tool.Tools;


/**
 * 主动上报电子运单 120D
 * @author DELL
 *
 */
public class UpExgMsgReportTakeEWayBill implements ISendProtocol {
	private static Logger logger = Logger
			.getLogger(UpExgMsgReportTakeEWayBill.class);
	private int msgType = 0x1200;
	private int subType = 0x120D;
	private String plateNo;
	private int plateColor;
	private String ebillContent;

	public UpExgMsgReportTakeEWayBill(String plateNo, int plateColor, String ebillContent) {
		this.plateNo = plateNo;
		this.plateColor = plateColor;
		this.ebillContent = ebillContent;
	}

	public JT809Message wrapper() {
		String content = Tools.ToHexString(ebillContent);
		int contentLength = content.length() / 2;
		int dataLength = 4 + contentLength;
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.ToHexString(plateNo, 21))
				.append(Tools.ToHexString(plateColor, 1))
				.append(Tools.ToHexString(subType, 2))
				.append(Tools.ToHexString(dataLength, 4))
				.append(Tools.ToHexString(contentLength, 4))
				.append(content);

		String body = sb.toString();

		JT809Message mm = new JT809Message(msgType,  subType,body);
		mm.setPlateColor(plateColor);
		mm.setPlateNo(plateNo);
		mm.setMsgDescr(ebillContent);
		
		EWayBill eBill = new EWayBill();
		eBill.setPlateColor(this.plateColor);
		eBill.setPlateNo(this.plateNo);
		eBill.seteContent(this.ebillContent);
		GlobalConfig.ewayBillMap.put(this.plateNo, eBill);
		return mm;
	}

}
