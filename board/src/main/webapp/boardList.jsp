<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<body>
	<table border="1" style="width:600px">
		<caption>게시판</caption>
		<colgroup>
			<col width='8%' />
			<col width='*%' />
			<col width='15%' />
			<col width='15%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>제목</th>
				<th>등록자</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
<%
	Class.forName("oracle.jdbc.driver.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe/board";
	String id = "whdytpq2"; 
	String pw = "1234";
	Connection conn=DriverManager.getConnection(url,id,pw);
	Statement stmt=conn.createStatement();
	
	String query="SELECT BRDNO, BRDTITLE, BRDWRITER, TO_CHAR(BRDDATE,'yyyy-mm-dd') BRDDATE " + 
				 "  FROM TBL_BOARD";
	ResultSet rs = stmt.executeQuery(query);
	
	while(rs.next()){
%>		
				<tr>
					<td><%=rs.getString("brdno")%></td>
					<td><a href="board1Read?brdno=<%=rs.getString("brdno")%>"><%=rs.getString("brdtitle")%></a></td>
					<td><%=rs.getString("brdwriter")%></td>
					<td><%=rs.getString("brddate")%></td>
				</tr>
<%
	}//	while(rs.next()){
	stmt.close();
	conn.close();
%>		
		</tbody>
	</table>    
</body>
</html>
