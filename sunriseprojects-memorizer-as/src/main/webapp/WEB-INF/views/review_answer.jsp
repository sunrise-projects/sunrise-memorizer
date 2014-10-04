<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}/question" var="questionMvcUrl" />
<c:set value="${pageContext.request.contextPath}/rest/question" var="questionRestUrl" />
<c:set value="${pageContext.request.contextPath}/resources" var="resourcesUrl" />
<html>
<head>
<script src="${resourcesUrl}/jquery-1.11.1.min.js"></script>
</head>
<body>
<input type="hidden" id="questionRestUrl" value="${questionRestUrl}" />
<input type="hidden" id="questionMvcUrl" value="${questionMvcUrl}" /> 
  <script>
  $(document).ready(function() {
	  
	  <!-- $("input[type=submit]").attr("disabled", "disabled"); -->
	  
	  $("input[type=submit]").removeAttr("disabled");  
	  
	  var questionMvcUrl = $('input#questionMvcUrl').val();	
	  
	  $("#reviewAnswers").click(function() {
		  var sessionId = $('input#sessionId').val();
		  var url = questionMvcUrl+"/review/"+sessionId;
		  window.location = url;
		});

	  return false; // keeps the page from not refreshing 
  
  });
  </script>

	<form method="get" action="${questionMvcUrl}/next/${question.sessionId}">
	<input type="hidden" id="sessionId" name="sessionId" value="${question.sessionId}">
	<input type="hidden" id="number" name="number" value="${question.number}">	
	<h1>Message : ${message}</h1>
	<h4>QuestionCode# : ${question.number} 
	totalQuestionRunningValue# : ${question.totalQuestionRunningValue} 
	totalQuestion# : ${question.totalQuestion}
	numberOfSetsDone# : ${question.numberOfSetsDone} 
	questionSetRunningValue# : ${question.questionSetRunningValue}
	questionSetTotalValue# : ${question.questionSetTotalValue} </h4>
	
	<c:if test="${empty question.question}">
		<h4>Status : ${question.status}</h4>
	    <br/>
		<input type="submit" value="Next Question" id="nextQuestion">	
	</c:if>
	
	<c:if test="${not empty question.question}">
		<h4>Question : ${question.question}</h4>
		<c:forEach var="entry" items="${question.selection}">
	  		<br/><c:out value="${entry.key}" />=<c:out value="${entry.value}" />  		
		</c:forEach>
		<br/>
		<h4>Answer : ${question.answer}</h4>
		<h4>Explanation : ${question.explanation}</h4>
		<br/>
		<h3 id="showStatus"></h3>
		<input type="button" value="Review Answers" id="reviewAnswers">	
	</c:if>
	</form>

</body>
</html>