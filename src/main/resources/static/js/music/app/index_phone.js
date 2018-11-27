var app = {};
//歌曲总时长
app.songTime = 0;
//歌曲当前时间
app.currentTime = 0;
//顺序播放
app.order = 1;
//普通播放模式
app.playerStatus = "miniPlayer";
$(function(){
	app.songList = songs;
	app.init();
})
//初始化
app.init = function(){
	//初始化图片轮播
	util.toucher(document.getElementById('carousel'))
	.on('swipeLeft', function(e) {
		$('#carright').click();
	}).on('swipeRight', function(e) {
		$('#carleft').click();
	});
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
	$("#play,#lyr_play").off("click").on("click",function(){
		var status = $(this).attr("isplay");
		isPlay(status)
		var mid = $("#audio").attr("songmid");
		if(!mid||mid.trim()==""){//没有选择的时候默认播放第一首
			mid = songs[0].data.songmid;
			app.play(mid);
			$("#audio").attr("songmid", mid);
			return
		}
		audio.paused?audio.play():audio.pause();
	});
	//下一曲
	$("#next,#lyr_next").off("click").on("click",function(){
		var rowid = $("#audio").attr("rowid");
		if(app.order==1){//顺序播放
			rowid++;
			if(rowid==app.songList.length){
				rowid = 0;
			}
		}else if(app.order==3){
			rowid = Math.floor(Math.random()*app.songList.length);
		}
		cutSong(rowid);
	});
	//上一曲
	$("#lyr_pause").off("click").on("click",function(){
		var rowid = $("#audio").attr("rowid");
		if(app.order==1){//顺序播放
			if(rowid==0){
				rowid = app.songList.length-1;
			}else{
				rowid--;
			}
		}else if(app.order==3){//随机播放
			rowid = Math.floor(Math.random()*app.songList.length);
		}
		cutSong(rowid);
	});
	//下滑
	util.toucher(document.getElementById('sliding_down'))
	.on("swipeDown",function(e){
		var y = e.moveY;
		if(y>10){
			$("#player").animate({"height":"50px"},function(){
				resPlayer();
			});
		}
	});
	//播放顺序
	$("#lyr_order").off("click").on("click",function(){
		var order = $(this).attr("order");
		if(order<3){
			order++;
		}else{
			order=1;
		}
		app.order = order;
		var imgurl = "/images/app/order_play_"+order+".svg";
		$(this).attr("order",order)
		$("#lyr_order img").attr("src",imgurl);
	});
}
//切歌
function cutSong(rowid){
	setPlayer(rowid);
	var mid = songs[rowid].data.songmid;
	$("#audio").attr("songmid",mid);
	app.play(mid);
	if(app.playerStatus=="superPlayer"){
		var  albummid = songs[rowid].data.albummid;
		changePlayer(albummid);
	}
}
//设置播放器内容
function setPlayer(rowid){
	$("#audio").attr("rowid",rowid);
	var row = songs[rowid];
	var songname = row.data.songname;
	$("#player_songname").text(songname);
	var singername = row.data.singer[0].name;
	$("#player_singername").text(singername);
	var  albummid = row.data.albummid;
	var imgUrl = getCover(albummid);
	$("#singer_cover").attr("src",imgUrl);
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
	audio.play();
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	audio.addEventListener('ended', audioEnded, false);
}
//播放
function audioPlay(ev) {
	isPlay(true);
}
//暂停
function audioPause(ev) {
	isPlay(false);
}
//更新进度
function updateProgress(ev){
	var songTime = Math.floor(audio.duration);
	app.songTime = songTime;
	var currentTime = Math.floor(audio.currentTime);
	app.currentTime = currentTime;
	$("#songTime span").text(formatTime(songTime));
	$("#currentTime span").text(formatTime(currentTime));
	app.tiomePointRun();
}
//播放结束
function audioEnded(){
	$("#next").click();
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
	$("#lyr_play img").attr({
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
	var height = $("#player").height();
	if(height==50){
		$("#player").animate({"height":"100%"},function(){
			//增强播放模式
			app.playerStatus = "superPlayer";
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
	app.initPlayerParm();
	app.timePointTouch();
	app.jumpTime();
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
				//console.log(ly)
			}
		}
	});
}
app.timePointTouch = function() {
	var param = this.playerParam;
	var pointTouch = util.toucher(document.getElementById('time_point'));
	pointTouch.on('swipe',function(e){
		var x = e.pageX - 15;
		var time = app.songTime - (Math.acos((x - param.centerX)/param.r)-Math.PI/3)*app.songTime/param.radian;
		time = Math.ceil(time) - 1;
		if(time>app.songTime){
			time = app.songTime;
		}else if(time<0){
			time = 0;
		}
		app.currentTime = time;
	});
}

app.getCurrentSong = function(){
	var rowid = $("#audio").attr("rowid");
	var mid = $("#audio").attr("songmid");
	app.currentRowid = rowid;
	app.currentMid = mid;
	return songs[rowid];
}
app.tiomePointRun = function(){
	var param = this.playerParam;
	var currentTime = this.currentTime;
	var radian = param.radian/this.songTime*currentTime;
	var x = param.centerX + Math.cos(radian+Math.PI*4/3)*param.r;
	var y = param.centerY + Math.sin(radian+Math.PI*4/3)*param.r;
	if(x>app.timePoint_end.x){
		x = app.timePoint_end.x;
		y = app.timePoint_end.y;
	}
	$("#time_point").css({top:y+'px',left:x+'px'});
}
app.jumpTime = function(){
	var param = this.playerParam;
	$("#bar_bg").off("click").on("click",function(e){
		var x = e.pageX - 15;
		var y = e.offsetY;
		var time = app.songTime - (Math.acos((x - param.centerX)/param.r)-Math.PI/3)*app.songTime/param.radian;
		time = Math.ceil(time) - 1;
		var radian = param.radian/app.songTime*time;
		var y1 = param.centerY + Math.sin(radian+Math.PI*4/3)*param.r;
		if(y<y1+5){
			audio.currentTime = time;
		}
	})
}
app.initPlayerParm = function (){
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
	var param = {
		centerX:centerX,
		centerY:centerY,
		r:r,
		radian:radian
	};
	this.playerParam = param;
	//进度条更新事件
	audio.addEventListener('timeupdate', updateProgress, false);
	return param;
}
