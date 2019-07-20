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
		}
		if(button==='toggle-on'){		
			$('.nav-toggle-btn').attr('id','toggle-off');
			$('.nav-toggle-btn').css('color','black');
			$('.nav-toggle-icon').attr('src','../img/icon/list.png');
			$('.nav').css('margin-left','100%');
			$('.nav').css('transition','0.3s');
			$('.search').fadeIn();
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
			document.location.href='/';
		}
	});

});