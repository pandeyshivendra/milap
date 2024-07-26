
    $(document).ready(function () {
        $('#sectionSelect').on('change', function() {
            var selectedValue = $(this).val();
            console.log('Selected Value:', selectedValue); // Debug log
            if (selectedValue === '1') {
                $('#foundSomeone').show();
                $('#foundSomeone').css('display','flex');
                $('#foundSomeone').css('flex-direction','column');
                $('#foundSomeone').css('gap','2rem');
                $('#reportLostPerson').hide();
            } else if (selectedValue === '2') {
                $('#foundSomeone').hide();
                $('#reportLostPerson').show();
                $('#reportLostPerson').css('display','flex');
                $('#reportLostPerson').css('flex-direction','column');
                $('#reportLostPerson').css('align-items','center');

            } else {
                $('#foundSomeone').hide();
                $('#reportLostPerson').hide();
            }
        });
        $("#uploadImg").fileinput({
            showBrowse: true,
            browseOnZoneClick: true
        })
        $("#uploadResult").fileinput({
            showBrowse: true,
            showUpload:false,
            browseOnZoneClick: true
        })
    });
