function reviewClick(element){
	let seq=$('#'+element.id+' input').val();
	location.href='/review/'+seq;
}
$(document).ready(()=>{
	var scrollCount=2;
	var bannerClickSw=0; // 배너 스위치
	var reviewCount=0; // 리뷰 섹션 인덱스
	var currentTop=0; // 현재 스크롤 위치
	
	$('.header').load('../include/header.html');
	
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
		$('.main-img1').attr('src','../img/'+count+'.jpg');
		$('.main-img1').css('transition-timing-function','ease-in');
		if(scrollCount<3){
			scrollCount++;
		}else{
			scrollCount=1;
		}
	} 
	
	/* item 슬라이드 버튼 클릭*/
	$('.slide-btn').click((e)=>{
		let id=e.target.id;
		reviewSlide();
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

	
	/* 세일아이템 슬라이드 */
	function reviewSlide(){
		if(reviewCount===0){	
			$('.sale-item').css('margin-left','-107vw');
			$('#item-slide-left-btn').css('margin-left','110vw');
			$('#item-slide-right-btn').css('margin-left','201vw');
			$('.item-section-nav div:nth-child(1)').css('background','transparent');
			$('.item-section-nav div:nth-child(2)').css('background','black');
			reviewCount=1;
		}
		else if(reviewCount===1){
			$('.sale-item').css('margin-left','0');
			$('#item-slide-left-btn').css('margin-left','3vw');
			$('#item-slide-right-btn').css('margin-left','94vw');
			$('.item-section-nav div:nth-child(1)').css('background','black');
			$('.item-section-nav div:nth-child(2)').css('background','transparent');
			reviewCount=0;
		}		
	}
	

	/* 새로운 리뷰 데이터 바인딩 */
	getNewReview();
	function getNewReview(){
	    let url='/ajax/getNewReview';  
	    $.ajax({      
	        type:'POST',  
	        url:url,      
	        success:(result)=>{   
	            console.log(result);
	            let list = result;
	            let newReview='';
	            for(let i=0;i<list.length;i++){
	            	newReview += '<div class="review-slot" id="new-review-slot'+(i+1)+'" onClick="reviewClick(this)">'
	            	+'<img class="review-img" src="'+list[i].THUM_IMG_PATH+'">'
	            	+'<h1>'+list[i].TITLE+'</h1><hr>'
	            	+'<div class="review-content" id="new-review-content'+i+'">'+list[i].CONTENTS+'</div>'
	            	+'<input type="hidden" value="'+list[i].SEQ+'">'
	            	+'</div>';
	            }
	        	$('#new-review').html(newReview);
	        	
	        	for(let i=0;i<list.length;i++){
	            	$('#new-review-content'+i).text($('#new-review-content'+i).text());
	        	}
	            
	        },   
	        error:(e)=>{  
	          console.log(e);
	        }
	     });
	}
	
	/* 베스트 리뷰 데이터 바인딩 */
	getBestReview();
	function getBestReview(){
	    let url='/ajax/getBestReview';  
	    $.ajax({      
	        type:'POST',  
	        url:url,      
	        success:(result)=>{   
	            let list = result;
	            console.log(list);
	            let bestReview='';
	            for(let i=0;i<list.length;i++){
	            	bestReview += '<div class="review-slot" id="best-review-slot'+(i+1)+'" onClick="reviewClick(this)">'
	            	+'<img class="review-img" src="'+list[i].THUM_IMG_PATH+'">'
	            	+'<h1>'+list[i].TITLE+'</h1><hr>'
	            	+'<a class="like-cnt">'+list[i].LIKE_CNT+'</a><a class="like-cnt-info">명이 이 리뷰를 좋아합니다 </a>'
	            	+'<img class="best-review-like-icon" src="../img/icon/like.png">'
	            	+'<div class="review-content" id="best-review-content'+i+'">'+list[i].CONTENTS+'</div>'
	            	+'<input type="hidden" value="'+list[i].SEQ+'">'
	            	+'</div>';
	            }
	        	$('#best-review').html(bestReview);
	        	for(let i=0;i<list.length;i++){
	            	$('#best-review-content'+i).text($('#best-review-content'+i).text());
	        	}
	        },   
	        error:(e)=>{  
	          console.log(e);
	        }
	     });
	}
	
	/* 세일 아이템 데이터 바인딩 */
	getProduct();
	function getProduct(){
	    let url='/ajax/getProduct';  
	    $.ajax({      
	        type:'POST',  
	        url:url,      
	        success:(result)=>{  
	            let list = result;
	    
	        	for(var i=0;i<10;i++){
	        		let product='<div class="item-section-slot">'
	        		if(i<list.length){
			        		product+='<img src="'+list[i].THUM_IMG_PATH+'">'
			        		+'<a href="'+list[i].URL+'">'+list[i].TITLE+'</a>'
			        		+'<p> 세일중</p>'
	        		}else{
	        			product+='<img src="../img/empty.jpg">'
	        				+'<a>상품을 준비중입니다.</a>'
	        		}

	        		if(i<5){
	        			$('#item-sec1').append(product);
	        		}else{
	        			$('#item-sec2').append(product);
	        		}
	        	
	        	}
	        	
	        },
	        error:(e)=>{
	        	console.log(e);
	        }
	    });
	}
});