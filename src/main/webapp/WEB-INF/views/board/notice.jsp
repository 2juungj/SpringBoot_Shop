<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:forEach var="board" items="${boards.content}">
		<a href="/board/${board.id}" class="card-link">
			<div class="card m-2">
				<div class="card-body">
					<h4 class="card-title text-center">${board.title}</h4>
				</div>
			</div>
		</a>
	</c:forEach>

	<ul class="pagination justify-content-center">
		<!-- start, center, end -->
		<c:choose>
			<c:when test="${boards.first }">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${boards.last }">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>

	<sec:authorize access="hasRole('ADMIN') or hasRole('SELLER')">
		<div style="text-align: center;">
			<a class="btn-default" href="/seller/boardSaveForm">공지작성</a>
		</div>
	</sec:authorize>

</div>



<%@ include file="../layout/footer.jsp"%>



