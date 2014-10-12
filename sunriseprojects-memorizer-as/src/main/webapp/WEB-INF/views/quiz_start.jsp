<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}/quiz" var="quizMvcUrl" />
<c:set value="${pageContext.request.contextPath}/rest/quiz" var="quizRestUrl" />
<c:set value="${pageContext.request.contextPath}/resources" var="resourcesUrl" />
<html>
<head>
<script src="${resourcesUrl}/jquery-1.11.1.min.js"></script>
</head>
<body>
<input type="hidden" id="quizRestUrl" value="${quizRestUrl}" />
<input type="hidden" id="quizMvcUrl" value="${quizMvcUrl}" />
<input type="hidden" id="uniqueid" value="${uniqueid}" /> 
  <script>
  $(document).ready(function() {

	  var quizRestUrl = $('input#quizRestUrl').val();
	  
	  $('#inline_content input:radio').click(function() { 
		    var ans = $('input#uniqueid').val()+'/'+$(this).attr('name')+'/'+$(this).val();
			var url = quizRestUrl+"/answer/"+ans;	    
		    $.ajax({ // ajax call starts
		          url: url, // JQuery loads serverside.php
		          dataType: 'json', // Choosing a JSON datatype
		          success: function(data) // Variable data contains the data we get from serverside
		          {
		        	  //alert(data);
		          }
		      });		  
	  });
	  
  });
  </script>
<h3>Questions</h3>
<div id="inline_content">
<table>
<c:forEach items="${questionlist}" var="element" varStatus="status"> 
  <tr>
    <td><b>${status.count}. ${element.question}</b>
	<br/>
	<c:forEach var="entry" items="${element.selections}">
  		<br/><input type="radio" name="${element.questionNumber}" value="<c:out value="${entry.key}" />"><c:out value="${entry.value}" />  		
	</c:forEach>
	<br/>
  </tr>
  <tr><td>&nbsp;</td></tr>
</c:forEach>
</table>
<br/>	
</div>
<a href="${quizMvcUrl}/results/${uniqueid}" target="_blank">Results</a>
</body>
</html>