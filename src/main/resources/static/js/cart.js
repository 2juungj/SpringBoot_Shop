let indexCart = { // item/detail.jsp에서 seller.js의 index 함수와 이름이 겹쳐서 수정
	init: function() {
		$("#btn-cart").on("click", () => {
			this.save();
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
}

indexCart.init();