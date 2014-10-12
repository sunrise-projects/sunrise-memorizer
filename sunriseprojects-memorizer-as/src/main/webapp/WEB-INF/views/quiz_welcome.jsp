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

  <script>
  $(document).ready(function() {
	  
	  var quizMvcUrl = $('input#quizMvcUrl').val();
	  
	  
	  $("#inline_content input[name='type']").click(function(){
		   	    
		    var answer = $('input:radio[name=type]:checked').val();

			  var url = quizMvcUrl+"/start/"+answer;
			  window.location = url;		    
		});	  
	  
	  return false; // keeps the page from not refreshing 
	    
  
  });
  </script>

	<h3>Message : ${message}</h3>	
	
<div id="inline_content">
    <form class="type">

<c:forEach var="entry" items="${fileList}">
	<br/><input type="radio" name="type" value="<c:out value="${entry.key}" />">
	<c:out value="${entry.value}" />  		
</c:forEach>

    </form>
</div>    
<br/><br/>
<div id="inline_content">
	<form method="POST" enctype="multipart/form-data"
		action="${quizMvcUrl}/upload">
		File to upload: <input type="file" name="file"><br /> Name: <input
			type="text" name="name"><br /> <br /> <input type="submit"
			value="Upload">
	</form>
</div>  
<br/><br/>
Templates: 
<br/>
<a href="/resources/memorize-this.xlsx">memorize-this.xlsx</a>	
<br/>
<a href="/resources/sample.xlsx">sample.xlsx</a>	
</body>
</html>