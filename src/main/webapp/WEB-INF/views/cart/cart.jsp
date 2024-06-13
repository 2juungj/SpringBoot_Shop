<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<br>
<div class="container">
	<div class="col-md-12" style="text-align: center;">
		<h1 class="page-header">장바구니</h1>
		<br>
	</div>
	<div class="row qnas" style="text-align: center;">
		<table class="table table-hover" style="width: 70%; margin: auto; border-bottom: 1px solid #D5D5D5;">
			<thead>
				<tr>
					<th></th>
					<th colspan="2" style="text-align: center;">상품명</th>
					<th>가격</th>
					<th>수량</th>
					<th>상품정보</th>
					<th>전체 선택 <input type="checkbox" id="selectAll" onclick="toggleAll(this)" checked></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${cartItems != null && fn:length(cartItems) > 0}">
						<c:forEach items="${cartItems}" var="cartItem" varStatus="status">
							<tr>
								<td><img alt="thumbnail" src="/image/upload/${fn:substringAfter(cartItem.item.itemImage, 'C:\\fakepath\\')}" width="80" height="80"></td>
								<td><input type="hidden" value="${cartItem.id}" id="id"> <!-- 장바구니 상품 삭제에 필요 --></td>
								<td>${cartItem.item.itemName}</td>
								<td><fmt:formatNumber type="number" value="${cartItem.item.price}" />&nbsp;원</td>
								<td><select onchange="updateAllPrice()">
										<c:forEach var="count" begin="1" end="${cartItem.item.stock}">
											<c:choose>
												<c:when test="${count == cartItem.count}">
													<option selected>${count}</option>
												</c:when>
												<c:otherwise>
													<option>${count}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select></td>
								<td>${cartItem.item.itemText}</td>
								<td><input type="checkbox" name="myCheckbox" class="itemCheckbox" onclick="updateSelectAll()" checked></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7" style="text-align: center;"><h3>장바구니에 내역이 없습니다.</h3></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

	<div class="col-md-12" style="text-align: center; margin: 80px 0;">
		<p>
			<!-- 상품이 담기지 않았을 때, 기본값 0원 -->
			결제 금액: <span id="allPrice">0</span>원
			<span id="shippingFeeMessage" style="display: none;">(배송비 포함)</span>
			<span id="freeShippingMessage" style="display: none;">(배송비 무료)</span>
		</p>
		<button class="btn btn-default" id="btn-buySelected">선택된 상품 주문하기</button>
		<div>
			<button class="btn btn-default" id="btn-delete">선택된 상품 장바구니에서 삭제하기</button>
		</div>
	</div>

</div>

<script type="text/javascript">
	function toggleAll(source) {
		checkboxes = document.getElementsByClassName('itemCheckbox');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
		updateAllPrice();
	}

	function updateSelectAll() {
		var allChecked = true;
		checkboxes = document.getElementsByClassName('itemCheckbox');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			if (!checkboxes[i].checked) {
				allChecked = false;
				break;
			}
		}
		document.getElementById('selectAll').checked = allChecked;
		updateAllPrice();
	}

	function updateAllPrice() {
		var allPrice = 0;
		var rows = document.querySelectorAll('tbody tr');
		rows.forEach(function(row) {
			var checkbox = row.querySelector('input[type="checkbox"]');
			if (checkbox && checkbox.checked) {
				var price = parseInt(
						row.querySelector('td:nth-child(4)').innerText.replace(
								/[^0-9]/g, ''), 10);
				var quantity = parseInt(row.querySelector('select').value, 10);
				allPrice += price * quantity;
			}
		});
		// 배송비 추가 로직
		var shippingFee = 0;
		var shippingMessage = '';
		if (allPrice > 0 && allPrice < 30000) {
			shippingFee = 3000;
			shippingMessage = '(배송비 포함)';
		} else if (allPrice >= 30000) {
			shippingMessage = '(배송비 무료)';
		}
		var totalPrice = allPrice + shippingFee;

		document.getElementById('allPrice').innerText = totalPrice.toLocaleString();
		document.getElementById('shippingFeeMessage').style.display = shippingFee > 0 ? 'inline' : 'none';
		document.getElementById('freeShippingMessage').style.display = allPrice >= 30000 ? 'inline' : 'none';
	}

	// 초기 로드 시 총 금액 업데이트
	document.addEventListener('DOMContentLoaded', function() {
		updateAllPrice();
	});
</script>

<script src="/js/cart.js"></script>
<%@ include file="../layout/footer.jsp"%>