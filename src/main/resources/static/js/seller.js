let index = {
	init: function() {
		$("#btn-save").on("click", () => { 
			this.save();
		});
		$("#btn-update").on("click", () => { 
			this.update();
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
			url: "/seller/new/item",
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
	
	update: function() {
		let id = $("#id").val();
		
		let data = {
			itemName: $("#itemname").val(),
			price: $("#price").val(),
			stock: $("#stock").val(),
			itemText: $("#itemtext").val()	,
			itemImage: $("#itemimage").val()
		};

		$.ajax({
			type: "PUT",
			url: "/seller/update/item/"+id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset = utf-8", 
			dataType: "json" 
		}).done(function(resp) {
			if(resp.status === 500){
				alert("상품수정 실패");
			}
			else{
				alert("상품수정 완료");
				location.href = "/";
			}
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
}

index.init();