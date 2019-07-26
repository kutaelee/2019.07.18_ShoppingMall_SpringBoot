$(document).ready(()=>{
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/'); //uri 매핑
	getReviewInfo();
	function getReviewInfo(){
		const url='/ajax/getReviewInfo';
		$.ajax({
			url:url,
			data:{'seq':path[2]},
			success:(result)=>{
				console.log(result);
			},
			error:(e)=>{
				console.log(e);
			}
		})
	}
});