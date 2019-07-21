$(document).ready(()=>{
	/* 메뉴 토글 */
	function navToggle(button){
		if(button==='toggle-off'){
			$('.nav-toggle-btn').attr('id','toggle-on');
			$('.nav-toggle-icon').attr('src','../img/icon/cancel.png');
			$('.nav').css('margin-left','85%');
			$('.nav').css('transition','0.3s');
			$('.search').fadeOut();
			$('.title').slideUp('slow');
			$('.login-icon').fadeOut();
		}
		if(button==='toggle-on'){		
			$('.nav-toggle-btn').attr('id','toggle-off');
			$('.nav-toggle-btn').css('color','black');
			$('.nav-toggle-icon').attr('src','../img/icon/list.png');
			$('.nav').css('margin-left','100%');
			$('.nav').css('transition','0.3s');
			$('.search').fadeIn('slow');
			$('.title').slideDown('slow');
			$('.login-icon').fadeIn();
		}
	}

	$('.nav-bar').click(()=>{
		let button=$('.nav-toggle-btn').attr('id');
		navToggle(button);
	});
	
	/* 검색 */
	$('.search-icon').click((e)=>{
		let id=e.target.id;
		if(id==='search-toggle-btn'){
			$('.search-icon').attr('id','search-submit-btn');
			$('.search-text').fadeIn();
			$('.search-text').focus();
		}
		if(id==='search-submit-btn'){
			document.location.href='/searchpage?'+$('.search-text').val();
		}
	});

	/* 로그인 버튼 */
	$('.login-icon').click(()=>{
		document.location.href='/account';
	});
	
	/* 카테고리 데이터 바인드 */
	getCategory();
	function getCategory(){
		let url="/ajax/getMainCategory";  
		$.ajax({
			type:'post',
			url:url,
			success:(data)=>{		
				for(item of data){
					let mainCategory="<h2 id=main-category"+item.SEQ+">"+item.TITLE+"</h2>" 
					+"<div class='main-category-list' id=main-category"+item.SEQ+"-list>" 
					+ "<ul id=sub-category"+item.SEQ+"></ul></div>";
					$('.nav').append(mainCategory);
				}
			},
			error:(e)=>{
				console.log(e);
			}
		}).done((data, status, responseObj) => {
			let url="/ajax/getSubCategory";  
			$.ajax({
				type:'post',
				url:url,
				success:(data)=>{
					for(item of data){
						let mainCategory= "<li><a href='/subcategory"+item.SEQ+"'>"+item.TITLE+"</a></li>";	
						$('#sub-category'+item.PARENT_SEQ).append(mainCategory);
					}
				},
				error:(e)=>{
					console.log(e);
				}
			});
			
			}).fail((result, status, responseObj) => {
				console.log(e);
		});
	}
	
	/* 메인카테고리 클릭 토글 */
	$(document).on('click','.nav h2',(e)=>{
		let id=e.target.id;
		if($('#'+id+'-list ul').css('display')==='none'){
			$('.main-category-list ul').slideUp('fast');
			$('#'+id+'-list ul').slideToggle('fast');
		}else{
			$('.main-category-list ul').slideUp('fast');
		}
	});
	
});