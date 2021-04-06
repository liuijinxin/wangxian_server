package com.xuanzhi.tools.query.net;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 生成时间：2009-06-04 11:18:28 759<br>
 * 版本号：null<br>
 * <br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>mobile.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>mobile</td><td>String</td><td>mobile.length</td><td>字符串对应的byte数组</td></tr>
 * </table>
 */
public class MOBILE_REQ implements RequestMessage{

	static MobileQueryFactory mf = MobileQueryFactory.getInstance();

	long seqNum;
	String mobile;

	public MOBILE_REQ(long seqNum,String mobile){
		this.seqNum = seqNum;
		this.mobile = mobile;
	}

	public MOBILE_REQ(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 1024) throw new Exception("string length ["+len+"] big than the max length [1024]");
		mobile = new String(content,offset,len);
		offset += len;
	}

	public int getType() {
		return 0x00000002;
	}

	public String getTypeDescription() {
		return "MOBILE_REQ";
	}

	public String getSequenceNumAsString() {
		return String.valueOf(seqNum);
	}

	public long getSequnceNum(){
		return seqNum;
	}

	private int packet_length = 0;

	public int getLength() {
		if(packet_length > 0) return packet_length;
		int len =  mf.getNumOfByteForMessageLength() + 4 + 4;
		len += 2;
		len +=mobile.getBytes().length;
		packet_length = len;
		return len;
	}

	public int writeTo(ByteBuffer buffer) {
		int messageLength = getLength();
		if(buffer.remaining() < messageLength) return 0;
		buffer.put(mf.numberToByteArray(messageLength,mf.getNumOfByteForMessageLength()));
		buffer.putInt(getType());
		buffer.putInt((int)seqNum);

		byte[] tmpBytes1 = mobile.getBytes();
		buffer.putShort((short)tmpBytes1.length);
		buffer.put(tmpBytes1);
		return messageLength;
	}

	/**
	 * 获取属性：
	 *	手机号码
	 */
	public String getMobile(){
		return mobile;
	}

	/**
	 * 设置属性：
	 *	手机号码
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}

}