<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}/profile" var="profileMvcUrl" />
<c:set value="${pageContext.request.contextPath}/rest/profile" var="profileRestUrl" />
<c:set value="${pageContext.request.contextPath}/resources" var="resourcesUrl" />
<html>
<head>
<script src="${resourcesUrl}/jquery-1.11.1.min.js"></script>
</head>
<body>
<input type="hidden" id="profileMvcUrl" value="${profileMvcUrl}" />
<input type="hidden" id="profileRestUrl" value="${profileRestUrl}" />

  <script>
  $(document).ready(function() {
	  	  
	  return false; // keeps the page from not refreshing 
	    
  
  });
  </script>

	<h3>${message}</h3>	
	
<div id="inline_content">
<form method="POST" action="${profileMvcUrl}/signup">		
	Email: <input type="text" name="email">
	<br/>
	Password: <input type="password" name="passwd">
	<br/>
	Verify: <input type="password" name="passwd2">
	<br /><br />
	<input type="submit" value="Signup">
</form>
</div>  
<br/><br/>
</body>
</html>