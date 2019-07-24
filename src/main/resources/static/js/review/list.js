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
	let REVIEW_COUNT; //서브 카테고리에 포함된 전체 리뷰개수
	let CURRENT_PAGE=1; // 현재 페이지 넘버
	const path=location.pathname.split('/'); //uri 매핑
	let subjectTitle;
	$(".header").load("../include/header.html");
	let checkedCompanySeqList=[];
	/* 카테고리 데이터 바인딩 */
	/* 메인 카테고리SEQ -> 서브 카테고리SEQ-> 서브 카테고리 제목 */
	getMainCategory().then(()=>{
		getSubCategoryParentSeq().then((result)=>{
				getSubCategoryMatchParentSeq(result);
		});
	});
	getManCompanyTitle();
	
	/* 리뷰 상세 검색 */
	$(document).on('change','.company-checkbox',() =>{
			checkedCompanySeqList=[];
		   $('input[type="checkbox"]:checked').each(function (index,item){
			   checkedCompanySeqList.push($(this).val());
		   });
		   CURRENT_PAGE=1;
		   getReviewCount(subjectTitle,checkedCompanySeqList).then((result)=>{
			   REVIEW_COUNT=result;
			   paging(1);
			   $('.pagenum').css('color','grey');
			   $('#pagenum-1').css('color','crimson');
			   getReviewList(checkedCompanySeqList);
		   });
	});

	function getMainCategory(){
		return new Promise((resolve,reject)=>{
		let url="/ajax/getMainCategoryList";  
		$.ajax({
			type:'POST',
			url:url,
			success:(result)=>{		
				for(item of result){
					let mainCategory='<a id="main-'+item.SEQ+'">'+item.TITLE+'</a>';
					$('.main-category').append(mainCategory);
				}
				resolve();
			},
			error:(e)=>{
				reject(e);
				console.log(e);
			}
		});
		});
	}
	
	function getSubCategoryParentSeq(callback){
		return new Promise((resolve,reject)=>{
			let url='/ajax/getSubCategoryParentSeq';
			let data=path[2];
			
			$.ajax({
				url:url,
				type:'POST',
				data:{'parentSeq':data},
				success:(result)=>{
					resolve(result);
				},
				error:(e)=>{
					reject(e);
					console.log(e);
				}
			});
		});
	}
	
	function getSubCategoryMatchParentSeq(result){
		let url="/ajax/getSubCategoryMatchParentSeq"; 
		$('.main-category a').css('font-weight','unset');
		$('#main-'+result).css('font-weight','bold');
		$.ajax({
			type:'POST',
			url:url,
			data:{'parentSeq':result},
			success:(result)=>{
				let subCategory='';
				for(item of result){
				subCategory+='<a href="/reviewlist/'+item.SEQ+'"id="sub-'+item.SEQ+'">'+item.TITLE+'</a>';
				}
				$('.sub-category').html(subCategory);
				$('#sub-'+path[2]).css('font-weight','bold');
			},
			error:(e)=>{
				console.log(e);
			}
		});
	}
	/* 제조사 데이터 바인딩 */
	function getManCompanyTitle(){
		let url="/ajax/getManCompanyTitle";  
		$.ajax({
			type:'POST',
			url:url,
			data:{'parentSeq':path[2]},
			success:(result)=>{		
				for(item of result){
					$('#man-company').append('<input type="checkbox" value="'+item.SEQ+'"class="company-checkbox"><label for="company-'+item.SEQ+'">'+item.TITLE+'</label>');
				}
			},
			error:(e)=>{
				console.log(e);
			}
		});
	}
	/* 메인 카테고리 클릭 시 서브 카테고리 데이터 바인딩*/
	$(document).on('click','.main-category a',(e)=>{
		let seq=e.target.id.split('-');
		getSubCategoryMatchParentSeq(seq[1]);
	});
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
	getSubCategoryTitle().then((result)=>{
		getReviewCount(result).then((result)=>{
				if(result){
					paging(1);
					selectPage(1);
				}
		});
	});

	/* 서브카테고리 명 데이터 바인딩 */
	function getSubCategoryTitle(callback){
		return new Promise((resolve,reject)=>{
			let url='/ajax/getSubCategoryTitle';
			let data=path[2];
			
			$.ajax({
				url:url,
				type:'POST',
				data:{'seq':data},
				success:(result)=>{
					$('.subcategory-title').append(result);	
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
	function getReviewCount(result,checkedCompanySeqList){
		return new Promise((resolve,reject)=>{
		subjectTitle=result;
		$('.subcategory-list-title').text('카테고리 - '+subjectTitle);
		let url='/ajax/getReviewCount';
		let data={};
		if(checkedCompanySeqList){
			data={'parentSeq':path[2]};
			let i=0;
			for(item of checkedCompanySeqList){
				data['checkedCompanySeqList'+i]=item;
				i++;
			}
		}else{
			data={'parentSeq':path[2]};
		}
		$.ajax({
			url:url,
			type:'POST',
			data:data,
			success:(result)=>{
				if(result==='0'){
					$('.subcategory-list-title').text(subjectTitle+'의 리뷰가 존재하지않습니다.');
				}else{
					$('.subcategory-list-title').html(subjectTitle+' 카테고리의 리뷰  개수 <a>('+result+')</a>');
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
			$('.reivew-list-paging').append('<a class="pagenum" id="pagenum-1">1</a> <a class="page-last">》</a>');
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
		scorllBody();
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
		scorllBody();
	});
	/* 페이지 last 버튼 클릭 */
	$(document).on('click','.page-last',(e)=>{
		let len=Math.ceil(REVIEW_COUNT/5);
		if(len>10){
			CURRENT_PAGE=len;
			paging(len-len%10+1);
		}else{
			CURRENT_PAGE=len;
			paging(len);
		}
		selectPage(len);
		scorllBody();
	});
	/* 페이지 first 버튼 클릭 */
	$(document).on('click','.page-first',(e)=>{
		if(CURRENT_PAGE!==1){
			CURRENT_PAGE=1;
			paging(1);
			selectPage(1);
			scorllBody();
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
		scorllBody();
	});
	function scorllBody(){
		let offset=$('.review-list').offset();
		$('html,body').stop().animate({scrollTop :offset.top-50},100);
	}
	function selectPage(num){
		$('.pagenum').css('color','grey');
		$('#pagenum-'+num).css('color','crimson');
		getReviewList(checkedCompanySeqList);
	}
	
	/* 서브카테고리 리뷰 데이터 바인딩 */
	function getReviewList(checkedCompanySeqList){
		let url='/ajax/getReviewList';
		let parentSeq=path[2];
		let index=(CURRENT_PAGE-1)*5;
		if(CURRENT_PAGE===1){
			index=0;
		}
		let data={};
		if(checkedCompanySeqList){
			data={'parentSeq':parentSeq,'index':index}
			let i=0;
			for(item of checkedCompanySeqList){
				data['checkedCompanySeqList'+i]=item;
				i++;
			}
		}else{
			data={'parentSeq':parentSeq,'index':index};
		}
		$('.review-item-table').text("");
		$.ajax({
			url:url,
			type:'POST',
			data:data,
			success:(result)=>{
				console.log(result);
				$('.review-item-table').html(makeReviewList(result));
			},
			error:(e)=>{
				console.log(e);
				}
		});
	}
	function makeReviewList(result){
		let reviewItem='';
		for(item of result){
			reviewItem+='<tr class="review-item" onMouseOver="spettacoliIn.call(this)" id="'+item.SEQ+'">'
			+'<td class="review-item-thumnail"><img class="item-img" src="'+item.THUM_IMG_PATH+'"></td>'
			+'<td class="review-item-info"><h3 class="review-item-title">'+item.TITLE+'</h3>'
			+'<p class="review-item-regdate">'+item.FRST_REG_DT+'</p>'
			+'<p class="review-item-content">'+item.CONTENTS+'</p>'
			+'<p class="review-item-wirter">By.'+item.FRST_REG_ID+'</p>'
			+'<p class="review-item-likecnt">'+item.LIKE_CNT+'명이 도움받은 리뷰입니다</p>'
			+'<img class="review-item-like-icon" src="../img/icon/like.png">'
			+'</td></tr>';
		}
		return reviewItem;
	}
});