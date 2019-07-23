$(document).ready(()=>{
	$(".header").load("../include/header.html");
	const path=location.pathname.split('/');
	
	/* 서브카테고리 명 데이터 바인딩 */
	getSubCategory();
	
	function getSubCategory(){
		let url='/ajax/getSubCategory';
		let data=path[2];
		
		$.ajax({
			url:url,
			type:'POST',
			data:{'seq':data},
			success:(result)=>{
				$('.subcategory-title').prepend(result);	
			},
			error:(e)=>{
				console.log(e);
			}
		}).done((result)=>{
			$('.subcategory-list-title').prepend(result);
			let url='/ajax/getReviewCount';
			let data=path[2];
			$.ajax({
				url:url,
				type:'POST',
				data:{'seq':data},
				success:(result)=>{
					console.log(result);
					if(result==='0'){
						$('.subcategory-list-title').prepend('상세검색을 포함한 ').append('의 리뷰가 존재하지않습니다.');
					}else{
						$('.subcategory-list-title').prepend('상세검색을 포함한 ').append('의 리뷰는 '+result+'개 입니다.');
					}
					
				
				},
				error:(e)=>{
					console.log(e);
				}
			})
		});
	}
	
});