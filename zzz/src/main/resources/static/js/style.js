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
function post(obj) {
	var id = $(obj).data("id");
	var state = $(obj).prop("checked");
	$.ajax({
		type: "POST",
		url: "/task_update",
		data: {
			"id": id,
			"state": state
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("任务完成!");
			}
			if(data.code == "201") {
				console.log("succeed");
				alert("取消完成!");
			}
			if(data.code == "400") {}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function pro_edit(obj) {
	var id = $(obj).data("id");
	$.ajax({
		type: "POST",
		url: "/pro_edit",
		data: {
			"id": id
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("ok!");
			}
			if(data.code == "400") {
				alert("没有权限！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function pro_delete(pro) {
	var id = $(pro).data("id");
	var proid =pro.id;
	$.ajax({
		type: "POST",
		url: "/pro_delete",
		data: {
			"id": id,
			"proid": proid
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("删除成功!");
			}
			if(data.code == "400") {
				alert("没有权限！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}