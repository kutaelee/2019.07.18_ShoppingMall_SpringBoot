
$(document).ready(()=>{
/*	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$(() => {
	    $(document).ajaxSend((e, xhr, options) => {
	        xhr.setRequestHeader(header, token);
	    });
	});*/
	/* 메뉴 토글 */
	function navToggle(button){
		if(button==='toggle-off'){
			$('.nav-toggle-btn').attr('id','toggle-on');
			$('.nav-toggle-icon').attr('src','../img/icon/cancel.png');
			$('.nav-toggle-icon').attr('title','닫기');
			$('.nav').css('margin-left','85%');
			$('.nav').css('transition','0.5s');
			$('.search').fadeOut();
			$('.icon').fadeOut();
			$('.title').slideUp();
			$('.nav-title').css('transform','translateX(0vw)');
			$('.nav h2').css('transform','translateX(0vw)');
		}
		if(button==='toggle-on'){
			$('.nav-toggle-btn').attr('id','toggle-off');
			$('.nav-toggle-btn').css('color','black');
			$('.nav-toggle-icon').attr('src','../img/icon/list.png');
			$('.nav-toggle-icon').attr('title','메뉴');
			$('.nav').css('margin-left','100%');
			$('.nav').css('transition','0.3s');
			$('.search').fadeIn('slow');
			$('.icon').show();
			$('.title').slideDown('slow');
			
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

	/* 헤더 로그인아이콘 세션체크 */
	$.ajax({
		url:'/ajax/sessionCheck',
		type:'post',
		success:(result)=>{
			if(result){
				$('.login-icon').css('display','none');
				$('.logout-icon').css('display','block');
			}else{
				$('.logout-icon').css('display','none');
				$('.login-icon').css('display','block');
			}
		}
	});
	
	/* 로그인 버튼 */
	$('.login-icon').click(()=>{
		document.location.href='/account';
	});
	/* 로그아웃 버튼 */
	$('.logout-icon').click(()=>{
		$.ajax({
			url:'/logout',
			type:'post',
			success:()=>{
				alert('정상적으로 로그아웃되었습니다.');
				window.location.reload();
			},error:(e)=>{
				console.log(e);
			}
		});
	});
	/* 카테고리 데이터 바인드 */
	getCategory();
	function getCategory(){
		let url="/ajax/getMainCategoryList";  
		$.ajax({
			type:'POST',
			url:url,
			success:(result)=>{		
				let i=.2;
				for(item of result){
					let mainCategory='<h2 id="main-category'+item.SEQ+'" style="transition:'+i+'s">'+item.TITLE+'</h2>'
					+'<div class="main-category-list" id="main-category'+item.SEQ+'-list">' 
					+ '<ul id="sub-category'+item.SEQ+'"></ul></div>';
					$('.nav').append(mainCategory);
					i+=.1;
				}
			},
			error:(e)=>{
				console.log(e);
			}
		}).done((result, status, responseObj) => {
			let url="/ajax/getSubCategoryList";  
			$.ajax({
				type:'POST',
				url:url,
				success:(result)=>{
					for(item of result){
						let mainCategory= '<li><a href="/reviewlist/'+item.SEQ+'">'+item.TITLE+'</a></li>';	
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
		$('#'+id+'-list ul').slideToggle('fast');
	});
	
});