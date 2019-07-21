$(document).ready(()=>{
	$(".header").load("../include/header.html");
	$('.box').html=$('#login-box').html();
	/* 인풋 블러효과 */
	$('input').focus((e)=>{
		let id=e.target.id;
		$('#'+id).css('border-color','black');
	});
	$('input').blur((e)=>{
		let id=e.target.id;
		$('#'+id).css('border-color','crimson');
	});
	
	/* 회원가입 폼 토글 */
	$('.signform-call-btn').click(()=>{
		$('#login-box').css('margin-left','-100vw');
		$('#sign-box').css('margin-left','36vw');
	});
	
	$('.loginform-call-btn').click(()=>{
		$('#login-box').css('margin-left','36vw');
		$('#sign-box').css('margin-left','100vw');
	});
	
	/* 회원정보 찾기 폼 토글 */
	$('.findform-call-btn').click(()=>{
		$('#login-box').css('margin-top','-100vh');
		$('#find-box').css('margin-top','10vh');
	});
	
	$('.back-call-btn').click(()=>{
		$('#login-box').css('margin-top','10vh');
		$('#find-box').css('margin-top','100vh');
	});
});