package com.fy.boss.platform.migu;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fy.boss.game.model.Server;
import com.fy.boss.game.service.ServerManager;
import com.fy.boss.platform.migu.MiguWorker.UrlInfo;
import com.fy.boss.tools.JacksonManager;
import com.fy.boss.transport.BossServerService;
import com.xuanzhi.tools.servlet.HttpUtils;
import com.xuanzhi.tools.text.JsonUtil;
import com.xuanzhi.tools.text.ParamUtils;

public class MiGuSalePlayerServlet extends HttpServlet {
//TODO 需要限制ip
	//更新相关的订单状态
	public static Logger logger = BossServerService.logger;
	
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		long startTime = System.currentTimeMillis(); 
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		JacksonManager jacksonManager = JacksonManager.getInstance();
		String myappkey = "wangxian";
		String secretkey = "%$wang2014";
		/**
		 * 查询装备列表
		 * 首先获取可出售的装备，需要http通讯
		 * 在这里需要限制ip，验证用户有效性，
		 */
		
		String appkey = ParamUtils.getParameter(req, "appkey");
		String service = ParamUtils.getParameter(req, "service");
		String submittime = ParamUtils.getParameter(req, "submittime");
		String sign = ParamUtils.getParameter(req, "sign");
		String username = ParamUtils.getParameter(req, "useraccount");
		String saleprice = ParamUtils.getParameter(req, "saleprice");			//上架定价
		String roleid = ParamUtils.getParameter(req, "roleid");				//登陆米谷的角色id
		String servername =  URLDecoder.decode( ParamUtils.getParameter(req, "servername"),"utf-8");
		String platform = ParamUtils.getParameter(req, "platform");
		String channel = ParamUtils.getParameter(req, "channel");
		
		String mac  = ParamUtils.getParameter(req, "mac");
		String aid  = ParamUtils.getParameter(req, "aid");
		
		String enterTime = ParamUtils.getParameter(req, "entertime");
		String saleroleid = ParamUtils.getParameter(req, "saleroleid");			//要出售的角色id
		String saleservername = ParamUtils.getParameter(req, "saleservername");	//要出售的角色名服务器名
		String clientid = ParamUtils.getParameter(req, "clientid");	//要出售的角色名服务器名
		String devicecode = "";
		
		if(MiguWorker.CURRENT_VALIDATE_REQ_TYPE ==MiguWorker.VALIDATE_REQUEST_TYPE_DEVICECODE)
		{
			if(!StringUtils.isEmpty(mac) && !"02:00:00:00:00:00".equals(mac))
			{
				devicecode = mac;
			}
			else if(!StringUtils.isEmpty(aid))
			{
				devicecode = aid;
			}
		}
		else if(MiguWorker.CURRENT_VALIDATE_REQ_TYPE ==MiguWorker.VALIDATE_REQUEST_TYPE_ENTERSERVER_TIME)
		{
			if(!StringUtils.isEmpty(enterTime) )
			{
				devicecode = enterTime;
			}
		}
		
		String miguuid = ParamUtils.getParameter(req, "miguuserid");
	
		String mysign = MiguWorker.genSign(req.getParameterMap(), myappkey, secretkey);
		
		Map map = new LinkedHashMap();
		map.put("appkey", "wangxian");
		map.put("service", "SYS10010");
		map.put("submittime", System.currentTimeMillis()/1000);
		map.put("roleid", roleid);
		
		String cacheKey = MiguWorker.buildKey(username, roleid, servername);
		int validateRequestResult = MiguWorker.validateRequest(cacheKey, devicecode);
		
		if(validateRequestResult != 1)
		{
			map.put("result", 3);
			map.put("type", -1);
			map.put("recordList", new ArrayList());
			res.getWriter().write(jacksonManager.toJsonString(map));
			logger.warn("[米谷通讯] [验证合法请求] [失败] [不是合法请求] ["+username+"] ["+roleid+"] ["+servername+"] ["+map+"]");
			return;
		}
		
		
		if(mysign.equalsIgnoreCase(sign))
		{
			if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(roleid) 
					&& !StringUtils.isEmpty(servername) && !StringUtils.isEmpty(platform) && !StringUtils.isEmpty(channel))
			{
				String jsons = "";
				HashMap headers = new HashMap();
				
				String now = System.currentTimeMillis() + "";
				Map<String,String> paramMap = new LinkedHashMap();
				paramMap.put("op", "giveprice4role");				//出售角色
				paramMap.put("playerId", roleid);
				paramMap.put("username", username);
				paramMap.put("servername", URLEncoder.encode(servername,"utf-8"));
				paramMap.put("platform", platform);
				paramMap.put("channel", channel);
				paramMap.put("saleprice", saleprice);
				paramMap.put("clientid", clientid);
				paramMap.put("saleroleid", saleroleid);
				paramMap.put("selledservername", saleservername);
				paramMap.put("mac", (StringUtils.isEmpty(mac)?aid:mac));
				paramMap.put("validExceptionTime", now);			//在游戏服使用此时间与player拼key，在访问超时后验证订单使用
				MiguWorker.addVisitGameUser(paramMap);
				String params = MiguWorker.getSignParam(paramMap);
				String returnString = "";
				
				Server server = ServerManager.getInstance().getServer(saleservername);
//				Server server = ServerManager.getInstance().getServer(servername);
				
				if(server == null)
				{
					logger.error("[米谷通讯] [获取服务器] [失败] [fail] [未知服务器] ["+saleservername+"] ["+username+"]");
					map.put("result", 3);
					map.put("type", -1);
					res.getWriter().write(jacksonManager.toJsonString(map));
					return;
				}
				
				UrlInfo urlInfo = MiguWorker.getUrlInfo(server.getSavingNotifyUrl());
				
				if(urlInfo == null)
				{
					logger.error("[米谷通讯] [获取连接信息] [失败] [fail] [无法获取服务器连接] ["+server.getSavingNotifyUrl()+"] ["+username+"]");
					map.put("result", 3);
					map.put("type", -1);
					res.getWriter().write(jacksonManager.toJsonString(map));
					return;
				}
				
				URL url = new URL(urlInfo.url+":"+urlInfo.port+"/migu/miguNotify");
				try {
					byte bytes[] = HttpUtils.webPost(url,params.getBytes("utf-8"), headers,60000, 60000);
					String encoding = (String)headers.get(HttpUtils.ENCODING);
					Integer code = (Integer)headers.get(HttpUtils.Response_Code);
					String message = (String)headers.get(HttpUtils.Response_Message);
					returnString = new String(bytes,encoding);
				} catch (Exception e) {
					map.put("result", 4);
					map.put("type", -1);
					map.put("recordList", new ArrayList());
					res.getWriter().write(jacksonManager.toJsonString(map));
					logger.info("[米谷通讯] [用户开始角色挂单] [失败] [异常捕获后] [连接服务器失败] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"]",e);
					return;
				}
				
				if(!StringUtils.isEmpty(returnString))
				{
					try
					{
						if (!returnString.contains("falereason")) {
							map.put("result", 1);
							Map lst = new HashMap();
							try
							{
								lst = JsonUtil.objectFromJson(returnString, java.util.Map.class);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							map.put("recordid", lst.get("recordid"));
							map.put("showtime", lst.get("showtime"));
							if(logger.isInfoEnabled())
								logger.info("[米谷通讯] [用户开始挂单] [成功] [ok] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"]");
						} else {
							String temp = URLDecoder.decode(returnString,"utf-8");
							try {
								map.put("result", 0);
								map.put("reason", URLEncoder.encode(temp.split(":")[1],"utf-8"));
								if(logger.isInfoEnabled())
									logger.info("[米谷通讯] [用户开始挂单] [失败] [] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"] [" + URLEncoder.encode(returnString,"utf-8") + "]");
							} catch (Exception e) {
								map.put("result", 0);
								map.put("reason", returnString);
								if(logger.isInfoEnabled())
									logger.info("[米谷通讯] [用户开始挂单] [失败] [] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"] [" + returnString + "]");
								logger.warn("[返回上架失败] [异常] [" + returnString + "]",e);
							}
						}
					}
					catch(Exception e)
					{
						logger.warn("[米谷通讯] [用户开始挂单] [失败] [出现未知异常] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"]",e);
					}
				}
				else
				{
					map.put("result", 3);
					map.put("type", -1);
					map.put("recordList", new ArrayList());
					logger.warn("[米谷通讯] [用户开始挂单] [失败] [游戏服返回失败] ["+url+"] ["+username+"] ["+jsons+"] ["+servername+"] ["+channel+"] ["+roleid+"]");
				}
				
				res.getWriter().write(jacksonManager.toJsonString(map));
				return;
			}
			else
			{
				map.put("result", 3);
				map.put("type", -1);
				map.put("recordList", new ArrayList());
				res.getWriter().write(jacksonManager.toJsonString(map));
				logger.warn("[米谷通讯] [用户开始挂单] [失败] [出现不合法的请求] ["+username+"] ["+servername+"] ["+channel+"] ["+roleid+"]");
				return;
			}
		}
		else
		{
			map.put("result", 2);
			map.put("type", -1);
			map.put("recordList", new ArrayList());
			res.getWriter().write(jacksonManager.toJsonString(map));
			logger.warn("[米谷通讯] [用户开始挂单] [失败] [出现不合法的签名] ["+username+"] ["+servername+"] ["+channel+"] ["+roleid+"] ["+sign+"] ["+mysign+"]");
			return;
		}
	}
	
	/**
	 * [{"id":1222000000019859218,"version":4,"playerId":1395000000000015087,"serverRealName":"仙魂侠魄","userName":"yuwenbao","level":3,"playerName":"大仙","career":1,"playerRMB":0,"playerVIP":0,"lastAccessTime":1401459750502}]
	 * @param map
	 * @return
	 */
	
	public SimplePlayerInfo convertSimlePlayer(Map map)
	{
		SimplePlayerInfo simplePlayerInfo = new SimplePlayerInfo();
		simplePlayerInfo.roleid = (String)map.get("playerId");
		simplePlayerInfo.rolename = (String)map.get("playerName");
		simplePlayerInfo.servername = (String)map.get("serverRealName");
		simplePlayerInfo.rolevocation = (Integer)map.get("career");
		simplePlayerInfo.rolelevel = (Integer)map.get("level");
		return simplePlayerInfo;
	}
	
	public static class SimplePlayerInfo
	{
		public String roleid;
		public String rolename;
		public String servername;
		public int rolevocation;
		public int rolelevel;
	}
	
}