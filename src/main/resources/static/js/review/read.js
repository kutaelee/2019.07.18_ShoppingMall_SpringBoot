$(document).ready(()=>{
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/'); //uri 매핑
	getReviewInfo().then((result)=>{
		bindProductInfo(result);
		bindReviewInfo(result);
	});
	
	
	/* 상품,리뷰 정보 데이터 바인딩*/
	function getReviewInfo(){
		return new Promise((resolve,reject)=>{
			const url='/ajax/getReviewInfo';
			$.ajax({
				url:url,
				data:{'seq':path[2]},
				success:(result)=>{
					console.log(result)
					resolve(result);
				},
				error:(e)=>{
					reject(e);
					console.log(e);
				}
			});
		});
	}
	
	/* 상품정보 편집 후 리턴 */
	function bindProductInfo(result){
		if(result[0]){
		let productInfo='';
		for(item of result){
			productInfo='<div class="product-box" id="product-thumnail-box"><img class="product-thumnail"src='+item.P_THUM_IMG_PATH+'></div>'
			+'<div class="product-box" id="product-info-box">'
			+'<input type="hidden" value='+item.P_SEQ+'>'
			+'<p>제품명:'+item.P_TITLE+'</p>'
			+'<p>별점:'+item.RATING_AVG+'</p>'
			+'<p>가격대:'+item.PRICE+'</p>'
			+'<p>제조일자:'+item.REL_REG_DT+'</p>'
			+'<p>제조회사:'+item.MAN_COMPANY+'</p>'
			+'<p>마지막 수정자:'+item.LAST_MOD_ID+'</p>'
			+'<p>사이즈:'+item.SIZE+'<p></div>';
		}

		$('.product').append(productInfo);
		}else
			$('.product').append('<h1> 제품정보가 존재하지않습니다.</h2>');
	}
	function bindReviewInfo(result){
		let reviewInfo='';
		for(item of result){
			reviewInfo='<div class="review-box">'
				+'<h1 class="review-title">'+item.R_TITLE+'</h1>'
				+'<img src="'+item.R_THUM_IMG_PATH+'">'
				+'<p>별점:'+item.RATING+'</p>'
				+'<p>내용:'+item.CONTENTS+'</p>'
				+'<p>작성일:'+item.FRST_REG_DT+'</p>'
				+'<p>마지막 수정일:'+item.LAST_REG_DT+'</p>'
				+'<p>좋아요:'+item.LIKE_CNT+'</p>'
		}
		$('.review').append(reviewInfo);
	}
});