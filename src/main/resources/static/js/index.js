$(document).ready(()=>{
	var scrollCount=2;
	var bannerClickSw=0; // 배너 스위치
	var reviewCount=0; // 리뷰 섹션 인덱스
	var currentTop=0; // 현재 스크롤 위치
	
	$(".header").load("../include/header.html");
	
	/* 배너 자동변경 */
	window.setInterval(()=>{
		if(bannerClickSw===0){
			bannerSlide();
		}else{
			bannerClickSw=0;
		}
	},3000);
	
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
	
	/* 스크롤 애니메이션 */
	$(window).scroll(()=>{ 
		currentTop = $(document).scrollTop(); 
		if(currentTop>$('.item-section-nav').scrollTop()){
				displayInlineblock(['new-review-slot1','new-review-slot2','new-review-title','new-review-title-info']);
				listFadeIn(['new-review-title','new-review','new-review-title-info']);
		}
		
		if(currentTop>$('.review-slot').offset().top+$('.review-slot').outerHeight(true)-$(window).height() 
				&& 'inline-block'===$('#new-review-slot1').css('display')){
				listFadeIn(['best-review-title','best-review','best-review-title-info']);
				displayInlineblock(['best-review-slot1','best-review-slot2','best-review-title','best-review-title-info']);

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
	getNewReview();
	/* 새로운 리뷰 데이터 바인딩 */
	function getNewReview(){
	    let url="/ajax/getNewReview";  
	    $.ajax({      
	        type:"POST",  
	        url:url,      
	        success:(data)=>{   
	            console.log(data);
	            console.log(data[1].THUM_IMG_PATH);
	            let list = data;
	            
	            for(var i=0;i<list.length;i++){
	            	let newReview = '<div class="review-slot" id="new-review-slot'+(i+1)+'">'
	            	+'<img src="'+list[i].THUM_IMG_PATH+'">'
	            	+'<h1>'+list[i].TITLE+'</h1>'
	            	+'<p>'+list[i].CONTENTS+'</p>'
	            	+'</div>'
	            	$('#new-review').append(newReview);
	            }
	            
	            
	        },   
	        error:function(e){  
	          console.log(e);
	        }
	     });
	}
});