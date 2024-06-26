let indexCart = { // item/detail.jsp에서 seller.js의 index 함수와 이름이 겹쳐서 수정
	init: function() {
		$("#btn-cart").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteSelected();
		});
		$("#btn-orderCartForm").on("click", () => {
			this.orderForm();
		});
	},

	save: function() {
		let data = {
			id: $("#id").val(), // itemId
			count: $("#count").val()
		};

		$.ajax({
			type: "POST",
			url: "/cart/new",
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("장바구니 담기 실패");
			}
			else {
				alert("장바구니 담기 완료");
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteSelected: function() {
		let ids = [];
		$(".itemCheckbox:checked").each(function() {
			ids.push($(this).closest("tr").find("#id").val()); // 체크박스가 선택된 cartItem.id를 ids 리스트에 담는다.
		});
		if (ids.length === 0) {
			alert("삭제할 상품을 선택해주세요.");
			return;
		}
		$.ajax({
			type: "DELETE",
			url: "/cart/delete",
			data: JSON.stringify(ids),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("장바구니 내 상품 삭제 실패");
			} else {
				alert("장바구니 내 상품 삭제 완료");
				location.reload(); // 선택된 항목 삭제 후 페이지 새로고침
			}
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	orderForm: function() {
		let ids = [];
		$(".itemCheckbox:checked").each(function() {
			ids.push($(this).closest("tr").find("#id").val());
		});
		if (ids.length === 0) {
			alert("구매할 상품을 선택해주세요.");
			return;
		}
		let queryString = ids.map(id => `id=${id}`).join("&");
		location.href = `/order/orderCart?${queryString}`; // 체크박스가 선택된 cartItem.id들을 쿼리스트링으로 담아서 전달 (ajax를 사용하지 않으므로)
	},

}

indexCart.init();