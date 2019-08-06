$(document).ready(()=>{
	$(".header").load("../include/header.html");

	/* ck에디터 치환 */
	 CKEDITOR.replace('p-content', {height: 500 , 
		 filebrowserUploadUrl: '/ajax/fileUpload'});
	
	 /* 리뷰 작성 */
	 $('#review-submit-btn').click(()=>{
		 let reviewContents = CKEDITOR.instances['p-content'].getData();
		 let reviewTitle=$('#review-title').val();
		 console.log(reviewContents);
		 console.log(reviewTitle);
	 });
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
		
	  /* 메인 카테고리  바인딩 */
	  getMainCategory();
		function getMainCategory(){
			let url="/ajax/getMainCategoryList";  
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
			let url="/ajax/getSubCategoryMatchParentSeq"; 
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
			let url="/ajax/getManCompanyTitle";  
			$.ajax({
				type:'POST',
				url:url,
				data:{'parentSeq':seq},
				success:(result)=>{		
					let company='';
					for(item of result){
						company+='<li class="man-company" id="com-'+item.SEQ+'">'+item.TITLE+'</li>';
					}
					company+='<li class="man-company" id="com-other">기타</li>';
					$('#company-box ul').html(company);
				},
				error:(e)=>{
					console.log(e);
				}
			});
		}
		/* 상품정보 바인딩  */
		function getProductTitle(manSeq,parentSeq){
			let url="/ajax/getProductTitle";
			$.ajax({
				type:'POST',
				url:url,
				data:{'parentSeq':parentSeq,'manSeq':manSeq},
				success:(result)=>{
					let product='';
					for(item of result){
						product+='<li class="product" id="product-'+item.SEQ+'">'+item.TITLE+'</li>';
					}
					product+='<li class="product" id="other">직접입력</li>';
					$('#product-box ul').html(product);
				},error:(e)=>{
					console.log(e);
				}
			});
		}
	  
});