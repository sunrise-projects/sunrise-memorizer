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
	  $("#checkAnswer").click(function(){
		  
		  $("#showStatus").text("");
		  
		  var questionRestUrl = $('input#questionRestUrl').val();		  
		  var sessionId = $('input#sessionId').val();
		  var number = $('input#number').val();
		  var answer = $('input:radio[name=answer]:checked').val();		  
		var url = questionRestUrl+"/check/"+sessionId+"/"+number+"/"+answer;	    
	    $.ajax({ // ajax call starts
	          url: url, // JQuery loads serverside.php
	          dataType: 'json', // Choosing a JSON datatype
	          success: function(data) // Variable data contains the data we get from serverside
	          {
	        	  $("#showStatus").text(data.status);
	          }
	      });
	      return false; // keeps the page from not refreshing 
	    
	  });	  
  });
  </script>

	<form method="get" action="${questionMvcUrl}/next/${question.sessionId}">
	<input type="hidden" id="sessionId" name="sessionId" value="${question.sessionId}">
	<input type="hidden" id="number" name="number" value="${question.number}">	
	<h4>Message : ${message}</h4>
	<h4>QuestionCode# : ${question.number} 
	totalQuestionRunningValue# : ${question.totalQuestionRunningValue} 
	totalQuestion# : ${question.totalQuestion}
	numberOfSetsDone# : ${question.numberOfSetsDone} 
	questionSetRunningValue# : ${question.questionSetRunningValue}
	questionSetTotalValue# : ${question.questionSetTotalValue} </h4>
	
	<h4>Question : ${question.question}</h4>
	<c:forEach var="entry" items="${question.selection}">
  		<br/><input type="radio" name="answer" value="<c:out value="${entry.key}" />"><c:out value="${entry.value}" />  		
	</c:forEach>
	<br/>
	<br/>
	<h3 id="showStatus"></h3>
	<input type="button" value="Check Answer" id="checkAnswer">	
	<input type="submit" value="Next Question" id="nextQuestion">
	</form>
	
	
</body>
</html>