package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;

import com.fy.engineserver.homestead.cave.CaveScheduleForClient;
import com.fy.engineserver.homestead.cave.ResourceCollection;


/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 获得仙府的详细信息<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>caveValid</td><td>boolean</td><td>1个字节</td><td>布尔型长度,0==false，其他为true</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>currScheduleNum</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>maxScheduleNum</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>increaseScheduleLastTime</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules.length</td><td>int</td><td>4个字节</td><td>CaveScheduleForClient数组长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[0].name.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[0].name</td><td>String</td><td>schedules[0].name.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[0].optionType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[0].buildingType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[0].lastTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[0].leftTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[0].scheduleIcon.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[0].scheduleIcon</td><td>String</td><td>schedules[0].scheduleIcon.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[0].scheduleDescription.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[0].scheduleDescription</td><td>String</td><td>schedules[0].scheduleDescription.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[1].name.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[1].name</td><td>String</td><td>schedules[1].name.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[1].optionType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[1].buildingType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[1].lastTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[1].leftTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[1].scheduleIcon.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[1].scheduleIcon</td><td>String</td><td>schedules[1].scheduleIcon.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[1].scheduleDescription.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[1].scheduleDescription</td><td>String</td><td>schedules[1].scheduleDescription.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[2].name.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[2].name</td><td>String</td><td>schedules[2].name.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[2].optionType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[2].buildingType</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[2].lastTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[2].leftTime</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[2].scheduleIcon.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[2].scheduleIcon</td><td>String</td><td>schedules[2].scheduleIcon.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>schedules[2].scheduleDescription.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>schedules[2].scheduleDescription</td><td>String</td><td>schedules[2].scheduleDescription.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td colspan=4>......... 重复</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>currResource.food</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>currResource.wood</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>currResource.stone</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>currMaxResource.food</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>currMaxResource.wood</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>currMaxResource.stone</td><td>int</td><td>4个字节</td><td>对象属性配置的长度</td></tr>
 * </table>
 */
public class CAVE_DETAILED_INFO_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	boolean caveValid;
	int currScheduleNum;
	int maxScheduleNum;
	long increaseScheduleLastTime;
	CaveScheduleForClient[] schedules;
	ResourceCollection currResource;
	ResourceCollection currMaxResource;

	public CAVE_DETAILED_INFO_RES(){
	}

	public CAVE_DETAILED_INFO_RES(long seqNum,boolean caveValid,int currScheduleNum,int maxScheduleNum,long increaseScheduleLastTime,CaveScheduleForClient[] schedules,ResourceCollection currResource,ResourceCollection currMaxResource){
		this.seqNum = seqNum;
		this.caveValid = caveValid;
		this.currScheduleNum = currScheduleNum;
		this.maxScheduleNum = maxScheduleNum;
		this.increaseScheduleLastTime = increaseScheduleLastTime;
		this.schedules = schedules;
		this.currResource = currResource;
		this.currMaxResource = currMaxResource;
	}

	public CAVE_DETAILED_INFO_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		caveValid = mf.byteArrayToNumber(content,offset,1) != 0;
		offset += 1;
		currScheduleNum = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		maxScheduleNum = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		increaseScheduleLastTime = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 8192) throw new Exception("object array length ["+len+"] big than the max length [8192]");
		schedules = new CaveScheduleForClient[len];
		for(int i = 0 ; i < schedules.length ; i++){
			schedules[i] = new CaveScheduleForClient();
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			schedules[i].setName(new String(content,offset,len,"UTF-8"));
			offset += len;
			schedules[i].setOptionType((int)mf.byteArrayToNumber(content,offset,4));
			offset += 4;
			schedules[i].setBuildingType((int)mf.byteArrayToNumber(content,offset,4));
			offset += 4;
			schedules[i].setLastTime((long)mf.byteArrayToNumber(content,offset,8));
			offset += 8;
			schedules[i].setLeftTime((long)mf.byteArrayToNumber(content,offset,8));
			offset += 8;
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			schedules[i].setScheduleIcon(new String(content,offset,len,"UTF-8"));
			offset += len;
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			schedules[i].setScheduleDescription(new String(content,offset,len,"UTF-8"));
			offset += len;
		}
		currResource = new ResourceCollection();
		currResource.setFood((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
		currResource.setWood((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
		currResource.setStone((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
		currMaxResource = new ResourceCollection();
		currMaxResource.setFood((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
		currMaxResource.setWood((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
		currMaxResource.setStone((int)mf.byteArrayToNumber(content,offset,4));
		offset += 4;
	}

	public int getType() {
		return 0x8F00002E;
	}

	public String getTypeDescription() {
		return "CAVE_DETAILED_INFO_RES";
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
		len += 1;
		len += 4;
		len += 4;
		len += 8;
		len += 4;
		for(int i = 0 ; i < schedules.length ; i++){
			len += 2;
			if(schedules[i].getName() != null){
				try{
				len += schedules[i].getName().getBytes("UTF-8").length;
				}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			}
			len += 4;
			len += 4;
			len += 8;
			len += 8;
			len += 2;
			if(schedules[i].getScheduleIcon() != null){
				try{
				len += schedules[i].getScheduleIcon().getBytes("UTF-8").length;
				}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			}
			len += 2;
			if(schedules[i].getScheduleDescription() != null){
				try{
				len += schedules[i].getScheduleDescription().getBytes("UTF-8").length;
				}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			}
		}
		len += 4;
		len += 4;
		len += 4;
		len += 4;
		len += 4;
		len += 4;
		packet_length = len;
		return len;
	}

	public int writeTo(ByteBuffer buffer) {
		int messageLength = getLength();
		if(buffer.remaining() < messageLength) return 0;
		int oldPos = buffer.position();
		buffer.mark();
		try{
			buffer.put(mf.numberToByteArray(messageLength,mf.getNumOfByteForMessageLength()));
			buffer.putInt(getType());
			buffer.putInt((int)seqNum);

			buffer.put((byte)(caveValid==false?0:1));
			buffer.putInt(currScheduleNum);
			buffer.putInt(maxScheduleNum);
			buffer.putLong(increaseScheduleLastTime);
			buffer.putInt(schedules.length);
			for(int i = 0 ; i < schedules.length ; i++){
				byte[] tmpBytes2 ;
				try{
				tmpBytes2 = schedules[i].getName().getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
				buffer.putInt((int)schedules[i].getOptionType());
				buffer.putInt((int)schedules[i].getBuildingType());
				buffer.putLong(schedules[i].getLastTime());
				buffer.putLong(schedules[i].getLeftTime());
				try{
				tmpBytes2 = schedules[i].getScheduleIcon().getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
				try{
				tmpBytes2 = schedules[i].getScheduleDescription().getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
			}
			buffer.putInt((int)currResource.getFood());
			buffer.putInt((int)currResource.getWood());
			buffer.putInt((int)currResource.getStone());
			buffer.putInt((int)currMaxResource.getFood());
			buffer.putInt((int)currMaxResource.getWood());
			buffer.putInt((int)currMaxResource.getStone());
		}catch(Exception e){
		 e.printStackTrace();
			buffer.reset();
			throw new RuntimeException("in writeTo method catch exception :",e);
		}
		int newPos = buffer.position();
		buffer.position(oldPos);
		buffer.put(mf.numberToByteArray(newPos-oldPos,mf.getNumOfByteForMessageLength()));
		buffer.position(newPos);
		return newPos-oldPos;
	}

	/**
	 * 获取属性：
	 *	仙府是否有效.无效则不用显示下面信息
	 */
	public boolean getCaveValid(){
		return caveValid;
	}

	/**
	 * 设置属性：
	 *	仙府是否有效.无效则不用显示下面信息
	 */
	public void setCaveValid(boolean caveValid){
		this.caveValid = caveValid;
	}

	/**
	 * 获取属性：
	 *	当前使用了的进度数
	 */
	public int getCurrScheduleNum(){
		return currScheduleNum;
	}

	/**
	 * 设置属性：
	 *	当前使用了的进度数
	 */
	public void setCurrScheduleNum(int currScheduleNum){
		this.currScheduleNum = currScheduleNum;
	}

	/**
	 * 获取属性：
	 *	最多进度数
	 */
	public int getMaxScheduleNum(){
		return maxScheduleNum;
	}

	/**
	 * 设置属性：
	 *	最多进度数
	 */
	public void setMaxScheduleNum(int maxScheduleNum){
		this.maxScheduleNum = maxScheduleNum;
	}

	/**
	 * 获取属性：
	 *	增加进度剩余时间,0表示无
	 */
	public long getIncreaseScheduleLastTime(){
		return increaseScheduleLastTime;
	}

	/**
	 * 设置属性：
	 *	增加进度剩余时间,0表示无
	 */
	public void setIncreaseScheduleLastTime(long increaseScheduleLastTime){
		this.increaseScheduleLastTime = increaseScheduleLastTime;
	}

	/**
	 * 获取属性：
	 *	正在执行的工作进度
	 */
	public CaveScheduleForClient[] getSchedules(){
		return schedules;
	}

	/**
	 * 设置属性：
	 *	正在执行的工作进度
	 */
	public void setSchedules(CaveScheduleForClient[] schedules){
		this.schedules = schedules;
	}

	/**
	 * 获取属性：
	 *	当前资源
	 */
	public ResourceCollection getCurrResource(){
		return currResource;
	}

	/**
	 * 设置属性：
	 *	当前资源
	 */
	public void setCurrResource(ResourceCollection currResource){
		this.currResource = currResource;
	}

	/**
	 * 获取属性：
	 *	资源上限
	 */
	public ResourceCollection getCurrMaxResource(){
		return currMaxResource;
	}

	/**
	 * 设置属性：
	 *	资源上限
	 */
	public void setCurrMaxResource(ResourceCollection currMaxResource){
		this.currMaxResource = currMaxResource;
	}

}