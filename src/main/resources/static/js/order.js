let index = {
	init: function() {
		$("#btn-orderItem").on("click", () => {
			this.orderItem();
		});
		$("#btn-orderCart").on("click", () => {
			this.orderCart();
		});
	},

	orderItem: function() {
		let data = {
			id: $("#itemId").val(),
			count: $("#itemCount").val(),
			allPrice: $("#allPrice").val(),
			ordername: $("#ordername").val(),
			address: $("#address").val(),
			email: $("#email").val(),
			tel: $("#tel").val(),
			shippingFee: $("#shippingFee").val()
		};

		$.ajax({
			type: "POST",
			url: "/order/orderItem/new",
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("상품구매 실패");
				location.reload;
			}
			else {
				alert("상품구매 완료");
				location.href = "/"; // 주문 확인 페이지 구현 시 해당 주소로 이동
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	orderCart: function() {
		let ids = [];
		$("input[id='id']").each(function() {
			ids.push($(this).val());
		});

		let data = {
			ids: ids,
			allPrice: $("#allPrice").val(),
			allCount: $("#allCount").val(),
			ordername: $("#ordername").val(),
			address: $("#address").val(),
			email: $("#email").val(),
			tel: $("#tel").val(),
			shippingFee: $("#shippingFee").val()
		};

		$.ajax({
			type: "POST",
			url: "/order/orderCart/new",
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("상품구매 실패");
				location.reload;
			}
			else {
				alert("상품구매 완료");
				location.href = "/"; // 주문 확인 페이지 구현 시 해당 주소로 이동
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();