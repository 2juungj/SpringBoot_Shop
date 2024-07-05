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

		let merchant_uid = "L" + new Date().getTime(); // 주문번호 생성

		let IMP = window.IMP;
		IMP.init('imp24282365');

		IMP.request_pay({
			pg: "html5_inicis",           					// 등록된 pg사 (적용된 pg사는 KG이니시스)
			pay_method: "card",          				// 결제방식: card(신용카드), trans(실시간계좌이체), vbank(가상계좌), phone(소액결제)
			merchant_uid: merchant_uid,   	// 주문번호
			name: $("#itemName").val(),		// 상품명
			amount: data.allPrice,           			// 금액
			buyer_name: data.ordername,    // 주문자
			buyer_tel: data.tel,             			// 전화번호 (필수입력)
			buyer_addr: data.address    		// 주소
		}, function(rsp) {
			if (rsp.success) {
				// 결제 성공 시 서버에 주문 정보를 전송
				data.merchant_uid = merchant_uid;
				$.ajax({
					type: "POST",
					url: "/order/orderItem/new",
					data: JSON.stringify(data),
					contentType: "application/json; charset = utf-8",
					dataType: "json"
				}).done(function(resp) {
					if (resp.status === 500) {
						alert("상품구매 실패");
						location.reload();
					} else {
						alert("상품구매 완료");
						location.href = "/"; // 주문 확인 페이지 구현 시 해당 주소로 이동
					}
				}).fail(function(error) {
					alert(JSON.stringify(error));
				});
			} else {
				alert("결제가 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
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
		
		let merchant_uid = "L" + new Date().getTime(); // 주문번호 생성
		
		let IMP = window.IMP;
		IMP.init('imp24282365');

		IMP.request_pay({
			pg: "html5_inicis",          					 // 등록된 pg사 (적용된 pg사는 KG이니시스)
			pay_method: "card",           				 // 결제방식: card(신용카드), trans(실시간계좌이체), vbank(가상계좌), phone(소액결제)
			merchant_uid: merchant_uid,   	 // 주문번호
			name: data.allCount+"개의 상품",	 // 상품명
			amount: data.allPrice,           			 // 금액
			buyer_name: data.ordername,     // 주문자
			buyer_tel: data.tel,             			 // 전화번호 (필수입력)
			buyer_addr: data.address    		 // 주소
		}, function(rsp) {
			if (rsp.success) {
				// 결제 성공 시 서버에 주문 정보를 전송
				data.merchant_uid = merchant_uid;
				$.ajax({
					type: "POST",
					url: "/order/orderCart/new",
					data: JSON.stringify(data),
					contentType: "application/json; charset = utf-8",
					dataType: "json"
				}).done(function(resp) {
					if (resp.status === 500) {
						alert("상품구매 실패");
						location.reload();
					} else {
						alert("상품구매 완료");
						location.href = "/"; // 주문 확인 페이지 구현 시 해당 주소로 이동
					}
				}).fail(function(error) {
					alert(JSON.stringify(error));
				});
			} else {
				alert("결제가 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		});
	},

}

index.init();