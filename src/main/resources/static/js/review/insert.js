$(document).ready(()=>{
	$(".header").load("../include/header.html");
	 CKEDITOR.replace('p_content', {height: 500 , 
		 filebrowserUploadUrl: '/ajax/fileUpload'});
});