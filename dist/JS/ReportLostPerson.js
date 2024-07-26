$(document).ready(function() {
    $('#imageUpload').on('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                $('#previewImage').attr('src', e.target.result).show();
            };
            reader.readAsDataURL(file);
        }
    });

    $('#reportForm').on('submit', function(event) {
        event.preventDefault();
        const name = $('#name').val();
        const age = $('#age').val();
        const color = $('#color').val();
        const image = $('#imageUpload')[0].files[0];
        //add to data base 
        if (name && age && color && image) {
            alert('Details submitted successfully!');
        } else {
            alert('Please fill in all the details and upload an image.');
        }
    });
});
