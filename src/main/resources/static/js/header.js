$(document).ready(()=>{
	function navToggle(button){
		if(button==='toggle-off'){
			$('.nav-toggle-btn').attr('id','toggle-on');
			$('.nav-toggle-btn').text('X');
			$('.nav').css('margin-left','85%');
			$('.nav').css('transition','0.3s');
			$('.title').css('width','85%');
			$('.title').css('transition','0.3s');
		}
		if(button==='toggle-on'){
			$('.nav-toggle-btn').attr('id','toggle-off');
			$('.nav-toggle-btn').css('color','black');
			$('.nav-toggle-btn').text('三');
			$('.nav').css('margin-left','100%');
			$('.nav').css('transition','0.3s');
			$('.title').css('width','100%');
			$('.title').css('transition','0.3s');
		}
	}
	$('.nav-bar').click(()=>{
		let button=$('.nav-toggle-btn').attr('id');
		navToggle(button);
	});

});