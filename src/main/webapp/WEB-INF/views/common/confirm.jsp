<script>
	<c:if test="${not empty sessionScope.msg}">
		alert("${msg}");
	</c:if>
</script>