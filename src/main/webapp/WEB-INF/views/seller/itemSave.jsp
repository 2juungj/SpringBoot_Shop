<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<h1 style="text-align: center;">상품 등록</h1>
	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
		<form method="post" action="/upload" enctype="multipart/form-data">
			<img id="imagePreview" src="#" alt=" 사진을 추가해주세요" style="max-width: 200px; max-height: 200px;">
			<div>
				<br> <input type="file" name="image" onchange="previewImage(event)"> <br>
			</div>
			<br> <input type="button" value="이미지 비우기" onclick="clearImage()">
		</form>
		<div style="flex: 1;">
			<div>
				<label>상품명</label> <input type="text" class="form-control" id="itemname" required>
				<!-- required를 추가하면 필수 입력 값이 된다. -->
			</div>
			<div>
				<label>상품 가격</label> <input type="number" class="form-control" id="price" required>
			</div>
			<div>
				<label>재고 수량</label> <input type="number" class="form-control" id="stock" required>
			</div>
			<div>
				<label>상품 설명</label> <input type="text" class="form-control" id="itemtext" required>
			</div>
			<br>
			<div>
				<input type="button" value="상품 등록" id="btn-save">
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function previewImage(event) {
		var reader = new FileReader();
		reader.onload = function() {
			var output = document.getElementById('imagePreview');
			output.src = reader.result;
		}
		reader.readAsDataURL(event.target.files[0]);
	}

	function clearImage() {
		var output = document.getElementById('imagePreview');
		output.src = "#"; // 이미지 초기화
		var fileInput = document.querySelector('input[type=file]');
		fileInput.value = ""; // 파일 입력값 초기화
	}
</script>

<%@ include file="../layout/footer.jsp"%>



