$(document).ready(function () {

	$(".btn-select").click(function () {
	    $("#Pack_file").click();
      return false;
	})
$(".btn-select").click(function() {
    $("#EditPack_file").click();
    return false;
}) 
$(".btn-select").click(function () {
    $(".input-file").click();
    return false;
})
$(".btn-select-sticker").click(function () {
    $("#Sticker_file").click();
    return false;
})
  $("#Slide_type").change(function() {
      if ($("#Slide_type").val()==1) {
        $("#Slide_url").parent().hide();
        $("#Slide_category").parent().show();
        $("#Slide_pack").parent().hide();
      }
      if ($("#Slide_type").val()==2) {
        $("#Slide_url").parent().show();
        $("#Slide_category").parent().hide();
        $("#Slide_pack").parent().hide();
      }
      if ($("#Slide_type").val()==3) {
        $("#Slide_url").parent().hide();
        $("#Slide_category").parent().hide();
        $("#Slide_pack").parent().show();
      }
  });
 $(".btn-select-stickers").click(function () {
      $("#Pack_files").click();
      return false;
  })
 $(".input-file").change(function() {
     readURLL(this,"#img-preview");
 })
	$("#Pack_file").change(function(){
	    readURLL(this,"#img-preview");
	});
  $("#EditPack_file").change(function(){
      readURLL(this,"#img-preview");
  });
  $("#Sticker_file").change(function(){
      readURLWebp(this,"#img-preview");
  });
  $(".alert-dashborad .close").click(function() {
    $(".alert-dashborad").fadeOut();
  })
  $('#Pack_files').on('change', function() {
      $("div.pack-stickers-files").html("");
      imagesPreview(this, 'div.pack-stickers-files');
  });

    // Multiple images preview in browser
    var imagesPreview = function(input, placeToInsertImagePreview) {

        if (input.files) {
            var filesAmount = input.files.length;

            for (i = 0; i < filesAmount; i++) {
                var reader = new FileReader();

                reader.onload = function(event) {
                    $($.parseHTML('<img style="width:95px;height:95px;margin:8px;display:inline-block;border:1px solid #ccc"  class="thumbnail thumbnail-2" >')).attr('src', event.target.result).appendTo(placeToInsertImagePreview);
                }

                reader.readAsDataURL(input.files[i]);
            }
        }

    };

    
});
function readURLWebp(input,pr) {
    if (input.files && input.files[0]) {
    var countFiles = input.files.length;
     var imgPath = input.value;
     var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1).toLowerCase();

     if (extn == "png" || extn == "webp" ) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(pr).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      }else{
          alert("the image file is not valid please select a valid image file : Webp or png");
      }
    }
}
function readURLL(input,pr) {
    if (input.files && input.files[0]) {
    var countFiles = input.files.length;
     var imgPath = input.value;
     var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1).toLowerCase();

     if (extn == "png" || extn == "jpg" || extn == "jpeg" || extn == "gif") {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(pr).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      }else{
          alert("the image file is not valid please select a valid image file : png,jpg or jpeg");
      }
    }
}
