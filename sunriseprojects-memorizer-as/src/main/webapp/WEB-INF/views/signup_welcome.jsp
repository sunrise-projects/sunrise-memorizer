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

	<h3>${message} ${profile.email}</h3>
	<h3>${profile.statusCode} ${profile.statusText}</h3>		
	
</body>
</html>