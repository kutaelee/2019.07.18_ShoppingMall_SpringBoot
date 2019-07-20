$(document).ready(()=>{

	var scrollCount=2;
	var bannerClickSw=0;
	var reviewCount=0;
	var currentTop=0;
	
	$(".header").load("../include/header.html");
	/* 배너 자동변경 */
	window.setInterval(()=>{
		if(bannerClickSw===0){
			bannerSlide();
		}else{
			bannerClickSw=0;
		}
	},3000);
	/* 스크롤 */
	$(window).scroll(function () { 
		currentTop = $(document).scrollTop(); 
		if(currentTop>$('.item-section-nav').scrollTop()){
			window.setTimeout(()=>{
				displayInlineblock(['new-review-slot1','new-review-slot2','new-review-title','new-review-title-info']);
				listFadeIn(['new-review-title','new-review','new-review-title-info']);
			},100);
		}
		
		if(currentTop>$('.review-slot').offset().top+$('.review-slot').outerHeight(true)-$(window).height() 
				&& 'inline-block'===$('#new-review-slot1').css('display')){
			window.setTimeout(()=>{
				console.log('a')
				listFadeIn(['best-review-title','best-review','best-review-title-info']);
				displayInlineblock(['best-review-slot1','best-review-slot2','best-review-title','best-review-title-info']);
			},200);
		}
	});
	
	/* css 변환 */
	function displayInlineblock(list){
		for(id of list){
			$('#'+id).css('display','inline-block');
		}
	}
	function listFadeIn(list){
		for(id of list){
			$('#'+id).fadeIn('slow');
		}
	}
	
	/* 슬라이드 버튼 클릭*/
	$(".slide-btn").click((e)=>{
		let id=e.target.id;
		if(id==='item-slide-right-btn'||id==='item-slide-left-btn'){
			reviewSlide();
		}
	
	});
	$('.nav-circle').click((e)=>{
		let id=e.target.id;
		if(id==='nav-circle1'){
			reviewCount=1;
		}else{
			reviewCount=0;
		}
		reviewSlide();
	});
	/* 배너 버튼클릭 */
	function bannerSlide(){
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
	function reviewSlide(){
		if(reviewCount===0){	
			$('.new-item').css('margin-left','-107vw');
			$('.item-section-nav div:nth-child(1)').css('background','transparent');
			$('.item-section-nav div:nth-child(2)').css('background','black');
			reviewCount=1;
		}
		else if(reviewCount===1){
			$('.new-item').css('margin-left','0');
			$('.item-section-nav div:nth-child(1)').css('background','black');
			$('.item-section-nav div:nth-child(2)').css('background','transparent');
			reviewCount=0;
		}		
	}

});