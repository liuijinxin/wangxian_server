package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 激活或者重置坐骑装备孔<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>horseEquId</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>inlayIndex</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>inlayColor</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>needArticles.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>needArticles[0].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>needArticles[0]</td><td>String</td><td>needArticles[0].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>needArticles[1].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>needArticles[1]</td><td>String</td><td>needArticles[1].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>needArticles[2].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>needArticles[2]</td><td>String</td><td>needArticles[2].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td colspan=4>......... 重复</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>haveNums.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>haveNums</td><td>int[]</td><td>haveNums.length</td><td>*</td></tr>
 * </table>
 */
public class ACTIVITY_HORSEEQU_INLAY_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	long horseEquId;
	int inlayIndex;
	int inlayColor;
	String[] needArticles;
	int[] haveNums;

	public ACTIVITY_HORSEEQU_INLAY_RES(){
	}

	public ACTIVITY_HORSEEQU_INLAY_RES(long seqNum,long horseEquId,int inlayIndex,int inlayColor,String[] needArticles,int[] haveNums){
		this.seqNum = seqNum;
		this.horseEquId = horseEquId;
		this.inlayIndex = inlayIndex;
		this.inlayColor = inlayColor;
		this.needArticles = needArticles;
		this.haveNums = haveNums;
	}

	public ACTIVITY_HORSEEQU_INLAY_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		horseEquId = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		inlayIndex = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		inlayColor = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 4096) throw new Exception("array length ["+len+"] big than the max length [4096]");
		needArticles = new String[len];
		for(int i = 0 ; i < needArticles.length ; i++){
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			needArticles[i] = new String(content,offset,len,"UTF-8");
		offset += len;
		}
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 4096) throw new Exception("array length ["+len+"] big than the max length [4096]");
		haveNums = new int[len];
		for(int i = 0 ; i < haveNums.length ; i++){
			haveNums[i] = (int)mf.byteArrayToNumber(content,offset,4);
			offset += 4;
		}
	}

	public int getType() {
		return 0x70FFF101;
	}

	public String getTypeDescription() {
		return "ACTIVITY_HORSEEQU_INLAY_RES";
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
		len += 8;
		len += 4;
		len += 4;
		len += 4;
		for(int i = 0 ; i < needArticles.length; i++){
			len += 2;
			try{
				len += needArticles[i].getBytes("UTF-8").length;
			}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
				throw new RuntimeException("unsupported encoding [UTF-8]",e);
			}
		}
		len += 4;
		len += haveNums.length * 4;
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

			buffer.putLong(horseEquId);
			buffer.putInt(inlayIndex);
			buffer.putInt(inlayColor);
			buffer.putInt(needArticles.length);
			for(int i = 0 ; i < needArticles.length; i++){
				byte[] tmpBytes2 ;
				try{
				tmpBytes2 = needArticles[i].getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
			}
			buffer.putInt(haveNums.length);
			for(int i = 0 ; i < haveNums.length; i++){
				buffer.putInt(haveNums[i]);
			}
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
	 *	坐骑装备id
	 */
	public long getHorseEquId(){
		return horseEquId;
	}

	/**
	 * 设置属性：
	 *	坐骑装备id
	 */
	public void setHorseEquId(long horseEquId){
		this.horseEquId = horseEquId;
	}

	/**
	 * 获取属性：
	 *	孔索引
	 */
	public int getInlayIndex(){
		return inlayIndex;
	}

	/**
	 * 设置属性：
	 *	孔索引
	 */
	public void setInlayIndex(int inlayIndex){
		this.inlayIndex = inlayIndex;
	}

	/**
	 * 获取属性：
	 *	孔颜色
	 */
	public int getInlayColor(){
		return inlayColor;
	}

	/**
	 * 设置属性：
	 *	孔颜色
	 */
	public void setInlayColor(int inlayColor){
		this.inlayColor = inlayColor;
	}

	/**
	 * 获取属性：
	 *	开孔需要道具名
	 */
	public String[] getNeedArticles(){
		return needArticles;
	}

	/**
	 * 设置属性：
	 *	开孔需要道具名
	 */
	public void setNeedArticles(String[] needArticles){
		this.needArticles = needArticles;
	}

	/**
	 * 获取属性：
	 *	背包内拥有的道具数量
	 */
	public int[] getHaveNums(){
		return haveNums;
	}

	/**
	 * 设置属性：
	 *	背包内拥有的道具数量
	 */
	public void setHaveNums(int[] haveNums){
		this.haveNums = haveNums;
	}

}