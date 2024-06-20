function uploadImage() {
    let fileInput = document.getElementById('itemimage');
    let formData = new FormData();
    formData.append('itemImage', fileInput.files[0]);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/upload/image', true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('Image uploaded successfully.');
        } else {
            console.error('Failed to upload image.');
        }
    };

    xhr.send(formData);
}

document.getElementById('btn-saveimage').addEventListener('click', uploadImage);
