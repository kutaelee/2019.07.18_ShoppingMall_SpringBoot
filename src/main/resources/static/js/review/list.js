
function spettacoliIn(){
	let offset=$(this).offset();
	let height=$(this).css('height');
	let width=$(this).css('width');
	$('.overlay').stop().show();
	$('.overlay').css('top',offset.top);
	$('.overlay').css('height',height);
	$('.overlay').css('width',width);
	$('.overlay').attr('id',this.id);
}
$(document).ready(()=>{
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/');
	
	$('.overlay').mouseleave(()=>{
		$('.overlay').stop().hide();
	});
	$('.overlay').click(()=>{
		let id=$('.overlay').prop('id');
		window.location.href='/review/'+id;
	})
	/* 카테고리 정보 -> 리뷰 개수 -> 리뷰리스트  데이터 바인딩 */
	getSubCategory().then((result)=>{
		getReviewCount(result).then((result)=>{
				if(result){
					getReviewList();
				}
		});
	});
	
	/* 서브카테고리 명 데이터 바인딩 */
	function getSubCategory(callback){
		return new Promise((resolve,reject)=>{
			let url='/ajax/getSubCategory';
			let data=path[2];
			
			$.ajax({
				url:url,
				type:'POST',
				data:{'seq':data},
				success:(result)=>{
					$('.subcategory-title').prepend(result);	
					resolve(result);
				},
				error:(e)=>{
					reject(e);
					console.log(e);
				}
			});
		});
	}
	
	/* 서브카테고리의 전체 리뷰 갯수 리턴 */
	function getReviewCount(result){
		return new Promise((resolve,reject)=>{
		$('.subcategory-list-title').prepend(result);
		let url='/ajax/getReviewCount';
		let data=path[2];
		$.ajax({
			url:url,
			type:'POST',
			data:{'seq':data},
			success:(result)=>{
				if(result==='0'){
					$('.subcategory-list-title').append('의 리뷰가 존재하지않습니다.');
				}else{
					$('.subcategory-list-title').append(' 카테고리의 리뷰  개수 <a>('+result+')</a>');
					resolve(result);
				}
			},
			error:(e)=>{
				reject(e);
				console.log(e);
			}
		});
		});
	}

	/* 서브카테고리 리뷰 데이터 바인딩 */
	function getReviewList(){
		let url='/ajax/getReviewList';
		let data=path[2];
		$.ajax({
			url:url,
			type:'POST',
			data:{'seq':data},
			success:(result)=>{
				console.log(result);
				for(item of result){
					let a='<div class="overlay"><h1 class="overlay-plus">+</h1><h1 class="overlay-info">자세히 보러가기</h1></div>';
					let reviewItem='<tr class="review-item" onMouseOver="spettacoliIn.call(this)" id="'+item.SEQ+'">'
					+'<td class="review-item-thumnail"><img class="item-img" src="'+item.THUM_IMG_PATH+'"></td>'
					+'<td class="review-item-info"><h3 class="review-item-title">'+item.TITLE+'</h3>'
					+'<p class="review-item-regdate">'+item.FRST_REG_DT+'</p>'
					+'<p class="review-item-content">'+item.CONTENTS+'</p>'
					+'<p class="review-item-wirter">By.'+item.FRST_REG_ID+'</p>'
					+'<p class="review-item-likecnt">'+item.LIKE_CNT+'명이 도움받은 리뷰입니다</p>'
					+'<img class="review-item-like-icon" src="../img/icon/like.png">'
					+'</td></tr>'
					$('.review-item-table').append(reviewItem);
				}
			},
			error:(e)=>{
				console.log(e);
				}
		});
		
	}
});