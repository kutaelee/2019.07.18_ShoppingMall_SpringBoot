$(document).ready(()=>{
	$(".header").load("../include/header.html");

	/* ck에디터 치환 */
	 CKEDITOR.replace('p-content', {height: 500 , 
		 filebrowserUploadUrl: '/ajax/fileUpload'});
	
	 /* 리뷰 및 상품 등록 */
	 $('#review-submit-btn').click(()=>{
		 let reviewContents = CKEDITOR.instances['p-content'].getData();
		 let reviewTitle=$('#review-title').val();
		 let productSeq=$('#product-box .select-category').prop('id');
		 let subCategorySeq=$('#sub-box .select-category').prop('id');
		 if(productSeq){
			 productSeq=seqReturn(productSeq);
			 subCategorySeq=seqReturn(subCategorySeq);
			 if(productSeq==='others'){
				 newProductReviewInsert(reviewContents,reviewTitle,subCategorySeq);
			 }else{
				 productReviewInsert(reviewContents,reviewTitle,productSeq,subCategorySeq);
			 }
		 }else{
			 alert('상품정보는 필수입니다.');
		 }
	 });
	 //이미 있는 제조사 및 제품명 사용불가하게 해야됨
	 //파일패스 수정필요
	 //미리보기 글 추출필요
	 function newProductReviewInsert(reviewContents,reviewTitle,subCategorySeq){
		 let proTitle=$('#product-name').val();
		 let proCompany=$('#product-company').val();
		 let proPrice=$('#product-price').val();
		 let proRegdate=$('#product-regdate').val();
		 let proThumnailImg=$('#product-img').val();
	
		 if(proTitle && proCompany && proPrice && proRegdate && proThumnailImg){
			 let url='/ajax/newProductReviewInsert';
			 $.ajax({
				type:'POST',
				url:url,
				data:{'proTitle':proTitle,'proCompany':proCompany,'proPrice':proPrice
					,'proRegdate':proRegdate,'proThumnailImg':proThumnailImg,'reviewContents':reviewContents
					,'reviewTitle':reviewTitle,'subCategorySeq':subCategorySeq},
				success:(result)=>{
					if(result){
						alert('작성이 완료되었습니다.');
						location.href='/reviewlist/'+subCategorySeq;
					}else{
						alert('로그인세션이 끊겼습니다 다시 로그인해주세요.');
					}
				},error:(e)=>{
					console.log(e);
				}
			 });
		 }else{
			 alert('상품정보를 모두 채워주세요');
		 }
	 }
	 /* 제조년월 데이트피커 */
	  $( "#product-regdate" ).datepicker({
		    dateFormat: 'yy.mm.dd',
		    prevText: '이전 달',
		    nextText: '다음 달',
		    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    dayNames: ['일','월','화','수','목','금','토'],
		    dayNamesShort: ['일','월','화','수','목','금','토'],
		    dayNamesMin: ['일','월','화','수','목','금','토'],
		    showMonthAfterYear: true,
		    changeMonth: true,
		    changeYear: true,
		    yearSuffix: '년'
		});
	
	  /* id에서 seq추출 */
	  function seqReturn(id){
			let str=id.split('-');
			return str[1];
		}
	  /* 카테고리 클릭 이벤트 */
		$(document).on('click','.main-category',(e)=>{
			let id=e.target.id;
			let seq=seqReturn(id);
			$('#main-box .select-category').attr('class','main-category');
			$('#main-'+seq).attr('class','select-category');
			getSubCategoryMatchParentSeq(seq);
		});
		$(document).on('click','.sub-category',(e)=>{
			let id=e.target.id;
			let seq=seqReturn(id);
			$('#sub-box .select-category').attr('class','sub-category');
			$('#sub-'+seq).attr('class','select-category');
			getManCompanyTitle(seq);
		});
		$(document).on('click','.man-company',(e)=>{
			let id=e.target.id;
			let seq=seqReturn(id);
			let parentSeq=seqReturn($('#sub-box .select-category').prop('id'));
			$('#company-box .select-category').attr('class','man-company');
			$('#com-'+seq).attr('class','select-category');
			getProductTitle(seq,parentSeq);
		});
		$(document).on('click','.product',(e)=>{
			let id=e.target.id;
			let seq=seqReturn(id);
			let parentSeq=seqReturn($('#sub-box .select-category').prop('id'));
			$('#product-box .select-category').attr('class','product');
			$('#product-'+seq).attr('class','select-category');
			if(seq==='others'){
				$('#product-info').fadeOut();
				$('#others-info').fadeIn();
			}else{
				$('#others-info').fadeOut();
				getProductInfo(seq,parentSeq);
				$('#product-info').fadeIn();
			}
		});

	  /* 메인 카테고리  바인딩 */
	  getMainCategory();
		function getMainCategory(){
			let url='/ajax/getMainCategoryList';  
			$.ajax({
				type:'POST',
				url:url,
				success:(result)=>{		
					for(item of result){
						let mainCategory='<li class="main-category" id="main-'+item.SEQ+'">'+item.TITLE+'</li>';
						$('#main-box ul').append(mainCategory);
					}
	
				},
				error:(e)=>{
					console.log(e);
				}
			});
		}
		/* 서브카테고리 바인딩*/
		function getSubCategoryMatchParentSeq(seq){
			let url='/ajax/getSubCategoryMatchParentSeq'; 
			$.ajax({
				type:'POST',
				url:url,
				data:{'parentSeq':seq},
				success:(result)=>{
					let subCategory='';
					for(item of result){
					subCategory+='<li class="sub-category" id="sub-'+item.SEQ+'">'+item.TITLE+'</li>';
					}
					$('#sub-box ul').html(subCategory);
				},
				error:(e)=>{
					console.log(e);
				}
			});
		}

		/* 제조사 데이터 바인딩 */
		function getManCompanyTitle(seq){
			let url='/ajax/getManCompanyTitle';  
			$.ajax({
				type:'POST',
				url:url,
				data:{'parentSeq':seq},
				success:(result)=>{		
					let company='';
					for(item of result){
						company+='<li class="man-company" id="com-'+item.SEQ+'">'+item.TITLE+'</li>';
					}
					$('#company-box ul').html(company);
				},
				error:(e)=>{
					console.log(e);
				}
			});
		}
		/* 상품명 바인딩  */
		function getProductTitle(manSeq,parentSeq){
			let url='/ajax/getProductTitle';
			$.ajax({
				type:'POST',
				url:url,
				data:{'parentSeq':parentSeq,'manSeq':manSeq},
				success:(result)=>{
					let product='';
					for(item of result){
						product+='<li class="product" id="product-'+item.SEQ+'">'+item.TITLE+'</li>';
					}
					product+='<li class="product" id="product-others">직접입력</li>';
					$('#product-box ul').html(product);
				},error:(e)=>{
					console.log(e);
				}
			});
		}
		/* 등록된 상품정보 바인딩 */
		function getProductInfo(seq,parentSeq){
			let url='/ajax/getProductInfo';
			$.ajax({
				type:'POST',
				url:url,
				data:{'seq':seq,'parentSeq':parentSeq},
				success:(result)=>{
					let productInfo='<p>제품 이미지</p>'
					+'<img src="'+result.THUM_IMG_PATH+'">'
					+'<p>제품명: '+result.TITLE+'</p>'
					+'<p>출시일: '+result.REL_REG_DT+'</p>'
					+'<p>가격: '+result.PRICE+'</p>'
					+'<p>최근 등록자: '+result.LAST_MOD_ID+'</p>'
					+'<p>최근 수정일: '+result.LAST_REG_DT+'</p>'
					+'<h4>등록된 제품정보가 잘못되었다면 수정요청을 부탁드립니다.</h4>';
					$('#product-info').html(productInfo);
				},error:(e)=>{
					console.log(e);
				}
			});
		}
});