var app = {};
$(function(){
	var myTouch = util.toucher(document
			.getElementById('carousel-example-generic'));
	myTouch.on('swipeLeft', function(e) {
		$('#carright').click();
	}).on('swipeRight', function(e) {
		$('#carleft').click();
	});
	app.init();
})
//初始化
app.init = function(){
	//初始化播放器
	setPlayer(0)
	//歌曲列表绑定点击事件
	$(".songs").on("click",function(){
		var rowid = $(this).attr("rowid");
		var mid = songs[rowid].data.songmid;
		setPlayer(rowid);
		app.play(mid);
	});
	//分享添加功能绑定点击事件
	$(".more").off("click").on("click",function(e){
		var $td = $(this).parent().prev();
		var left = $td.offset().left;
		var top = $td.offset().top+22;
		var h_top = $("#helper").offset().top;
		if(h_top!=top){
			$("#helper").hide();
		}
		$("#helper").css({"left":left,"top":top}).fadeToggle(500);
		hideElement("more","helper");
		e.stopPropagation();
	});
	//播放功能绑定点击事件
	$("#play").off("click").on("click",function(){
		var status = $(this).attr("isplay");
		isPlay(status)
		var mid = $("#audio").attr("mid");
		if(!mid||mid.trim()==""){//没有选择的时候默认播放第一首
			mid = songs[0].data.songmid;
			app.play(mid);
			$("#audio").attr("mid", mid);
			return
		}
		audio.paused?audio.play():audio.pause();
	});
}
//设置播放器内容
function setPlayer(rowid){
	$("#audio").attr("rowid",rowid);
	var row = songs[rowid];
	var mid = row.data.songmid;
	var songname = row.data.songname;
	$("#player_songname").text(songname);
	var singername = row.data.singer[0].name;
	$("#player_singername").text(singername);
	var  albummid = row.data.albummid;
	var imgUrl = getCover(albummid);
	$("#singer_cover").attr("src",imgUrl);
}
//获取歌曲的相关信息并播放
app.play = function(mid){
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
	//audio.addEventListener('timeupdate', updateProgress, false);
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	//audio.addEventListener('ended', audioEnded, false);
	
}
//播放
function audioPlay(ev) {
	isPlay(true);
}
//暂停
function audioPause(ev) {
	isPlay(false);
}
//是否播放
function isPlay(isplay){
	var imgurl = "/images/app/pause.svg";
	if (!isplay) {
		imgurl = "/images/app/play.svg"
	}
	$("#play").attr({
		"src":imgurl,
		"isplay":isplay
	});
}
//获取vkey
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
function shellPlayer(){
	var rowid = $("#audio").attr("rowid");
	var row = songs[rowid];
	var songmid = row.data.songmid;
	var albummid = row.data.albummid;
	//window.location.href = "../music/toPlayer?songmid="+songmid+"&songid="+songid+"&albumid="+albumid;
	var height = $("#player").height();
	if(height==50){
		$("#player").animate({"height":"100%"},function(){
			changePlayer(albummid);
		});
	}/*else{
		$("#player").animate({"height":"50px"},function(){
			resPlayer();
		});
	}*/
}
function changePlayer(albummid){
	$("#player_normal").hide();
	$("#player_super").show();
	var imgUrl = getCover(albummid);
	$("#lyr_bg").css({"background-image":"url("+imgUrl+")"});
	$("#player").css("background-color","#fff");
	timePointRun();
}
function resPlayer(){
	$("#player_normal").show();
	$("#player_super").hide();
	$("#player").css({"background-color":"#fe0041"});
}

function timePointRun(){
	var param = app.playerParm();
	var it = setInterval(function() {
		app.currentTime++;
		app.run(param,app.currentTime);
		if(app.currentTime == app.songTime){
			app.currentTime = 0;
			clearInterval(it);
		}
	},1000);
	app.jumpTime();
}
app.songTime = 60;
app.currentTime = 0;
app.run = function(param,currentTime){
	var radian = param.radian/app.songTime*currentTime;
	var x = param.centerX + Math.cos(radian+Math.PI*4/3)*param.r;
	var y = param.centerY + Math.sin(radian+Math.PI*4/3)*param.r;
	$("#time_point").animate({top:y+'px',left:x+'px'},10);
}
app.jumpTime = function(){
	$("#bar_bg").off("click").on("click",function(e){
		var x = e.offsetX;
		var y = e.offsetY;
		console.log(x,y,e)
	})
}
app.playerParm = function (){
	var bar = document.getElementsByClassName("progress_bar")[0];
	var d = bar.offsetWidth;
	var bg = document.getElementById("bar_bg");
	var w = bg.offsetWidth;
	var h =  bg.offsetHeight;
	//圆半径
	var r = w/2;
	//圆心坐标
	var centerX = bg.offsetLeft + r - 4; 
	var centerY = bg.offsetTop + r - 4;
	//弧度
	var radian = Math.acos(1-(d*d)/(2*r*r));
	var songTime = this.songTime;
	var x = centerX + Math.cos(Math.PI*4/3)*r;
	var y = centerY + Math.sin(Math.PI*4/3)*r;
	$("#time_point").css({top:y+'px',left:x+'px'});
	return {
		centerX:centerX,
		centerY:centerY,
		r:r,
		radian:radian
	};
}
