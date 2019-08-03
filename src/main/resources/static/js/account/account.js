$(document).ready(()=>{	
	let emailDoubling=false;
	let idDoubling=false;
	let emailLenCheck=false;
	let idLenCheck=false;
	let pwLenCheck=false;
	let pw2LenCheck=false;
	let pwSame=false;
	let eamilPatten=false;
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
	
	/* 회원 가입 */
	$('.sign-btn').click(()=>{
		if(emptyCheck() && emailDoubling && idDoubling && lengthCheck() && pwSameCheck() && emailPattenCheck()){
			insertUser();
		}else{
			if(!emptyCheck()){
				alert("빈칸이 존재합니다.");
			}
			else if(!emailDoubling){
				alert("이미 등록된 이메일 입니다.");
			}else if(!idDoubling){
				alert("이미 등록된 아이디 입니다.");
			}else if(!pwSameCheck){
				alert("비밀번호가 같지 않습니다.");
			}else if(!lengthCheck()){
				alert("입력하신 글자수가 4자 이하 이거나 50자 이상 입니다.");
			}else if(!emailPattenCheck()){
				alert("이메일 규격을 맞춰야합니다.");
			}
		}	
	});
	
	function insertUser(){
		const data=$('.sign-form').serialize();
		const url='/ajax/insertUser'
		$.ajax({
			type:'POST',
			url:url,
			data:data,
			success:(result)=>{
				alert('회원가입이 완료되었습니다!');
				history.back();
			},
			error:(e)=>{
				console.log(e);
				alert('회원가입 실패');
			}
		});
		
	}

	/* 회원 가입 밸류 체크 */
	let doubling=false;
	$('.sign-form input').change((e)=>{
		const key=e.target.className;
		const value=e.target.value;
		if(key==='id'){
			idPattenReplace(value);
		}
		lengthCheck();

		if(pwLenCheck){
			$('.pw-check-icon').attr('src','../img/icon/success.png');
		}else{
			$('.pw-check-icon').attr('src','../img/icon/error.png');
		}
		
		pwSameCheck();
		
		if(pwSame){
			$('.pw2-check-icon').attr('src','../img/icon/success.png');
		}else{
			$('.pw2-check-icon').attr('src','../img/icon/error.png');
		}
		
		if(!(key==='pw') && !(key==='pw-check')){
			doubleCheck(key.toUpperCase(),value);

		}
		
	});
	
	/* 빈값이 있으면 false를 반환 */
	function emptyCheck(){
		const email=$('.sign-form .email').val();
		const id=$('.sign-form .id').val();
		const pw=$('.sign-form .pw').val();
		const pwCheck=$('.sign-form .pw-check').val();
		let bool=true;
		
		if(!email){
			$('.sign-form .email').focus();
			bool=false;
		}else if(!id){
			$('.sign-form .id').focus();
			bool=false;
		}else if(!pw){
			$('.sign-form .pw').focus();
			bool=false;
		}else if(!pwCheck){
			$('.sign-form .pw-check').focus();
			bool=false;
		}

			return bool;	
	}
	
	/* 비밀번호 두개가 같지 않으면 false를 반환 */
	function pwSameCheck(){
		const pw=$('.sign-form .pw').val();
		const pw2=$('.sign-form .pw-check').val();
		if(pw===pw2 && pw2.length>3){
			pwSame=true;
			return true;
		}else{
			pwSame=false;
			return false;
		}
	}
	
	/* 길이가 모두 규격에 맞지 않으면 false를 반환 */
	function lengthCheck(){
		let bool=true;
		const emailLen=$('.sign-form .email').val().length;
		const idLen=$('.sign-form .id').val().length;
		const pwLen=$('.sign-form .pw').val().length;
		const pwCheckLen=$('.sign-form .pw-check').val().length;
		
		if(emailLen>50 || emailLen<4){
			emailLenCheck=false;
			bool=false;
		}else{
			emailLenCheck=true;
		}
		
		if(idLen>50 || idLen<4){
			idLenCheck=false;
			bool=false;
		}else{
			idLenCheck=true;
		}
		
		if(pwLen>50 || pwLen<4){
			pwLenCheck=false;
			bool= false;
		}else{
			pwLenCheck=true;
		}
		
		if(pwLen>50 || pwCheckLen<4){
			pw2LenCheck=false;
			bool=false;
		}else{
			pw2LenCheck=true;
		}
		return bool;
	}
	
	/* 중복값이 있으면 false를 반환 */
	function doubleCheck(key,value){
		const url='/ajax/doubleCheck';
		
		$.ajax({
			type:'POST',
			url:url,
			data:{'key':key,'value':value},
			success:(result)=>{
				if(result){
					if(key==='EMAIL'){
						emailDoubling=false;
					}
					if(key==='ID'){
						idDoubling=false;
					}
				}else{
					if(key==='EMAIL'){
						emailDoubling=true;
					}
					if(key==='ID'){
						idDoubling=true;
					}
				}
			},
			error:(e)=>{
				console.log(e);
			}
		}).done(()=>{
			
			if(emailDoubling && emailLenCheck && emailPattenCheck()){
				$('.email-check-icon').attr('src','../img/icon/success.png');
			}else{
				$('.email-check-icon').attr('src','../img/icon/error.png');
			}
			
			if(idDoubling && idLenCheck){
				$('.id-check-icon').attr('src','../img/icon/success.png');
			}else{
				$('.id-check-icon').attr('src','../img/icon/error.png');
			}

			
		});
	}
	/* 특수문자가 있으면 빈값으로 치환 */
	function idPattenReplace(value){
		const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
		   if (regExp.test(value)) {
			      $('.sign-form .id').val(value.replace(regExp, ""));
		   }
	}
	/* email 정규식 검사*/
	function emailPattenCheck(){
		let value=$('.email').val();
		const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		   if (regExp.test(value)) {
				return true;
			}else{
				return false;
			}
	}
	/* 로그인페이지 세션체크 */
	$.ajax({
		url:'/ajax/sessionCheck',
		type:'post',
		success:(result)=>{
			if(result){
				alert('이미 로그인하셨습니다.');
				window.history.back();
			}
		}
	});
});