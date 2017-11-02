$(document).ready(function(){
	$("#aside_left").click(function(){
  		var screen1 = window.matchMedia('(max-width: 900px)');
	    if (screen1.matches){
	        $("#header")[0].style.left=0;
	    }
  			$("#header_before").fadeToggle();
	});
	$("#header_before").click(function(){
		var screen1 = window.matchMedia('(max-width: 900px)');
	    if (screen1.matches){
	        $("#header")[0].style.left=-256+"px";
	    }
  			$("#header_before").fadeToggle();
	});
	$("#sousuo").click(function(){
		$(".search").animate({height:'55px'},"slow");
  		$(".search").contents()[0].focus();
	});
	$("#search").blur(function(){
		var screen1 = window.matchMedia('(max-width: 540px)');
		if (screen1.matches){
  			 $(".search").animate({height:'0px'},"slow");
  		}
	});
});