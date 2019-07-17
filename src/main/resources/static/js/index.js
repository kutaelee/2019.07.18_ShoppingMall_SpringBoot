

$(document).ready(()=>{
	var scrollCount=1;
	$(".header").load("../include/header.html");
	
	$(".banner-slide-btn").click(()=>{
		bannerScroll();
		if(scrollCount<3){
			scrollCount++;
		}else{
			scrollCount=1;
		}
	});
	
	function bannerScroll(){
		let count=scrollCount;
		for(let i=1;i<4;i++){
			count++;
			if(count>3){
				count=1;
			}
			$(".main-img"+i).css("background-image","url(../img/"+count+".jpg)");

		}
	}
});

