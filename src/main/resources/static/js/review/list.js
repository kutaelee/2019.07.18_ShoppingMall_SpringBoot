/* 리뷰 리스트 마우스 오버*/
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
	let REVIEW_COUNT;
	let CURRENT_PAGE=1;
	
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/');
	
	/* 리뷰 리스트 마우스아웃 */
	$('.overlay').mouseleave(()=>{
		$('.overlay').stop().hide();
	});
	
	/* 리뷰 리스트 클릭*/
	$('.overlay').click(()=>{
		let id=$('.overlay').prop('id');
		window.location.href='/review/'+id;
	})
	
	/* 카테고리 정보 -> 리뷰 개수 -> 리뷰리스트  데이터 바인딩 */
	getSubCategory().then((result)=>{
		getReviewCount(result).then((result)=>{
				if(result){
					paging(1);
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
					REVIEW_COUNT=result;
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
	/* 페이징 */
	function paging(startNum){
		$('.reivew-list-paging').html('<a class="page-first">《</a>');
		if(startNum>=11){
			$('.reivew-list-paging').append('<a class="page-prev">〈</a>');
		}else{
			startNum=1;
		}
		let pageCount=1;
		
		if(REVIEW_COUNT>5){
			let str;
			let len=Math.ceil(REVIEW_COUNT/5);
			let count=startNum+len%10-1;
			if(len%10===0 || len>=startNum+9){
				count=startNum+9;
			}
			if(len<10){
				count=len;
			}
			for(let i=startNum;i<=count;i++){
					$('.reivew-list-paging').append('<a class="pagenum" id="pagenum-'+i+'">'+i+'</a>');
			}
			if((startNum+10)*5<REVIEW_COUNT){
				$('.reivew-list-paging').append('<a class="page-next">〉</a>');
			}
			$('.reivew-list-paging').append('<a class="page-last">》</a>');

		}else{
			$('.reivew-list-paging').append('<a class="pagenum" id="pagenum-1">1</a><a class="page-next">〉</a> <a class="page-last">》</a>');
		}
		if(CURRENT_PAGE===1){
			selectPage(1);
		}

	}
	/* 페이지 next 버튼 클릭 */
	$(document).on('click','.page-next',(e)=>{
		if(CURRENT_PAGE%10===0){
			CURRENT_PAGE--;
		}
		let startNum=Math.floor(CURRENT_PAGE-CURRENT_PAGE%10+11);
		if(startNum>CURRENT_PAGE){
			CURRENT_PAGE=startNum;
			paging(startNum);
			selectPage(startNum);
		}
	});
	/* 페이지 prev 버튼 클릭 */
	$(document).on('click','.page-prev',(e)=>{
		if(CURRENT_PAGE%10===0){
			CURRENT_PAGE--;
		}
		let startNum=Math.floor(CURRENT_PAGE-CURRENT_PAGE%10-9);
		if(1>startNum){
			startNum=1;
		}
		CURRENT_PAGE=startNum;
		paging(startNum);
		selectPage(startNum);
	});
	/* 페이지 last 버튼 클릭 */
	$(document).on('click','.page-last',(e)=>{
		let len=Math.ceil(REVIEW_COUNT/5);
		if(len>10){
			CURRENT_PAGE=len;
			paging(len-len%10+1);
			selectPage(len);
		}else{
			CURRENT_PAGE=len;
			paging(len);
			selectPage(len);
		}
	});
	/* 페이지 first 버튼 클릭 */
	$(document).on('click','.page-first',(e)=>{
		if(CURRENT_PAGE!==1){
			CURRENT_PAGE=1;
			paging(1);
		}
	});
	
	/* 페이지 넘버 클릭 시 */
	$(document).on('click','.pagenum',(e)=>{
		let num=$('#'+e.target.id).text();
		if(CURRENT_PAGE==num){
			return false;
		}
		CURRENT_PAGE=num;
		selectPage(num);
	});
	
	function selectPage(num){
		$('.pagenum').css('color','grey');
		$('#pagenum-'+num).css('color','crimson');
		getReviewList();
	}
	
	/* 서브카테고리 리뷰 데이터 바인딩 */
	function getReviewList(){
		let url='/ajax/getReviewList';
		let data=path[2];
		let index=(CURRENT_PAGE-1)*5;
		if(CURRENT_PAGE===1){
			index=0;
		}
		$('.review-item-table').text("");
		$.ajax({
			url:url,
			type:'POST',
			data:{'seq':data,'index':index},
			success:(result)=>{
				console.log(result);
				for(item of result){
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