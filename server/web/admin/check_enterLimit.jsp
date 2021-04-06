<%@page import="com.xuanzhi.tools.text.StringUtil"%>
<%@ page contentType="text/html;charset=utf-8" import="java.util.*"%>
<%@page import="com.xuanzhi.tools.text.*"%>
<%@page import="com.xuanzhi.tools.servlet.HttpUtils"%>
<%@page import="java.net.URL"%>
<%@page import="org.w3c.dom.*,com.xuanzhi.tools.text.*,com.xuanzhi.tools.cache.diskcache.concrete.DefaultDiskCache"%>
<%@page import="com.fy.boss.gm.XmlServerManager"%>
<%@page import="com.fy.boss.gm.XmlServer"%>
<%
boolean flag = false;
if(flag){ %>
<%!
	String cacheFile = System.getProperty("resin.home","/home/boss/resin_boss_test")+"/webapps/game_boss/WEB-INF/mieshi_player_deny.ddc";

	//username-->ArrayList<PlayerRecordData>
	DefaultDiskCache cache = new DefaultDiskCache(new java.io.File(cacheFile),"mieshi-player-deny-cache",365*24*3600*1000L);

	//day --> userList

	LinkedHashMap<String,EnterLimitData[]> serverInfoMap = new LinkedHashMap<String,EnterLimitData[]>();
	LinkedHashMap<String,Exception> serverExpMap = new LinkedHashMap<String,Exception>();
	LinkedHashMap<String,Integer> serverNewFlag = new LinkedHashMap<String,Integer>();
	
	public String getIPInfo(String ip,boolean showIPAddress) throws Exception{
		if(showIPAddress == false) return "";
		String url = "http://www.youdao.com/smartresult-xml/search.s?type=ip&q="+ip;
		byte content[] = HttpUtils.webGet(new java.net.URL(url),null,1000,1000);
		String s = new String(content,"gbk");
		Document dom = XmlUtil.loadString(s);
		Element root = dom.getDocumentElement();
		Element product = XmlUtil.getChildByName(root,"product");
		Element location = XmlUtil.getChildByName(product,"location");
		return XmlUtil.getText(location,null);
	}

	
	
public static class PlayerRecordData {
	public long lastModifyTime = System.currentTimeMillis();
	// 初始化数据
	public String username;
	public String playername;
	public String network;
	public long playerId;
	public String ua;
	public String channel;
	// 账号注册时间
	public String registerTime;
	/* #################################################################################################################### */
	public boolean online;
	public int level;
	public String gameName;
	public long rmb;
	// 当前主线任务级别
	public int mainTaskLevel;
	// 家族名字
	public String jiazuName;
	// 当前体力
	public int currentTili;
	// 最大坐骑数量
	public int maxHorseNum;
	// 最大宠物数量
	public int maxPetNum;
	// 防外挂答题接收次数
	public int answerAcceptTimes;
	// 防外挂答题正确次数
	public int answerRightTimes;
	// 防外挂答题错误次数
	public int answerWrongTimes;
	// 防外挂答题未答次数
	public int answerNoresponseTimes;
	// 背包格子数量
	public int packageSize;
	// 玩家身上紫装数量(包括完美)
	public int purpleEquipmentNum;
	// 玩家身上橙装数量(包括完美)
	public int orangeEquipmentNum;
	// 当前境界
	public int classLevel;

	// #########################################需要另外记录的数据#########################################//
	// 聊天次数
	public int chatTimes;
	// 喝酒次数
	public int drinkTimes;
	// 出售空格子次数
	public int sealEmptyCellTimes;
	// 接受体力任务次数
	public int tiliTaskAcceptTimes;
	// 求购次数
	public int requestBuyTimes;
	// 摆摊次数
	public int boothSaleTimes;
	// 同意国战次数
	public int guozhanAcceptTimes;
	// 发送带附件的邮件数
	public int sendArticleMailTimes;
	// 快速出售次数
	public int fastSellTimes;
	// 种植次数
	public int plantNum;
	// 合成绑定次数
	public int composeBindTimes;

	public Object obj0;
	public Object obj1;
	public Object obj2;
	public Object obj3;
	public Object obj4;
	public Object obj5;
	public Object obj6;
	public Object obj7;
	public Object obj8;
	public Object obj9;

	public String serverName;
}	
public static class EnterLimitData {
	public String ip;

	public Set<Long> playerIds = new HashSet<Long>();

	public List<PlayerRecordData> recordDatas = new ArrayList<PlayerRecordData>();

	public boolean equals(Object o){
		if(o instanceof EnterLimitData){
			EnterLimitData d = (EnterLimitData)o;
			try{	
			for(PlayerRecordData data : recordDatas){
				boolean find = false;
				for(PlayerRecordData data2 : d.recordDatas){
					if(data2.username != null && data2.username.equals(data.username)){
						find = true;
					}
				}
				if(find == false){
					return false;
				}
			
			}

			for(PlayerRecordData data : d.recordDatas){
                                boolean find = false;
                                for(PlayerRecordData data2 : recordDatas){
                                        if(data2.username != null && data2.username.equals(data.username)){
                                                find = true;
                                        }
                                }
                                if(find == false){
                                        return false;
                                }

                        }
			return true;	
			}catch(Exception e){
				return false;
			}
		}
		return false;
	}
}

public static PlayerRecordData getPlayerRecordData(LinkedHashMap<String,EnterLimitData[]> serverInfoMap,String serverName,String username){

	EnterLimitData[] eldMap = serverInfoMap.get(serverName);
	if(eldMap == null) return null;
	for(EnterLimitData eld : eldMap){
		if(eld.recordDatas == null) continue;
		for(PlayerRecordData data : eld.recordDatas){
			if(data != null && data.username != null && data.username.equals(username)){
				data.serverName = serverName;
				return data;
			}
		}
	}
	return null;
}
public static class IPUsernames{
	public String ip;
	public ArrayList<String> usernames = new ArrayList<String>();
	public ArrayList<String> ua = new ArrayList<String>();
}

public String getServerIp(String serverName){
	XmlServerManager xsm = XmlServerManager.getInstance();
	List<XmlServer> serverList =  xsm.getServers();
	

	for(int i = 0; i < serverList.size(); i++){
		XmlServer server = serverList.get(i);
		String serverIp = server.getUri();
		if (serverIp.contains("game_gateway") || serverIp.contains("112.25.14.11")) {
			continue;
		}
		serverIp = serverIp.substring(0,serverIp.indexOf("gm"));
		if(serverName.equals(server.getDescription())){
			return serverIp;
		}
	}
	return "no_ip";
}
%><%


	String userName = (String)session.getAttribute("username");
	if(userName == null){

		String action = request.getParameter("action");
		if(action != null && action.equals("login")){
			String u = request.getParameter("username");
			String p = request.getParameter("password");

			if(u != null && p != null && u.equals("zhanghuanyu") && p.equals("mijia666") ){
				userName = u;
				session.setAttribute("username",userName);
			}else{
				out.println("<h3>用户名密码不对</h3>");
			}
		}

		if(userName == null){
%>
	<center>
		<h1>请先登录</h1>
		<form>
		<input type="hidden" name="action" value="login">
		请输入用户名：<input type="text" name="username" value="" size=30><br/>
		请输入密码：<input type="text" name="password" value="" size=30><br/>
		<input type="submit" value="提  交">
		</form>
	</center>
<%
		return;
		}
	}	

boolean showOnlineOffline = false;
showOnlineOffline = "true".equals(request.getParameter("showOnlineOffline"));

boolean showIPAddress = true;
showIPAddress = "true".equals(request.getParameter("showIPAddress"));


boolean includeOffline = false;
includeOffline = "true".equals(request.getParameter("includeOffline"));

int lowLevel = 20;
int lowSize = 10; 
String lowLevelStr = request.getParameter("lowLevel");
String lowSizeStr = request.getParameter("lowSize");
        if (lowLevelStr==null || lowSizeStr==null) {
                lowSizeStr="10";
                lowLevelStr="20";
        }
        lowLevel = Integer.valueOf(lowLevelStr);
        lowSize = Integer.valueOf(lowSizeStr);

String args = "";

String action = request.getParameter("action");
if(action == null) action="login";



if(action.equals("query")){
	
	args = "authorize.username=lvfei&authorize.password=lvfei321&enterLimit=true&maxSize="+lowSize+"&level="+lowLevel+"&online="+(showOnlineOffline?"false":"true")+"";

	XmlServerManager xsm = XmlServerManager.getInstance();
	List<XmlServer> serverList =  xsm.getServers();
	
	
	List<String> tencentServers = new ArrayList<String>();
	tencentServers.add("霸气乾坤");
	tencentServers.add("凌霄宝殿");
	tencentServers.add("昆仑圣殿");
	tencentServers.add("太虚幻境");
	tencentServers.add("幽冥山谷");
	tencentServers.add("霸气无双");
	tencentServers.add("仙山琼阁");
	tencentServers.add("烟雨青山");
	tencentServers.add("腾讯内测");
	tencentServers.add("侠骨柔肠");
	tencentServers.add("华山之巅");
	tencentServers.add("神龙摆尾");
	
	LinkedHashMap<String,EnterLimitData[]> map = new LinkedHashMap<String,EnterLimitData[]>();
	
	serverExpMap.clear();
	serverNewFlag.clear();
	
	for(int i = 0; i < serverList.size(); i++){
		XmlServer server = serverList.get(i);
		String serverIp = server.getUri();

		if (serverIp.contains("game_gateway") || serverIp.contains("112.25.14.11")) {
			continue;
		}
		
		serverIp = serverIp.substring(0,serverIp.indexOf("gm"));
		
		String serverName =server.getDescription();
		
		if (serverName.equals("幽恋蝶谷")) {
			continue;
		}
		if (tencentServers.contains(serverName)) {
			continue;
		}
		try{
			byte[] b = HttpUtils.webPost(new URL(serverIp+"/admin/waigua/enterLimit.jsp"), args.getBytes(), new HashMap(), 60000, 60000);
			if(b != null && b.length > 0){
				String json = new String(b);
				EnterLimitData[] eldMap = JsonUtil.objectFromJson(json,EnterLimitData[].class);//好像这里不能用泛型，如果无法用的话，就用其他结构吧
				
				map.put(serverName,eldMap);
				EnterLimitData[] ed = serverInfoMap.get(serverName);
				if(ed == null || eldMap.length > ed.length){
					serverNewFlag.put(serverName,1);
					
				}
			}
		}catch(Exception e){
			serverExpMap.put(serverName+" " + serverIp + "/admin/waigua/enterLimit.jsp?" + args,e);
		}
	}
	serverInfoMap = map;
}

String filterChannel = null;
boolean queryFilterchannel = false;
if(action.equals("query_filterchannel")){
	queryFilterchannel = true;
	
	filterChannel = request.getParameter("channel");
	
}

boolean queryFilterRSA = false;
int queryFilterRSA_RMB = 1000;
String queryFilterRSA_Version = "1.7.25";

if(action.equals("query_filterrsa")){
        queryFilterRSA = true;
	
	queryFilterRSA_RMB = Integer.parseInt(request.getParameter("queryFilterRSA_RMB"));
	queryFilterRSA_Version = request.getParameter("queryFilterRSA_Version");
}


int konggezhinum = 5;
boolean queryFilterKongGeZhi = false;
if(action.equals("query_filterKongGeZhi")){
	queryFilterKongGeZhi = true;

	konggezhinum = Integer.valueOf(request.getParameter("konggezhinum"));
}

String showServerName = "";
if(action.equals("fenghao")){
	//String serverIps = request.getParameter("serverIp");
	filterChannel = request.getParameter("channel");
	queryFilterKongGeZhi = "true".equals(request.getParameter("queryFilterKongGeZhi"));
	if(queryFilterKongGeZhi){
	konggezhinum = Integer.valueOf(request.getParameter("konggezhinum"));
	}
	queryFilterchannel = "true".equals(request.getParameter("queryFilterchannel"));
	queryFilterRSA = "true".equals(request.getParameter("queryFilterRSA"));
	if(queryFilterRSA){
		queryFilterRSA_RMB = Integer.parseInt(request.getParameter("queryFilterRSA_RMB"));
        	queryFilterRSA_Version = request.getParameter("queryFilterRSA_Version");
	}
	
	String serverName = request.getParameter("serverName");
	String serverIp = getServerIp(serverName);
	String kick = request.getParameter("kick");
	if(kick == null) kick = "";
	String fenghao = request.getParameter("fenghao");
	if(fenghao == null) fenghao = "";
	String dajing = request.getParameter("dajing");
	if(dajing == null) dajing = "";
	
	ArrayList<String> checkUserNameList = new ArrayList<String>();
	String serverName_ipcheck[] = request.getParameterValues("serverName_ipcheck");
	if(serverName_ipcheck == null) serverName_ipcheck = new String[0];
	for(int i = 0 ; i < serverName_ipcheck.length ; i++){
		if(serverName_ipcheck[i] != null){
			String[] checkUsername = request.getParameterValues(serverName_ipcheck[i]+"_"+"checkUsername");
			for(int j = 0 ; checkUsername != null && j < checkUsername.length ; j++){
				checkUserNameList.add(checkUsername[j]);
			}
		}
	}

	args = "authorize.username=lvfei&authorize.password=lvfei321";
	
	
	if(serverIp != null && checkUserNameList.size() > 0){

		HashMap<String,IPUsernames> ipUsername = new HashMap<String,IPUsernames>();
		HashMap<String,ArrayList<String[]>> ipUsernameList = new HashMap<String,ArrayList<String[]>>();
		for(String str : checkUserNameList){
			String username = str.split("_@@@@@@")[0];
			String ip = str.split("_@@@@@@")[1];
			String ua = str.split("_@@@@@@")[2];
			if(ipUsername.get(ip) == null){
				IPUsernames ipU = new IPUsernames();
				ipU.ip = ip;
				ipUsername.put(ip,ipU);
			}
			ipUsername.get(ip).usernames.add(username);
			ipUsername.get(ip).ua.add(ua);
			
			if(ipUsernameList.get(ip) == null){
				ArrayList<String[]> al = new ArrayList<String[]>();
				ipUsernameList.put(ip,al);
			}
			ipUsernameList.get(ip).add(new String[]{username,ua});


			PlayerRecordData prd = getPlayerRecordData(serverInfoMap,serverName,username);
			if(prd !=  null){
				prd.lastModifyTime = System.currentTimeMillis();

				String jsonString = (String)cache.get(username);
        			ArrayList<PlayerRecordData> prdList = new ArrayList<PlayerRecordData>();
        			if(jsonString != null){
                			prdList = (ArrayList<PlayerRecordData>)JsonUtil.objectFromJson(jsonString,new com.fasterxml.jackson.core.type.TypeReference<ArrayList<PlayerRecordData>>(){});
       				 }
				prdList.add(prd);
				cache.put(username,JsonUtil.jsonFromObject(prdList));


				String today = DateUtil.formatDate(new Date(),"yyyy-MM-dd");	
				ArrayList<String> todayUserList = new ArrayList<String>();
				jsonString = (String)cache.get(today);
                                if(jsonString != null){
                                        todayUserList = (ArrayList<String>)JsonUtil.objectFromJson(jsonString,new com.fasterxml.jackson.core.type.TypeReference<ArrayList<String>>(){});
                                 }
				todayUserList.add(username);
				cache.put(today,JsonUtil.jsonFromObject(todayUserList));
			}
		}
		
		if(fenghao.equals("true")){
			String ssss = args + "&limit=true&usernames="+JsonUtil.jsonFromObject(ipUsername.values().toArray());
			byte[] b = HttpUtils.webPost(new URL(serverIp+"/admin/waigua/enterLimit.jsp"), ssss.getBytes(), new HashMap(), 60000, 60000);
			if(b != null && b.length > 0){
				String json = new String(b);
				out.println("<pre>"+json+"</pre>");
			}
		}
		
		if(kick.equals("true")){
			String ssss = args + "&limit=false&usernames="+JsonUtil.jsonFromObject(ipUsername.values().toArray());
			byte[] b = HttpUtils.webPost(new URL(serverIp+"/admin/waigua/enterLimit.jsp"), ssss.getBytes(), new HashMap(), 60000, 60000);
			if(b != null && b.length > 0){
				String json = new String(b);
				out.println("<pre>"+json+"</pre>");
			}
		}
		
		if(dajing.equals("true")){
			String ssss = args + "&action=add_studio&studio="+JsonUtil.jsonFromObject(ipUsernameList);
			out.println("<pre>"+ssss+"</pre>");
			byte[] b = HttpUtils.webPost(new URL(serverIp+"/admin/dajing_auto_add.jsp"), ssss.getBytes(), new HashMap(), 60000, 60000);
			if(b != null && b.length > 0){
				String json = new String(b);
				out.println("<pre>"+json+"</pre>");
			}
		}

		
	}
	showServerName = serverName;
	action = "showServer";
}



if(action.equals("showServer")){
	if(showServerName.length() == 0){
		showServerName = request.getParameter("showServerName");
	}
	if(showServerName == null) showServerName = "";

	args = "authorize.username=lvfei&authorize.password=lvfei321&enterLimit=true&maxSize="+lowSize+"&level="+lowLevel+"&online=false";
	
	String serverIp = getServerIp(showServerName);
	
	byte[] b = HttpUtils.webPost(new URL(serverIp+"/admin/waigua/enterLimit.jsp"), args.getBytes(), new HashMap(), 60000, 60000);
	if(b != null && b.length > 0){
		String json = new String(b);
		EnterLimitData[] eldMap = JsonUtil.objectFromJson(json,EnterLimitData[].class);//好像这里不能用泛型，如果无法用的话，就用其他结构吧
		
		serverInfoMap.put(showServerName,eldMap);
	}
	
	
}
int totalOnlineCount = 0;
Iterator<String> it = serverInfoMap.keySet().iterator();
HashSet<String> userNameUniqMap = new HashSet<String> ();
while(it.hasNext()){
	String serverName = it.next();
	EnterLimitData[] eldMap = serverInfoMap.get(serverName);
	for(int i = 0 ; i < eldMap.length ; i++){
		if(eldMap[i].playerIds.size() == 0 || eldMap[i].recordDatas.size() == 0) {
			continue;
		}
		int loopIndex = 0;
		for (Long playerId : eldMap[i].playerIds) {
			PlayerRecordData  data = eldMap[i].recordDatas.get(loopIndex++);
		if(data == null) continue;	
			Boolean b = data.online;
			String username = data.username;
			if(b){
				if(!userNameUniqMap.contains(serverName+username)){
					totalOnlineCount++;
					userNameUniqMap.add(serverName+username);
				}
				
			}
		}
	}
}
System.out.println("java逻辑完成");

try{
%>
<html><head>
<!--
  <link rel="stylesheet" type="text/css" href="table.css" />
  -->
<script type="text/javascript">
function checkIp(checkObject){
	var aa = document.getElementsByName(checkObject.value+"_checkUsername");
	if(checkObject.checked){
		for(var i = 0; i < aa.length; i++){
			aa[i].checked = true;
		}
	}else{
		for(var i = 0; i < aa.length; i++){
			aa[i].checked = false;
		}
	}

}
</script>

</HEAD>
<BODY style="font-size: 12px;">

<%
if(action.equals("login")){
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//登录，显示查询封号还是封外挂
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
%><center><br><br><h1><a href="./check_enterLimit.jsp?action=chaxun_deny&lowSize=<%=lowSize%>&lowLevel=<%=lowLevel%>"> 查询被封号的原因(供客服解决玩家问题)</a> | <a href="./check_enterLimit.jsp?action=&lowSize=<%=lowSize%>&lowLevel=<%=lowLevel%>">查封外挂（供运营人员使用）</a></h1></center><%

}else if(action.equals("chaxun_deny")){
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//查询封号的情况
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String action2 = request.getParameter("action2");

	ArrayList<PlayerRecordData> prdList = new ArrayList<PlayerRecordData>();

	String denyusername = request.getParameter("denyusername");
	String denyday = request.getParameter("denyday");

	if(action2 != null && action2.equals("username")){
		if(denyusername == null) denyusername = "";

		String jsonString = (String)cache.get(denyusername);

	
		if(jsonString != null){
			prdList = (ArrayList<PlayerRecordData>)JsonUtil.objectFromJson(jsonString,new com.fasterxml.jackson.core.type.TypeReference<ArrayList<PlayerRecordData>>(){});
		}
	}else{
		if(denyday == null || denyday.trim().length() == 0) denyday = DateUtil.formatDate(new Date(),"yyyy-MM-dd");

		String jsonString = (String)cache.get(denyday);
		ArrayList<String> userList = new ArrayList<String>();
		 if(jsonString != null){
                        userList = (ArrayList<String>)JsonUtil.objectFromJson(jsonString,new com.fasterxml.jackson.core.type.TypeReference<ArrayList<String>>(){});
                }
		ArrayList<String> userList2 = new ArrayList<String>();
		for(String username : userList){
			if(userList2.contains(username)== false) userList2.add(username);
		}
		for(String username : userList2){
			 jsonString = (String)cache.get(username);

			
               		 if(jsonString != null){
                       		ArrayList<PlayerRecordData> al = (ArrayList<PlayerRecordData>)JsonUtil.objectFromJson(jsonString,new com.fasterxml.jackson.core.type.TypeReference<ArrayList<PlayerRecordData>>(){});
				if(al.size() > 0)
				prdList.add(al.get(al.size()-1));
               		 }

		}
	}
%>
<h1>已经被封号的帐号数量为：<%=cache.getNumElements()%></h1>
<form action="./check_enterLimit.jsp">
<input type="hidden" name="action" value="chaxun_deny">
<input type="hidden" name="action2" value="username">
<input type="hidden" name="lowSize" value="<%=lowSize%>">
<input type="hidden" name="lowLevel" value="<%=lowLevel%>">
请输入要查询的玩家用户名(91，QQ，360SDK，UCSDK需要用用户伪码):<input type="text" name="denyusername" value="<%=denyusername%>"><input type="submit" value="查询"></form>

<form action="./check_enterLimit.jsp">
<input type="hidden" name="action" value="chaxun_deny">
<input type="hidden" name="action2" value="day">
<input type="hidden" name="lowSize" value="<%=lowSize%>">
<input type="hidden" name="lowLevel" value="<%=lowLevel%>">
请输入日期，格式yyyy-MM-dd:<input type="text" name="denyday" value="<%=denyday%>"><input type="submit" value="查询"></form>

<a href="./check_enterLimit.jsp?action=login&lowSize=<%=lowSize%>&lowLevel=<%=lowLevel%>">返回登录页</a><br>

<table border='1'>
<tr style="writing-mode: tb-rl;">
                                <td>编号</td>
                                <td>封号时间</td>
                                <td>服务器名</td>
                                <td>用户名</td>
                                <td>角色名</td>
                                <td>地图名</td>
                                <td>级别</td>
                                <td>任务级别</td>
                                <td>充值金额</td>
                                <td>在线</td>
                                <td>签名是否一致</td>
                                <td>是否模拟器</td>
                                <td>机型</td>
                                <td>渠道</td>
                                <td>版本</td>
                                <td width='20'>出售空格子数</td>
                                <td width='20'>发送带物品的邮件</td>
                                <td width='20'>合成绑定物品</td>
                                <td>体力</td>
                                <td width='20'>体力任务接取次数</td>
                                <td width='20'>喝酒</td>
                                <td width='20'>快售</td>
                                <td width='20'>求购次数</td>
                                <td>家族名字</td>
                                <td width='20'>坐骑数量</td>
                                <td width='20'>宠物数量</td>
                                <td width='20'>紫装</td>
                                <td width='20'>橙装</td>
                                <td>注册时间</td>
                </tr>
<%
	int dataIndex = 0;
	for(PlayerRecordData data : prdList){
		dataIndex++;
%><tr><td><%=dataIndex%></td>
                                                        <td><%= DateUtil.formatDate(new Date(data.lastModifyTime),"yyyy-MM-dd HH:mm:ss") %></td>
                                                        <td><%=data.serverName%></td>
                                                        <td><%=data.username %></td>
                                                        <td><%=data.playername %></td>
                                                        <td><%=data.gameName %></td>
                                                        <td style="color: #CE66EC;font-weight: bolder;"><%=data.level %></td>
                                                        <td style="color: #CE66EC;font-weight: bolder;"><%=data.mainTaskLevel %></td>
                                                        <td <%=data.rmb > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.rmb %></td>
                                                        <td><%=data.online %></td>
                                                        <td><% if(data.obj1!=null && data.obj1.toString().equals("false")){ out.println("<b>不一致</b>") ;}else{ out.println(data.obj1);} %></td>
                                                        <td><% if(data.obj2!=null && data.obj2.toString().equals("true")){ out.println("<b>模拟器</b>") ;}else{ out.println(data.obj2);} %></td>
                                                        <td><%=data.ua %></td>
                                                        <td><%=data.channel %></td>
                                                        <td><%=data.obj3%></td>
                                                        <td <%=data.sealEmptyCellTimes > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.sealEmptyCellTimes %></td>
                                                        <td><%=data.sendArticleMailTimes %></td>
                                                        <td><%=data.composeBindTimes %></td>
                                                        <td><%=data.currentTili %></td>
                                                        <td><%=data.tiliTaskAcceptTimes %></td>
                                                        <td><%=data.drinkTimes %></td>
                                                        <td><%=data.fastSellTimes %></td>
                                                        <td><%=data.requestBuyTimes %></td>
                                                        <td><%=data.jiazuName %></td>
                                                        <td ><%=data.maxHorseNum %></td>
                                                        <td ><%=data.maxPetNum %></td>
                                                        <td ><%=data.purpleEquipmentNum %></td>
                                                        <td ><%=data.orangeEquipmentNum %></td>
                                                        <td><%=data.registerTime %></td>

                                                </tr><%

	}//end for
%></table><%
}else{
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//封外挂的功能
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

%>
疑似外挂在线人数: <%= totalOnlineCount %> | <a href='./check_enterLimit.jsp?action=login&lowSize=<%=lowSize%>&lowLevel=<%=lowLevel%>'>返回登录页</a>
<form method="post" action="./check_enterLimit.jsp">	
<input type="hidden" name="action" value="query">
	输入同ip下最小用户数量：<input type="text" name="lowSize" value="<%=lowSize %>">
	输入同ip下最小用户等级：<input type="text" name="lowLevel" value="<%=lowLevel %>">
	<input type="checkbox" name="showIPAddress" value="true" <%= showIPAddress?"checked":"" %> >显示IP地址 | 
	<input type="checkbox" name="showOnlineOffline" value="true" <%= showOnlineOffline?"checked":""%> >显示所有(包括不在线)
	<input type="submit" value="提交">
</form>
<form method="post" action="./check_enterLimit.jsp">	
<input type="hidden" name="action" value="query_filterchannel"><input type="hidden" name="lowSize" value="<%=lowSize %>">
<input type="hidden" name="lowLevel" value="<%=lowLevel %>">
<input type="hidden" name="showOnlineOffline" value="<%= showOnlineOffline?"true":"false"%>" >
<input type="hidden" name="showIPAddress" value="<%= showIPAddress?"true":"false" %>" >
	直接封号便捷操作,输入精确渠道串：<input type="text" name="channel" value="<%= filterChannel %>">
	<input type="submit" value="提交">
</form>

<form method="post" action="./check_enterLimit.jsp">
<input type="hidden" name="action" value="query_filterrsa"><input type="hidden" name="lowSize" value="<%=lowSize %>">
<input type="hidden" name="lowLevel" value="<%=lowLevel %>">
<input type="hidden" name="showOnlineOffline" value="<%= showOnlineOffline?"true":"false"%>" >
<input type="hidden" name="showIPAddress" value="<%= showIPAddress?"true":"false" %>" >
        直接封号便捷操作,过滤出可能不正常的包(只能参考不能查封)： 充值超过<input type="text" name="queryFilterRSA_RMB" value="<%=queryFilterRSA_RMB%>">分的屏蔽，版本不包含字符串<input type="text" name="queryFilterRSA_Version" value="<%=queryFilterRSA_Version%>">的屏蔽 <input type="submit" value="提交">
</form>

<form method="post" action="./check_enterLimit.jsp">
	<input type="hidden" name="action" value="query_filterKongGeZhi"><input type="hidden" name="lowSize" value="<%=lowSize %>">
<input type="hidden" name="lowLevel" value="<%=lowLevel %>">
<input type="hidden" name="showOnlineOffline" value="<%= showOnlineOffline?"true":"false"%>" >
<input type="hidden" name="showIPAddress" value="<%= showIPAddress?"true":"false" %>" >
请输入空格子数量:<input type='text' name='konggezhinum' value='<%=konggezhinum%>'><input type='submit' value='提交'>	
</form>
<%


it = serverInfoMap.keySet().iterator();
int index = 0;
while(it.hasNext()){
	String serverName = it.next();
	EnterLimitData[] eldMap = serverInfoMap.get(serverName);
	
	String color = "#e1e1e1";
	if(serverNewFlag.containsKey(serverName)){
		color="#FF0000";
	}
	if(serverName.equals(showServerName) == false && queryFilterchannel == false && queryFilterKongGeZhi == false && queryFilterRSA == false){
		
		StringBuffer sb = new StringBuffer();
		int countIP = 0;
		for(int i = 0 ; i < eldMap.length ; i++){
			if(eldMap[i].playerIds.size() == 0 || eldMap[i].recordDatas.size() == 0) {
				continue;
			}
			int count = 0;
			int loopIndex = 0;
			for (Long playerId : eldMap[i].playerIds){
				PlayerRecordData data = eldMap[i].recordDatas.get(loopIndex++);
				if(data == null) continue;
				Boolean b = data.online;
				if( !showOnlineOffline ){
					if(b){
						count++;
					}
				}else{
					count++;
				}
			}
			if(count >= lowSize){
				sb.append(eldMap[i].ip+"("+count+"); ");
				countIP ++;
			}
		}
		if(countIP > 0){
		%><table border="1" width=100%>
		  <tr><td width='10%'>服务器</td><td bgcolor="<%=color %>">
		  <a href="./check_enterLimit.jsp?showOnlineOffline=<%=showOnlineOffline %>&showIPAddress=<%=showIPAddress%>&action=showServer&showServerName=<%=serverName%>&lowLevel=<%=lowLevel%>&lowSize=<%=lowSize%>#<%=serverName%>">服务器(<%=++index %>)：<%=serverName+"("+getServerIp(serverName)+")" %></a> 
		  | <a href="./check_enterLimit.jsp?showOnlineOffline=<%=showOnlineOffline %>&includeOffline=true&showIPAddress=<%=showIPAddress%>&action=showServer&showServerName=<%=serverName%>&lowLevel=<%=lowLevel%>&lowSize=<%=lowSize%>#<%=serverName%>">显示非在线的</a> 
		  | <a href='<%= getServerIp(serverName) %>' target='_blank'>去服务器</a> </td>
		  </tr>
		  <tr><td width='10%'>IP数量</td><td><%= countIP %></td></tr>
		  <tr><td width='10%'>信息</td><td><%= sb %></td></tr>
		  
		  </table>
	<% 
		}
	}else{ //展开服务器
		
		ArrayList<EnterLimitData> alreadyShowList = new ArrayList<EnterLimitData>();		
		int counts = 0;
		for(int kk = 0 ; kk < eldMap.length ; kk++){
			EnterLimitData eld = eldMap[kk];
			if(eldMap[kk].playerIds.size() == 0 || eldMap[kk].recordDatas.size() == 0) {
				continue;
			}
			int count = 0;
			int loopIndex = 0;
			for(Long playerId : eld.playerIds){
				PlayerRecordData data = eld.recordDatas.get(loopIndex++);
				if (data==null)continue;
				Boolean b =data.online;

				 if(queryFilterRSA){
                                        if ((data.obj1 == null && data.obj2 == null)||("null".equals(String.valueOf(data.obj1)) && "null".equals(String.valueOf(data.obj2)) || "false".equals(String.valueOf(data.obj1)) )   ){
                                          if(b && data.rmb <= queryFilterRSA_RMB && !data.obj3.toString().contains(queryFilterRSA_Version)){

                                                                count++;
                                          }
                                        }
                                }else if(queryFilterchannel){
					String channel = data.channel;
					if(channel.equalsIgnoreCase(filterChannel)){
						if(!showOnlineOffline && includeOffline == false){
							if(b){
								count++;
							}
						}else{
							count++;
						}
					}
				}else if(queryFilterKongGeZhi){
					if(data.sealEmptyCellTimes>=konggezhinum){
						 if(!showOnlineOffline && includeOffline == false){
                                                        if(b){
                                                                count++;
                                                        }
                                                }else{
                                                        count++;
                                                }
					}	
				}else{

					if(!showOnlineOffline && includeOffline == false){
						if(b){
							count++;
						}
					}else{
						count++;
					}
				}
			}
			counts += count;
		}
		if(counts == 0) continue;
	
		
	%>
	
	<form id='ffff' method="post" action='./check_enterLimit.jsp'>
	<input type="hidden" name="serverName" value="<%=serverName %>" >
	<input type="hidden" name="lowLevel" value="<%=lowLevel%>">
	<input type="hidden" name="lowSize" value="<%=lowSize%>">
	<input type="hidden" name="action" value="fenghao">
	<input type="hidden" name="showIPAddress" value="<%=showIPAddress%>" >
	<input type="hidden" name="includeOffline" value="<%=includeOffline%>" >
	<input type="hidden" name="showOnlineOffline" value="<%=showOnlineOffline%>" >
	<% 
		if(queryFilterchannel){
			%><input type="hidden" name="queryFilterchannel" value="<%=queryFilterchannel%>" >
			<input type="hidden" name="channel" value="<%=filterChannel%>" ><% 
		}
		if(queryFilterRSA){
                        %>
				<input type="hidden" name="queryFilterRSA" value="<%=queryFilterRSA%>" >
				<input type="hidden" name="queryFilterRSA_RMB" value="<%=queryFilterRSA_RMB%>" >
				<input type="hidden" name="queryFilterRSA_Version" value="<%=queryFilterRSA_Version%>" >

			 <%
                }
		if(queryFilterKongGeZhi){
		%><input type="hidden" name="queryFilterKongGeZhi" value="<%=queryFilterKongGeZhi%>"><input type="hidden" name="konggezhinum" value="<%=konggezhinum%>"><%
		}
	%>
	<table border="1">
	<tr><td colspan="20" bgcolor="<%=color %>"><a name='<%=serverName%>'></a>服务器(<%=++index %>)：<%=serverName +"("+getServerIp(serverName)+")" %> | <a href="./check_enterLimit.jsp?showOnlineOffline=<%=showOnlineOffline %>&includeOffline=true&showIPAddress=<%=showIPAddress%>&action=showServer&showServerName=<%=serverName%>&lowLevel=<%=lowLevel%>&lowSize=<%=lowSize%>#<%=serverName%>">显示非在线的</a> | <a href='<%= getServerIp(serverName) %>' target='_blank'>去服务器</a>	
	
	<input type='checkbox' name='dajing' value="true"  <%=(queryFilterchannel||queryFilterKongGeZhi||queryFilterRSA)?"":"checked='true'" %> >加入打金工作室  | 
	<input type='checkbox' name='fenghao' value="true" <%=(queryFilterchannel||queryFilterKongGeZhi||queryFilterRSA)?"checked='true'":"" %> >封号| <input type='checkbox' name='kick' value="true">踢下线
	<input type='submit' value='单服提交'>

	</td></tr>
		<tr style="writing-mode: tb-rl;">
			<td>IP</td>
				<td>用户名</td>
				<td>角色名</td>
				<td>地图名</td>
				<td>级别</td>
				<td>任务级别</td>
				<td>充值金额</td>
				<td>在线</td>
				<td>签名是否一致</td>
				<td>是否模拟器</td>
				<td>机型</td>
				<td>渠道</td>
				<td>版本</td>
				<td width='20'>出售空格子数</td>
				<td width='20'>发送带物品的邮件</td>
				<td width='20'>合成绑定物品</td>
				<td>体力</td>
				<td width='20'>体力任务接取次数</td>
				<td width='20'>喝酒</td>
				<td width='20'>快售</td>
				<td width='20'>求购次数</td>
				<td>家族名字</td>
				<td width='20'>坐骑数量</td>
				<td width='20'>宠物数量</td>
				<td width='20'>紫装</td>
				<td width='20'>橙装</td>
				<td>注册时间</td>
		</tr>
		<%
		int ipIndex = 0;
		for(int kk = 0 ; kk < eldMap.length ; kk++){
			EnterLimitData eld = eldMap[kk];
			if(eldMap[kk].playerIds.size() == 0 || eldMap[kk].recordDatas.size() == 0) {
				continue;
			}
			if(eld != null && eld.playerIds != null){
				int printLineCount = 0;
				int count = 0;
				int loopIndex = 0;
				for (Long playerId : eld.playerIds) {
					PlayerRecordData data = eld.recordDatas.get(loopIndex++);
					if (data == null) continue;
					Boolean b = data.online;
				
					
                                 if(queryFilterRSA){
                                        if ((data.obj1 == null && data.obj2 == null)||("null".equals(String.valueOf(data.obj1)) && "null".equals(String.valueOf(data.obj2)) || "false".equals(String.valueOf(data.obj1)) )   ){
						if(b && data.rmb <= queryFilterRSA_RMB && !data.obj3.toString().contains(queryFilterRSA_Version)){
                                                                count++;
                                                        }
                                        }
                                }else	if(queryFilterchannel){
						String channel = data.channel;
						if(channel.equalsIgnoreCase(filterChannel)){
							if(!showOnlineOffline && includeOffline == false){
								if(b){
									count++;
								}
							}else{
								count++;
							}
						}
					}else if(queryFilterKongGeZhi){
                                	        if(data.sealEmptyCellTimes>=konggezhinum){
                                        	         if(!showOnlineOffline && includeOffline == false){
                                                	        if(b){
                                                        	        count++;
                                                 	       }
                                               		 }else{
                                                       		 count++;
                                               		 }
                                       		 }
					}else{
						if(!showOnlineOffline && includeOffline == false){
							if(b){
								count++;
							}
						}else{
							count++;
						}
					}
				}
				if(count < lowSize) continue;

				
		                if(alreadyShowList.contains(eld)){
                	       		 continue;
                		}
                       		alreadyShowList.add(eld);

				ipIndex++;
				loopIndex = 0;
				for (Long playerId : eld.playerIds) {
					PlayerRecordData data = eld.recordDatas.get(loopIndex++);
					if (data == null) continue; 
					Boolean b = data.online;
					if(!showOnlineOffline && b == false && includeOffline == false) continue;
					String channel = data.channel;
					if(queryFilterchannel && channel.equalsIgnoreCase(filterChannel) == false) continue;

					if(queryFilterRSA && (data.rmb > queryFilterRSA_RMB || data.obj3.toString().contains(queryFilterRSA_Version)  ||  b ==false || ("true".equals(String.valueOf(data.obj1)) && "false".equals(String.valueOf(data.obj2))))) continue;

					if(queryFilterKongGeZhi && data.sealEmptyCellTimes < konggezhinum) continue;	
					if(printLineCount==0){
						%><tr bgcolor="<%=ipIndex%2==0?"#A4A4A4":""%>">
										
							<td title="<%=serverName + "("+index+")" %>" rowspan="<%= count %>"><%=eld.ip + "["+ getIPInfo(eld.ip,showIPAddress)+"]" %>
							<input type="checkbox" name="serverName_ipcheck" value="<%=(serverName+"_"+eld.ip) %>" onclick="checkIp(this);" <%= (queryFilterchannel||queryFilterKongGeZhi||queryFilterRSA)?"checked":"" %> > <BR/>封现在这个ip下的用户 (<%= count %>)</td>
							<td><input type="checkbox" name="<%=(serverName+"_"+eld.ip) %>_checkUsername" value="<%=data.username+"_@@@@@@"+eld.ip + "_@@@@@@" + data.ua %>" <%= (queryFilterchannel||queryFilterKongGeZhi||queryFilterRSA)?"checked":"" %>><%=data.username %></td>
							<td><%=data.playername %></td>
							<td><%=data.gameName %></td>
							<td style="color: #CE66EC;font-weight: bolder;"><%=data.level %></td>
							<td style="color: #CE66EC;font-weight: bolder;"><%=data.mainTaskLevel %></td>
							<td <%=data.rmb > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.rmb %></td>
							<td><%=data.online %></td>
							<td><% if(data.obj1!=null && data.obj1.toString().equals("false")){ out.println("<b>不一致</b>") ;}else{ out.println(data.obj1);} %></td>
							<td><% if(data.obj2!=null && data.obj2.toString().equals("true")){ out.println("<b>模拟器</b>") ;}else{ out.println(data.obj2);} %></td>
							<td><%=data.ua %></td>
							<td><%=data.channel %></td>
							<td><%=data.obj3%></td>
							<td <%=data.sealEmptyCellTimes > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.sealEmptyCellTimes %></td>
							<td><%=data.sendArticleMailTimes %></td>
							<td><%=data.composeBindTimes %></td>
							<td><%=data.currentTili %></td>
							<td><%=data.tiliTaskAcceptTimes %></td>
							<td><%=data.drinkTimes %></td>
							<td><%=data.fastSellTimes %></td>
							<td><%=data.requestBuyTimes %></td>
							<td><%=data.jiazuName %></td>
							<td ><%=data.maxHorseNum %></td>
							<td ><%=data.maxPetNum %></td>
							<td ><%=data.purpleEquipmentNum %></td>
							<td ><%=data.orangeEquipmentNum %></td>
							<td><%=data.registerTime %></td>
							
						</tr>
						<%
					}else{
						%>
						<tr bgcolor="<%=ipIndex%2==0?"#A4A4A4":""%>">
							<td><input type="checkbox" name="<%=(serverName+"_"+eld.ip) %>_checkUsername" value="<%=data.username +"_@@@@@@"+eld.ip + "_@@@@@@" + data.ua%>" <%= (queryFilterchannel||queryFilterKongGeZhi||queryFilterRSA)?"checked":"" %> ><%=data.username %></td>
							<td><%=data.playername %></td>
							<td><%=data.gameName %></td>
							<td style="color: #CE66EC;font-weight: bolder;"><%=data.level %></td>
							<td style="color: #CE66EC;font-weight: bolder;"><%=data.mainTaskLevel %></td>
							<td <%=data.rmb > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.rmb %></td>
							<td><%=data.online %></td>
							<td><% if(data.obj1!=null && data.obj1.toString().equals("false")){ out.println("<b>不一致</b>") ;}else{ out.println(data.obj1);} %></td>
                                                        <td><% if(data.obj2!=null && data.obj2.toString().equals("true")){ out.println("<b>模拟器</b>") ;}else{ out.println(data.obj2);} %></td>
							<td ><%=data.ua %></td>
							<td ><%=data.channel %></td>
							<td ><%=data.obj3 %></td>
							<td <%=data.sealEmptyCellTimes > 0 ? "style='color: red;font-weight: bold;'" :"" %>><%=data.sealEmptyCellTimes %></td>
							<td><%=data.sendArticleMailTimes %></td>
							<td><%=data.composeBindTimes %></td>
							<td><%=data.currentTili %></td>
							<td><%=data.tiliTaskAcceptTimes %></td>
							<td><%=data.drinkTimes %></td>
							<td><%=data.fastSellTimes %></td>
							<td><%=data.requestBuyTimes %></td>
							<td><%=data.jiazuName %></td>
							<td ><%=data.maxHorseNum %></td>
							<td ><%=data.maxPetNum %></td>
							<td ><%=data.purpleEquipmentNum %></td>
							<td ><%=data.orangeEquipmentNum %></td>
							<td><%=data.registerTime %></td>
						</tr>
						<%
					}
					printLineCount++;
				}
				%>
				
				<%
			}
		}
		%>
	</table>
	</form>
	<%
	}// equals to showServerName
}

out.println("<h2>获取数据出现异常服务器</h2>");
Iterator<String> it2 = serverExpMap.keySet().iterator();

while(it2.hasNext()){
	String serverName = it2.next();
	Exception exp= serverExpMap.get(serverName);
	String ssss = StringUtil.getStackTrace(exp);
	%>
	<table border=1>
	<tr><td><%= serverName %></td></tr>
	<tr><td><pre><%= ssss %></pre></td></tr>
	</table>
	<% 
}


}// end of if(action.equals(".....");


}catch(Exception e){
	e.printStackTrace(System.out);
}
%>
<%}else{
	out.print("<h1>请先稍等使用，测试下该页面是否对服务器性能有影响</h1>");
} %>
</BODY></html>


