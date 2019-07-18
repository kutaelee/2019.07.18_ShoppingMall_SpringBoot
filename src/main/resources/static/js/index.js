

$(document).ready(()=>{
	/* 배너 버튼클릭 */
	var scrollCount=2;
	var sw=0;
	$(".header").load("../include/header.html");
	window.setInterval(()=>{
		if(sw==0){
			bannerScroll();
		}else{
			sw=0;
		}
	},3000);
	$(".banner-slide-btn").click(()=>{
		sw=1;
		bannerScroll();
	});
	
	function bannerScroll(){
		let count=scrollCount;
		$(".main-img1").attr("src","../img/"+count+".jpg");
		$('.main-img1').css("transition-timing-function","ease-in");
		if(scrollCount<3){
			scrollCount++;
		}else{
			scrollCount=1;
		}
	}
	
	/* 최신글 슬라이드 */
	var margin=0;
	
	$('.review-slide-btn').click(()=>{

		if(margin===0){	
			$('.new-review').css('margin-left','-95.8vw');
			margin=1;
		}
		else if(margin===1){
			$('.new-review').css('margin-left','0vw');
			margin=0;
		}		
	});
});

