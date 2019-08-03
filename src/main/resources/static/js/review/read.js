$(document).ready(()=>{
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/'); //uri 매핑

	/* 상품,리뷰 데이터 바인딩 */
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
				type:'POST',
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
	
	/* 좋아요 클릭 이벤트 */
	$(document).on('click','.like-icon',(e)=>{
		let cnt=0;
		let seq=$('.review-seq').val();
		let id=e.target.id;
		if(id==='like-empty'){
			cnt=1;
		}else{
			cnt=-1;
		}
		let url='/ajax/likeUpdate';
		$.ajax({
			url:url,
			type:'PUT',
			data:{'cnt':cnt,'seq':seq},
			success:(result)=>{
				if(id==='like-empty'){
					$('.like-icon').attr('src','../img/icon/like.png');
					$('.like-icon').attr('id','like');
				}else{
					$('.like-icon').attr('src','../img/icon/emptylike.png');
					$('.like-icon').attr('id','like-empty');
				}
				$('.like-cnt').text(result);
			},
			error:(e)=>{
				alert('로그인 후 이용해주세요');
				console.log(e);
			}
		});
	});
	
	/* 상품정보 편집 후 리턴 */
	function bindProductInfo(result){
		if(result[0]){
		let productInfo='';
		for(item of result){
			productInfo='<h1>'+item.P_TITLE+'</h1><hr>'
			+'<div class="product-box" id="product-thumnail-box"><img class="product-thumnail"src='+item.P_THUM_IMG_PATH+'></div>'
			+'<table class="product-box" id="product-info-box">'
			+'<input type="hidden" value='+item.P_SEQ+'>'
			+'<td class="thead">가격(원)</td><td class="product-value">'+item.PRICE+'</td><tr>'
			+'<td class="thead">별점</td><td class="product-value">'+ratingImg(item.RATING_AVG)+'</td><tr>'
			+'<td class="thead">제조년도</td><td class="product-value">'+item.REL_REG_DT+'</td><tr>'
			+'<td class="thead">제조회사</td><td class="product-value">'+item.MAN_COMPANY+'</td><tr>'
			+'<td class="thead">마지막 수정자</td><td class="product-value">'+item.LAST_MOD_ID+'</td><tr>'
			+'<td class="thead">사이즈</td><td class="product-value">'+item.SIZE+'<td></table><hr>';
		}

		$('.product').append(productInfo);
		}else
			$('.product').append('<h1> 제품정보가 존재하지않습니다.</h2>');
	}
	/* 별점 이미지 리턴 */
	function ratingImg(rating){
		let str='';
		for(let i=1;i<=5;i++){
			if(i<=rating){
				str+='<img class="rating-img" src="../img/icon/star.png">';
			}else{
				str+='<img class="rating-img" src="../img/icon/emptystar.png">';
			}
		}
		return str;
	}
	/* 리뷰 정보 편집 후 리턴 */
	function bindReviewInfo(result){
		let reviewInfo='';
		for(item of result){
			reviewInfo='<div class="review-box">'
				+'<h1 class="review-title">“ '+item.R_TITLE+' ”</h1><hr>'
				+'<p class="regdate">작성일:'+item.FRST_REG_DT+'</p>'
				+'<p class="regdate">수정일:'+item.LAST_REG_DT+'</p>'
				+'<img class="thum-img" src="'+item.R_THUM_IMG_PATH+'">'
				+'<p class="rating">리뷰 작성자의 별점:'+ratingImg(item.RATING_AVG)+'</p>'
				+'<div class="content">'+item.CONTENTS+'</div>'
				+'<img class="like-icon" id="like-empty" src="../img/icon/emptylike.png"><p class="like-cnt">'+item.LIKE_CNT+'</p>'
				+'<input class="review-seq" type="hidden" value="'+item.R_SEQ+'">';
				$('.list-btn').attr('href','/reviewlist/'+item.PARENT_SEQ);
		}
		$('.review').append(reviewInfo);
		
	}
});