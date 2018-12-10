//歌曲总时长
app.songTime = 0;
//歌曲当前时间
app.currentTime = 0;
var a = $(function(){
	app.playerStatus = "superPlayer";
	changeBG();
	app.initPlayerParm();
	app.timePointTouch();
	app.jumpTime();
	app.init();
	
});
//初始化
app.init = function(){
	//播放功能绑定点击事件
	var isplay = $("#lyr_play").attr("isplay");
	var imgurl = "/images/app/pause.svg";
	if (isplay!="true") {
		imgurl = "/images/app/play.svg"
	}
	$("#lyr_play img").attr("src",imgurl);
	$("#lyr_play").off("click").on("click",function(){
		$("#play").click();
	});
	//下一曲
	$("#lyr_next").off("click").on("click",function(){
		$("#next").click();
		var songid = app.currentSong.data.songid;
		var playerUrl = "../music/player?songid="+songid;
		$("#player_super").load(playerUrl);
	});
	//上一曲
	$("#lyr_pause").off("click").on("click",function(){
		if(app.songList.length>0){
			var playid = app.getPrevSongId();
			var song = app.songList[playid];
			$("#audio").attr("playid",playid);
			cutSong(song);
		}
		var rowid = $("#audio").attr("rowid");
		var playerUrl = "../music/player?rowid="+rowid;
		$("#player_super").load(playerUrl);
	});
	//下滑
	util.toucher(document.getElementById('sliding_down'))
	.on("swipeDown",function(e){
		var y = e.moveY;
		if(y>10){
			$("#player").animate({"height":"50px"},function(){
				toMiniPlayer();
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
//更改背景
function changeBG(){
	var playid = $("#audio").attr("playid");
	var song = app.songList[playid];
	var albummid = song.data.albummid;
	var imgUrl = getCover(albummid);
	$("#lyr_bg").css({"background-image":"url("+imgUrl+")"});
	$("#player").css("background-color","#fff");
	var songname = song.data.songname;
	$(".lyr_title h1,#top_song_title").text(songname);
	var singername = song.data.singer[0].name;
	$("#top_song_singer").text(singername);
	var imgurl = "/images/app/order_play_"+app.order+".svg";
	$("#lyr_order").attr("order",app.order)
	$("#lyr_order img").attr("src",imgurl);
}
//更新进度
function updateProgress(ev){
	var songTime = Math.floor(audio.duration);
	app.songTime = songTime;
	var currentTime = Math.floor(audio.currentTime*100)/100;
	app.currentTime = Math.floor(currentTime);
	$("#songTime span").text(formatTime(songTime));
	$("#currentTime span").text(formatTime(Math.floor(currentTime)));
	app.tiomePointRun();
	updateLyr(currentTime);
}
function updateLyr(currentTime){
	var lyrs = $(".lyr-p");
	for (var i = 0; i < lyrs.length; i++) {
		var lyr = lyrs[i];
		var startTime = $(lyr).attr("startTime");
		var second = conSeconds(startTime);
		if(second <currentTime+0.2&&second>currentTime-0.2){
			var divTop = $("#lyr-div").offset().top;
			var pTop = $(lyr).offset().top
			var top =  divTop - pTop + 80;
			$("#lyr-div").css("top",top+"px")
			$(".lyr-p").css("color","#fbfaf8");
			$(lyrs[i]).css("color","#fe0041");
		}
	}
}
function toMiniPlayer(){
	$("#player_normal").show();
	$("#player_super").hide();
	$("#player").css({"background-color":"#fe0041"});
	app.playerStatus = "miniPlayer";
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
		audio.currentTime = time;
	});
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
/*********************/
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
//把形如：01：25的时间转化成秒；
function conSeconds(t){
	var m = t.substring(0, t.indexOf(":"));
	var s = t.substring(t.indexOf(":") + 1);
	m = parseInt(m.replace(/0/, ""));
	var totalt = parseInt(m) * 60 + Math.floor(s*100)/100;
	return totalt;
}