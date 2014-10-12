<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}/quiz" var="quizMvcUrl" />
<c:set value="${pageContext.request.contextPath}/rest/quiz" var="quizRestUrl" />
<c:set value="${pageContext.request.contextPath}/resources" var="resourcesUrl" />
<html>
<head>
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<script src="${resourcesUrl}/jquery-1.11.1.min.js"></script>
</head>
<body>
<input type="hidden" id="quizRestUrl" value="${quizRestUrl}" />
<input type="hidden" id="quizMvcUrl" value="${quizMvcUrl}" />
<input type="hidden" id="uniqueid" value="${uniqueid}" /> 
  <script>
  $(document).ready(function() {

	  
  });
  </script>
<h3>Results</h3>
<br/>
<b>
Summary
</b>
<br/>
totalCorrect: ${totalCorrect}
<br/>
totalCount: ${totalCount}
<br/>
average: ${average}
<br/>
<div id="inline_content">
<table>
<c:forEach items="${questionlist}" var="element" varStatus="status"> 
  <tr>
    <td><b>${status.count}. ${element.question}</b>
	<br/>
	<c:forEach var="entry" items="${element.selections}">
		<c:choose>
		    <c:when test="${entry.key eq element.answer}">
		       <b><br/>${entry.key}. <c:out value="${entry.value}" /></b>
		    </c:when>
		    <c:otherwise>
		        <br/>${entry.key}. <c:out value="${entry.value}" />
		    </c:otherwise>
		</c:choose>		
	</c:forEach>
	<br/><br/>
	Answer: ${element.answer} 
	<br/>
	Your Answer: ${element.testerAnswer}
	<br/>
	Correct: ${element.testerCorrect}
	<br/>
	Explanation: ${element.explanation}
	<br/>
  </tr>
  <tr><td>&nbsp;</td></tr>
</c:forEach>
</table>
<br/><br/>
<b>
Summary
</b>
<br/>
totalCorrect: ${totalCorrect}
<br/>
totalCount: ${totalCount}
<br/>
average: ${average}
<br/>
<br/>	
</div>
</body>
</html>