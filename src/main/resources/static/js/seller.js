let index = {
	init: function() {
		$("#btn-save").on("click", () => { 
			this.save();
		});
	},

	save: function() {
		let data = {
			itemName: $("#itemname").val(),
			price: $("#price").val(),
			stock: $("#stock").val(),
			itemText: $("#itemtext").val()	,
			itemImage: $("#itemimage").val()
		};

		$.ajax({
			type: "POST",
			url: "/seller/new/product",
			data: JSON.stringify(data), 
			contentType: "application/json; charset = utf-8", 
			dataType: "json" 
		}).done(function(resp) {
			if(resp.status === 500){
				alert("상품등록 실패");
			}
			else{
				alert("상품등록 완료");
				location.href = "/";
			}
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
}

index.init();