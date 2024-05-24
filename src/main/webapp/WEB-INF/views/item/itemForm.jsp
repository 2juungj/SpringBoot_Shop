<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>




<br>


<!-- Section-->
<section class="py-5">
	<div class="container px-4 px-lg-5 mt-5">
		<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
			<c:forEach var="item" items="${items.content}">
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Product image-->
						<div class="image-container" style="width: 100%; padding-top: 100%; position: relative;">
                            <img class="card-img-top" src="/image/upload/${fn:substringAfter(item.itemImage, 'C:\\fakepath\\')}" 
                                 style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;" />
                        </div>
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">${item.itemName}</h5>
								<!-- Product price-->
								${item.price}원
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center">
								<a class="btn btn-outline-dark mt-auto" href="/item/${item.id}">상세보기</a>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>


<ul class="pagination justify-content-center">
    <c:if test="${items.totalPages > 1}">
        <!-- Previous page button -->
        <c:choose>
            <c:when test="${items.first}">
                <li class="page-item disabled"><span class="page-link">Previous</span></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${items.number - 1}">Previous</a></li>
            </c:otherwise>
        </c:choose>
        
        <!-- Page numbers -->
        <c:forEach var="pageNumber" begin="0" end="${items.totalPages - 1}">
            <c:choose>
                <c:when test="${pageNumber eq items.number}">
                    <li class="page-item active" aria-current="page"><span class="page-link">${pageNumber + 1}</span></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="?page=${pageNumber}">${pageNumber + 1}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        
        <!-- Next page button -->
        <c:choose>
            <c:when test="${items.last}">
                <li class="page-item disabled"><span class="page-link">Next</span></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${items.number + 1}">Next</a></li>
            </c:otherwise>
        </c:choose>
    </c:if>
</ul>


<%@ include file="../layout/footer.jsp"%>



