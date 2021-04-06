package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;

import com.fy.engineserver.datasource.article.entity.client.ArticleEntity;


/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 客户端查询某个怪，有什么东西可以捡的<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>spriteId</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>money</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities.length</td><td>int</td><td>4个字节</td><td>ArticleEntity数组长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[0].iconId.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[0].iconId</td><td>String</td><td>articleEntities[0].iconId.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[0].showName.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[0].showName</td><td>String</td><td>articleEntities[0].showName.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[0].bindStyle</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[0].assignFlag</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[0].id</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[0].colorType</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[1].iconId.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[1].iconId</td><td>String</td><td>articleEntities[1].iconId.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[1].showName.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[1].showName</td><td>String</td><td>articleEntities[1].showName.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[1].bindStyle</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[1].assignFlag</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[1].id</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[1].colorType</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[2].iconId.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[2].iconId</td><td>String</td><td>articleEntities[2].iconId.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[2].showName.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[2].showName</td><td>String</td><td>articleEntities[2].showName.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[2].bindStyle</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[2].assignFlag</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>articleEntities[2].id</td><td>long</td><td>8个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>articleEntities[2].colorType</td><td>byte</td><td>1个字节</td><td>对象属性配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td colspan=4>......... 重复</td></tr>
 * </table>
 */
public class QUERY_FLOPAVAILABLE_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	long spriteId;
	int money;
	ArticleEntity[] articleEntities;

	public QUERY_FLOPAVAILABLE_RES(){
	}

	public QUERY_FLOPAVAILABLE_RES(long seqNum,long spriteId,int money,ArticleEntity[] articleEntities){
		this.seqNum = seqNum;
		this.spriteId = spriteId;
		this.money = money;
		this.articleEntities = articleEntities;
	}

	public QUERY_FLOPAVAILABLE_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		spriteId = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		money = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 8192) throw new Exception("object array length ["+len+"] big than the max length [8192]");
		articleEntities = new ArticleEntity[len];
		for(int i = 0 ; i < articleEntities.length ; i++){
			articleEntities[i] = new ArticleEntity();
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			articleEntities[i].setIconId(new String(content,offset,len,"UTF-8"));
			offset += len;
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			articleEntities[i].setShowName(new String(content,offset,len,"UTF-8"));
			offset += len;
			articleEntities[i].setBindStyle((byte)mf.byteArrayToNumber(content,offset,1));
			offset += 1;
			articleEntities[i].setAssignFlag((byte)mf.byteArrayToNumber(content,offset,1));
			offset += 1;
			articleEntities[i].setId((long)mf.byteArrayToNumber(content,offset,8));
			offset += 8;
			articleEntities[i].setColorType((byte)mf.byteArrayToNumber(content,offset,1));
			offset += 1;
		}
	}

	public int getType() {
		return 0x700000D6;
	}

	public String getTypeDescription() {
		return "QUERY_FLOPAVAILABLE_RES";
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
		for(int i = 0 ; i < articleEntities.length ; i++){
			len += 2;
			if(articleEntities[i].getIconId() != null){
				try{
				len += articleEntities[i].getIconId().getBytes("UTF-8").length;
				}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			}
			len += 2;
			if(articleEntities[i].getShowName() != null){
				try{
				len += articleEntities[i].getShowName().getBytes("UTF-8").length;
				}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			}
			len += 1;
			len += 1;
			len += 8;
			len += 1;
		}
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

			buffer.putLong(spriteId);
			buffer.putInt(money);
			buffer.putInt(articleEntities.length);
			for(int i = 0 ; i < articleEntities.length ; i++){
				byte[] tmpBytes2 ;
				try{
				tmpBytes2 = articleEntities[i].getIconId().getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
				try{
				tmpBytes2 = articleEntities[i].getShowName().getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
				buffer.put((byte)articleEntities[i].getBindStyle());
				buffer.put((byte)articleEntities[i].getAssignFlag());
				buffer.putLong(articleEntities[i].getId());
				buffer.put((byte)articleEntities[i].getColorType());
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
	 *	怪的Id
	 */
	public long getSpriteId(){
		return spriteId;
	}

	/**
	 * 设置属性：
	 *	怪的Id
	 */
	public void setSpriteId(long spriteId){
		this.spriteId = spriteId;
	}

	/**
	 * 获取属性：
	 *	无帮助说明
	 */
	public int getMoney(){
		return money;
	}

	/**
	 * 设置属性：
	 *	无帮助说明
	 */
	public void setMoney(int money){
		this.money = money;
	}

	/**
	 * 获取属性：
	 *	物品掉落集合
	 */
	public ArticleEntity[] getArticleEntities(){
		return articleEntities;
	}

	/**
	 * 设置属性：
	 *	物品掉落集合
	 */
	public void setArticleEntities(ArticleEntity[] articleEntities){
		this.articleEntities = articleEntities;
	}

}