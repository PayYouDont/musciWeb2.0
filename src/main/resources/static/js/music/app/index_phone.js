$(function(){
	var myTouch = util.toucher(document
			.getElementById('carousel-example-generic'));
	myTouch.on('swipeLeft', function(e) {
		$('#carright').click();
	}).on('swipeRight', function(e) {
		$('#carleft').click();
	});
	$(".songs").on("click",function(){
		var mid = $(this).find("td").eq(0).find("input").eq(0).val();
		play(mid)
	});
	$(".more").off("click").on("click",function(e){
		$("#helper").hide();
		var $td = $(this).parent().prev();
		var left = $td.offset().left;
		var top = $td.offset().top+22;
		$("#helper").css({"left":left,"top":top}).fadeToggle(500);
		hideElement("more","helper");
		e.stopPropagation();
	});
})
function play(mid){
	var vkey = getVkey(mid);
	var playUrl = getM4a(mid,vkey);
	$("#audio").attr("src",playUrl);
	// 获得音频元素
	audio = document.getElementById("audio");
	/* 显示歌曲总长度 */
	if (audio.paused) {
		audio.play();
	} else {
		audio.pause();
	}
}
function getVkey(mid){
	var vkey = "";
	$.ajax({
		url:"../music/getVkey",
		type:"post",
		data:{songmid:mid},
		async : false,
		dataType : "json",
		success:function(json){
			if(json.success){
				var data = json.data;
				data = data.substring(data.indexOf("(")+1,data.length-1);
				data = JSON.parse(data);
				vkey = data.data.items["0"].vkey;
			}
		}
	})
	return vkey;
}