
$(function() {
	start();
	initPage();
	/* 自定义滚动条 */
	$(".songUL").rollbar({
		zIndex : 80
	});
	$(".checkAll").click(function() {
		var checked = $(this).prop("checked");
		if(checked){
			$(".check input[type='checkbox']").attr("checked",true);
		}else{
			$(".check input[type='checkbox']").attr("checked",false);
		}
	});
	/* 双击播放 */
	$(".songList").dblclick(function() {
		var sid = $(this).find(".start em").html();
		$(".start em[sonN=" + sid + "]").click();
	});
	/* 底部进度条控制 */
	$(".dian").draggable({
		containment : ".pro2",
		drag : function() {
			var l = $(".dian").css("left");
			var le = parseInt(l);
			audio.currentTime = audio.duration * (le / 678);
		}
	});
	/* 音量控制 */
	$(".dian2").draggable({
		containment : ".volControl",
		drag : function() {
			var l = $(".dian2").css("left");
			var le = parseInt(l);
			audio.volume = (le / 80);
		}
	});
	/* 底部播放按钮 */
	$(".playBtn").click(function() {
		var p = $(this).attr("isplay");
		if (p == 0) {
			$(this).css("background-position", "0 -30px");
			$(this).attr("isplay", "1");
		}
		if (p == 1) {
			$(this).css("background-position", "");
			$(this).attr("isplay", "0");
		}
		if (audio.paused){
			var playPromise = audio.play();
			if (playPromise !== undefined){
				playPromise.then(function() {
				    // Automatic playback started!
				  }).catch(function(error) {
				    // Automatic playback failed.
				    // Show a UI element to let the user manually start playback.
					  console.log(error)
				  });
			}
		}else{
			audio.pause();
		}

	});

});

/* 首尾模糊效果 */
function loadBG() {
	// alert();
	// stackBlurImage('canvas1', 'canvas', 60, false);
	var c = document.getElementById("canvas");
	var ctx = c.getContext("2d");
	var img = document.getElementById("canvas1");
	ctx.drawImage(img, 45, 45, 139, 115, 0, 0, 1366, 700);
	stackBlurCanvasRGBA('canvas', 0, 0, 1366, 700, 60);
}
function calcTime(time) {
	var hour;
	var minute;
	var second;
	hour = String(parseInt(time / 3600, 10));
	if (hour.length == 1)
		hour = '0' + hour;
	minute = String(parseInt((time % 3600) / 60, 10));
	if (minute.length == 1)
		minute = '0' + minute;
	second = String(parseInt(time % 60, 10));
	if (second.length == 1)
		second = '0' + second;
	return minute + ":" + second;
}

function audioPlay(ev) {
	$(".iplay").css("background",
			'url("/images/T1oHFEFwGeXXXYdLba-18-18.gif") 0 0');
	$(".playBtn").css("background-position", "0 -30px");
	$(".playBtn").attr("isplay", "1");
}
function audioPause(ev) {
	$(".iplay").css("background", "");
}
function audioEnded(ev) {
	var sid = parseInt(songIndex) + 1;
	$(".start em[sonN=" + sid + "]").click();
}
/************************************************/
function start() {
	/* 点击列表播放按钮 */
	$(".start em").click(function() {
		/* 开始放歌 */
		var sid = $(this).attr("sonn");
		var songImg = $(this).attr("songimg");
		songIndex = sid;
		play(sid,songImg);
	});
}
function play(sid, songImg) {
	var vkey = getVkey(sid);
	var playUrl;
	if(music.HIGHQUALITY==0){
		playUrl = getM4a(sid,vkey);
	}else if(music.HIGHQUALITY==1){
		playUrl = getMp3Pro(sid,vkey);
	}
	// 添加歌曲源
	$("#audio").attr("src",playUrl);
	// 获得音频元素
	audio = document.getElementById("audio");
	/* 显示歌曲总长度 */
	if (audio.paused) {
		var playPromise = audio.play();
		if (playPromise !== undefined){
			playPromise.then(function() {
			    // Automatic playback started!
			  }).catch(function(error) {
			    // Automatic playback failed.
			    // Show a UI element to let the user manually start playback.
				  console.log(error)
				  getMusicStream(playUrl);
			  });
		}
	} else {
		audio.pause();
	}
	audio.addEventListener('timeupdate', updateProgress, false);
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	audio.addEventListener('ended', audioEnded, false);
	
	/* 播放歌词 */
	//var musicId = songListObj[sid].musicId;
	//getReady1(musicId);// 准备播放
	//mPlay();// 显示歌词
	// 对audio元素监听pause事件
	/* 外观改变 */
	/*var html = "";
	html += '<div class="manyou">';
	html += '	<a href="#" class="manyouA">漫游相似歌曲</a>';
	html += '</div>';*/
	$(".start em").css({
		"background" : "",
		"color" : ""
	});
	$(".manyou").remove();
	$(".songList").css("background-color", "#f5f5f5");
	var ems = $(".start em");
	var em;
	for (var i = 0; i < ems.length; i++) {
		var sonN = $(ems[i]).attr("sonn");
		if (sid == sonN) {
			em = ems[i];
			break;
		}
	}
	$(em).css({"background" : 'url("/images/T1X4JEFq8qXXXaYEA_-11-12.gif") no-repeat',
				"color" : "transparent"});
	//$(em).parent().parent().append(html);
	var songLMain = $(em).parent().parent();
	$(".songLMain").removeClass("activity");
	songLMain.addClass("activity");
	/* 底部显示歌曲信息 */
	var songName = $(em).parent().parent().find(".colsn").html();
	var singerName = $(em).parent().parent().find(".colcn").html();
	$(".songName").html(songName);
	$(".songPlayer").html(singerName);
	/* 换右侧图片 */
	var url = "";
	if (songImg) {
		url = 'https://y.gtimg.cn/music/photo_new/T002R300x300M000'+songImg+'.jpg?max_age=2592000';
		$("#canvas1").attr("src", url);
	}
	/*$("#canvas1").load(function() {
		// loadBG();
	});*/
	$(".blur").css("opacity", "0");
	$(".blur").animate({
		opacity : "1"
	}, 1000);
}
//更新歌曲进度条
function updateProgress(ev) {
	/* 显示歌曲总长度 */
	var songTime = calcTime(Math.floor(audio.duration));
	$(".duration").html(songTime);
	/* 显示歌曲当前时间 */
	var curTime = calcTime(Math.floor(audio.currentTime));
	$(".position").html(curTime);
	/* 进度条 */
	var count = $(".progress").width();
	var lef = count * (Math.floor(audio.currentTime) / Math.floor(audio.duration));
	var llef = Math.floor(lef).toString() + "px";
	$(".dian").css("left", llef);
	if (songTime.toString() == curTime.toString()) {
		//circleType[circleIndex].circlePlay();
	}
}
function getVkey(songmid){
	var url ="/music/getVkey";
	var vkey = "";
	$.ajax({
		url:url,
		type:"post",
		data:{songmid:songmid},
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
function getMusicStream(source){
	var url = "/music/getMusicStream?url="+source;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var urlObject = window.URL || window.webkitURL || window;
	        audio.src = urlObject.createObjectURL(this.response);
	        audio.play()
	    }
	};
	xhr.open('POST',url,true);
	xhr.responseType = 'blob';
	xhr.send();
}
//初始分页
function initPage(search) {
	$('#pageLimit').bootstrapPaginator({
		currentPage : music.page.index,// 当前的请求页面。
		totalPages : music.page.count(),// 一共多少页。
		count : "normal",// 页眉的大小。
		bootstrapMajorVersion : 3,// bootstrap的版本要求。
		alignment : "right",
		numberOfPages : 5,//每页显示页数
		itemTexts : function(type, page, current) {
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return page;
			}
		},
		onPageClicked : function(event, originalEvent, type, page) {
			var pageIndex = page;
			var pageSize = music.page.size;
			window.location.href = "/music/toIndex?pageIndex="+pageIndex;
			start();
		}
	});
}