<%@page contentType="text/html;charset=gb2312"%><%@page import="java.lang.management.*"%><%!
	static{
	String getThreadGroupInfo(String uri,ThreadGroup group, boolean bDump, long lThreadID)
		StringBuffer rc = new StringBuffer();
			if (bDump || threads[i].getId() == lThreadID)
				rc.append("BlockedTime:"+oInfo.getBlockedTime() + ", BlockedCount:" + oInfo.getBlockedCount() + ", WaitedTime:" + oInfo.getWaitedTime() + ", WaitedCount:" + oInfo.getWaitedCount()).append("<BR>");
		ThreadGroup groups[] = new ThreadGroup[group.activeGroupCount()];
		return rc.toString();
	
	public String showThreads(String uri,boolean bDump, long lThreadID)
		// I'm not sure why what gets reported is off by +1, 
%><%
	      }
	String strDump = request.getParameter("dump_info");
	long lThreadID = -1;
	out.print(showThreads(request.getRequestURI()+"?s=&n=" ,bDump, lThreadID));   
%>
<script>
function suspendit(n)
function resumeit(n)
function interruptit(n)
