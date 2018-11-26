var app = {};
$(function(){
	app.songList = songs;
	var myTouch = util.toucher(document.getElementById('carousel-example-generic'));
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
		app.currentRowid = rowid;
		app.currentMid = mid;
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
	app.currentRowid = rowid;
	app.currentMid = mid;
	var songname = row.data.songname;
	$("#player_songname").text(songname);
	var singername = row.data.singer[0].name;
	$("#player_singername").text(singername);
	var  albummid = row.data.albummid;
	var imgUrl = getCover(albummid);
	$("#singer_cover").attr("src",imgUrl);
	$("#currentTime span").text(formatTime(app.currentTime));
	$("#songTime span").text(formatTime(app.songTime));
}
function formatTime(value){
	var second = value;//秒
	var minute = 0;//分
	var hour = 0;//时
	if(second>59){
		minute = parseInt(second/60);
		second = parseInt(second%60);
		if(minute>59){
			hour = parseInt(minute/60);
			minute = parseInt(minute%60);
		}
	}
	second = second>10?second:"0"+second;
	minute = minute>10?minute:"0"+minute;
	if(hour<1){
		return minute+":"+second;
	}else{
		hour = hour>10?hour:"0"+hour;
		return hour+":"+minute+":"+second;
	}
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
	loadLyr();
	timePointRun();
	timePointTouch();
}
function resPlayer(){
	$("#player_normal").show();
	$("#player_super").hide();
	$("#player").css({"background-color":"#fe0041"});
}
function loadLyr(){
	var song = app.getCurrentSong();
	$(".lyr_title h1").text(song.data.songname);
	$("#top_song_title").text(song.data.songname);
	var singer = "";
	for(i in song.data.singer){
		singer += ","+song.data.singer[i].name;
	}
	$("#top_song_singer").text(singer.substring(1));
	var songid = song.data.songid;
	$.ajax({
		url:'/music/getlyr',
		dataType:'json',
		type:'get',
		data:{songid:songid},
		success:function(json){
			if (json.success) {
				var data = json.data;
				data = data.substring(7,data.length-1);
				data = JSON.parse(data);
				var lrc = data.lyric;
				ly = lrc.replace(/&#(\d+);/g, (str, match) => String.fromCharCode(match));
				console.log(ly)
			}
		}
	});
}
function timePointTouch(){
	var param = app.playerParm();
	var pointTouch = util.toucher(document.getElementById('time_point'));
	pointTouch.on('swipe',function(e){
		var x = e.pageX - 15;
		var time = app.songTime - (Math.acos((x - param.centerX)/param.r)-Math.PI/3)*app.songTime/param.radian;
		time = Math.ceil(time) - 2;
		if(time>app.songTime){
			time = app.songTime - 1;
		}else if(time<0){
			time = 0;
		}
		app.currentTime = time;
	});
}
function timePointRun(){
	var param = app.playerParm();
	var it = setInterval(function() {
		app.currentTime++;
		app.run(param,app.currentTime);
		if(app.currentTime >= app.songTime){
			app.currentTime = 0;
			clearInterval(it);
		}
	},1000);
	app.jumpTime();
}
app.songTime = 60;
app.currentTime = 0;
app.getCurrentSong = function(){
	var rowid = this.currentRowid;
	return songs[rowid];
}
app.run = function(param,currentTime){
	var radian = param.radian/app.songTime*currentTime;
	var x = param.centerX + Math.cos(radian+Math.PI*4/3)*param.r;
	var y = param.centerY + Math.sin(radian+Math.PI*4/3)*param.r;
	if(x>app.timePoint_end.x){
		x = app.timePoint_end.x;
		y = app.timePoint_end.y;
	}
	$("#time_point").animate({top:y+'px',left:x+'px'},10);
	$("#currentTime span").text(formatTime(currentTime));
}
app.jumpTime = function(){
	var param = app.playerParm();
	$("#bar_bg").off("click").on("click",function(e){
		var x = e.pageX - 15;
		var y = e.offsetY;
		var time = app.songTime - (Math.acos((x - param.centerX)/param.r)-Math.PI/3)*app.songTime/param.radian;
		time = Math.ceil(time) - 2;
		var radian = param.radian/app.songTime*time;
		var y1 = param.centerY + Math.sin(radian+Math.PI*4/3)*param.r;
		if(y<y1+5){
			app.currentTime = time;
		}
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
	var startX = centerX + Math.cos(Math.PI*4/3)*r;
	var startY = centerY + Math.sin(Math.PI*4/3)*r;
	var endX = centerX + Math.cos(radian+Math.PI*4/3)*r
	var endY = centerY + Math.sin(radian+Math.PI*4/3)*r
	this.timePoint_start = {x:startX,y:startY};
	this.timePoint_end = {x:endX,y:endY};
	$("#time_point").css({top:startY+'px',left:startX+'px'});
	return {
		centerX:centerX,
		centerY:centerY,
		r:r,
		radian:radian
	};
}
