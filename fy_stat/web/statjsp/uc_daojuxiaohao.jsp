<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc.jsp"%>
<%@ page
	import="com.xuanzhi.tools.cache.diskcache.concrete.DefaultDiskCache,org.slf4j.*,java.io.*,java.util.*,
	com.xuanzhi.tools.text.*,com.sqage.stat.commonstat.entity.ChongZhi,com.xuanzhi.tools.text.*,
	com.sqage.stat.commonstat.dao.UserDao,com.sqage.stat.commonstat.entity.User,com.sqage.stat.commonstat.manager.UserManager,
    com.sqage.stat.commonstat.manager.Impl.*"
	%>
	<%!
	Object lock = new Object(){};
      %>
  <%
	String startDay = request.getParameter("day");
	String endDay = request.getParameter("endDay");
	String huoBiType=request.getParameter("huoBiType");
	String fenqu=request.getParameter("fenqu");
	String wupintype=request.getParameter("wupintype");
	String gamelevel = request.getParameter("gamelevel");
	String username = request.getParameter("username");
	
	
	if("0".equals(huoBiType)){huoBiType=null;}
	if(fenqu==null||"-1".equals(fenqu)){fenqu="龙吟虎啸','南蛮入侵','万箭齐发";}
	if("0".equals(wupintype)){wupintype=null;}
	if(gamelevel == null) gamelevel = "全部";
	if(username == null) username = "全部";
	
	String today = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
	if(startDay == null) startDay = today;
	if(endDay == null) endDay = today;
	/**
	*获得渠道信息
	**/
              //UserManagerImpl userManager=UserManagerImpl.getInstance();
              DaoJuManagerImpl daoJuManager=DaoJuManagerImpl.getInstance();
              ArrayList<String> huoBiTypeList=daoJuManager.getHuoBiType(null);
              ArrayList<String> daojuList=daoJuManager.getDaoju(null);
              //ArrayList<String []> fenQuList= userManager.getFenQu(null);//获得现有的分区信息
             
              String gamelevel_search=null;
              String username_search=null;
              if(!"全部".equals(username)){username_search=username;}
              if(!"全部".equals(gamelevel)){gamelevel_search=gamelevel;}
		      List goumaiList=daoJuManager.getDaoJuGouMai(startDay,endDay,fenqu,huoBiType,gamelevel_search,wupintype,"LOST",username_search);
%>
		
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" />
		<link rel="stylesheet" href="../css/atalk.css" />
		<script language="javascript" type="text/javascript" src="../js/flotr/lib/prototype-1.6.0.2.js"></script>
		<script language="javascript" type="text/javascript" src="../js/flotr/flotr-0.2.0-alpha.js"></script>
<script language="JavaScript">
     function downloads(){
     $('form1').action="daojuxiaohao_download.jsp";
	 $('form1').submit();
      }
</script>
	</head>
	<body>
		<center>
		<h2 style="font-weight:bold;">
			道具消耗
		</h2>
		<form name="form1" id="form1" method="post" >开始日期：<input type='text' name='day' value='<%=startDay %>'>
		-- 结束日期：<input type='text' name='endDay' value='<%=endDay %>'>(格式：2012-02-09)
		<br/><br/>
		       货币类型：<select name="huoBiType">
                       <option  value="0">全部</option>
                       <% 
                       for(int i = 0 ; i < huoBiTypeList.size() ; i++){
                       String _huoBiType = huoBiTypeList.get(i);
                       %>
                        <option value="<%=_huoBiType %>" 
                        <%
                        if(_huoBiType.equals(huoBiType)){
                        out.print(" selected=\"selected\"");
                        }
                         %>
                          ><%=_huoBiType %></option>
                       <%
                       }
                       %>
                </select> &nbsp;&nbsp;
                                        
                                   分区：<select name="fenqu">
                       <option  value="-1">全部</option>
                    
                       <option value="龙吟虎啸" 
                        <%
                        if("龙吟虎啸".equals(fenqu)){ out.print(" selected=\"selected\""); }
                         %>
                          >龙吟虎啸</option>
                          
                       <option value="南蛮入侵" 
                        <%
                        if("南蛮入侵".equals(fenqu)){ out.print(" selected=\"selected\""); }
                         %>
                          >南蛮入侵</option>
                          
                       <option value="万箭齐发" 
                        <%
                        if("万箭齐发".equals(fenqu)){ out.print(" selected=\"selected\""); }
                         %>
                          >万箭齐发</option>
                   </select>
                
                                            物品类型：<select name="wupintype">
                       <option  value="0">全部</option>
                       <% 
                       for(int i = 0 ; i < daojuList.size() ; i++){
                       String _wupintype = daojuList.get(i);
                       %>
                        <option value="<%=_wupintype %>" 
                        <%
                        if(_wupintype.equals(wupintype)){
                        out.print(" selected=\"selected\"");
                        }
                         %>
                          ><%=_wupintype %></option>
                       <%
                       }
                       %>
                </select>&nbsp;&nbsp;
                
                                                         等级：<input type='text' name='gamelevel' value='<%=gamelevel %>'>&nbsp;&nbsp;
                                                玩家账号：<input type='text' name='username' value='<%=username %>'>&nbsp;&nbsp;
		    		<input type='submit' value='提交'>
		    		</form>
		    		
		    		<h3>道具消耗</h3>
		    		<%
		    		out.println("<table id='test2' align='center' width='90%' cellpadding='1' bgcolor='#000000' cellspacing='1' border='0'>");
					out.print("<tr bgcolor='#EEEEBB'><td>物品名称</td><td>物品单价(元宝)</td><td>消耗数量</td><td>总价格(元宝)</td><td>占比</td></tr>");
					Long moneysum=0L;
					Long count=0L;
					for(int i = 0 ; i < goumaiList.size() ; i++){
					
					String [] goumai=(String[])goumaiList.get(i);
					moneysum+=Long.parseLong(goumai[3]);
					count+=Long.parseLong(goumai[2]);
					}
					for(int i = 0 ; i < goumaiList.size() ; i++){
					String [] goumai=(String[])goumaiList.get(i);
					Long zhanbi=0L;
					if(count!=0){zhanbi=(Long.parseLong(goumai[2])*100)/count;}
						out.print("<tr bgcolor='#FFFFFF'><td>"+goumai[0]+"</td><td>"+goumai[1]+"</td><td>"+goumai[2]+"</td><td>"+goumai[3]+"</td><td>"+zhanbi+"% &nbsp;</td>");
						out.println("</tr>");
					}
					out.print("<tr bgcolor='#EEEEBB'><td>总计</td><td>&nbsp;</td><td>"+count+"</td><td>"+moneysum+"</td><td>&nbsp;</td></tr>");
					out.println("</table><br>");
		    		%>
		    		<div id="regUserContainer" style="width:100%;height:400px;display:block;"></div>
		</center>
		<br>
		<i>这里的用户都是指进入游戏的用户</i>
		<br>
	</body>
</html>