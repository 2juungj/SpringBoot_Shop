<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<h1 style="text-align: center;">상품 등록</h1>
	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
		<!-- 사진 첨부 및 미리보기 -->
		<form method="post"  enctype="multipart/form-data">
			<img id="imagePreview" src="#" alt=" 사진을 추가해주세요" style="max-width: 200px; max-height: 200px;">
			<div>
				<br> 
				<input type="file" id="itemimage" name="itemimage" onchange="previewImage(event)"  required> 
				<br>
			</div>
			<br>
			<input type="button" value="이미지 비우기" onclick="clearImage()">
			<br><br>
			<input type="button" value="이미지 저장" id="btn-saveimage">
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

<!-- 사진 첨부, 미리보기 동작 함수 -->
<script type="text/javascript">
	function previewImage(event) {
		var reader = new FileReader();
		reader.onload = function() {
			var output = document.getElementById('imagePreview');
			var imagePath = document.getElementById('imagePath');
			output.src = reader.result;
			imagePath.value = event.target.value;
		}
		reader.readAsDataURL(event.target.files[0]);
	}

	function clearImage() {
		var output = document.getElementById('imagePreview');
		output.src = "#"; // 이미지 초기화
		var fileInput = document.querySelector('input[type=file]');
		fileInput.value = ""; // 파일 입력값 초기화
		var imagePath = document.getElementById('imagePath');
		imagePath.value = ""; // 이미지 경로 초기화
		 var fileInput = document.getElementById('itemimage');
		    fileInput.value = ""; // 파일 입력값 초기화
	}
</script>

<script src="/js/item.js"></script>
<script src="/js/fileUpload.js"></script>
<%@ include file="../layout/footer.jsp"%>
