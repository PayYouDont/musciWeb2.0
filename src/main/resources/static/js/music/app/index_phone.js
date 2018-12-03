var app = {};
//顺序播放
app.order = 1;
//普通播放模式
app.playerStatus = "miniPlayer";
$(function(){
	app.songList = songs;
	 $("#carousel").carousel({
         interval: 5000,
         wrap: true
     });
	$('#carright').click(function(){
		$("#carousel").carousel('next');
	});
	$('#carleft').click(function(){
		$("#carousel").carousel('prev');
	});
	$(".carousel-indicators li").click(function(){
		var index = $(this).data("slide-to");
		$("#carousel").carousel(index);
	});
	util.toucher(document.getElementById('carousel'))
	.on('swipeLeft', function(e) {
		$('#carright').click();
	}).on('swipeRight', function(e) {
		$('#carleft').click();
	});
	setPlayer(0);
	//歌曲列表绑定点击事件
	$(".songs").on("click",function(){
		var rowid = $(this).attr("rowid");
		var mid = songs[rowid].data.songmid;
		$("#audio").attr("songmid",mid);
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
	$("#next").off("click").on("click",function(){
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
	// 键盘回车搜索
	$("#search").keydown(function(e) {
		var key = e.which;
		if (key == 13) {
			//searchMusic();
			search();
		}
	})
})
//搜索歌曲
function search(){
	var searchText = $("#search").val();
	if (searchText.trim() == "") {
		return;
	}
	window.location.href = "/music/search?w="+searchText;
}
//切歌
function cutSong(rowid){
	setPlayer(rowid);
	var mid = songs[rowid].data.songmid;
	$("#audio").attr("songmid",mid);
	app.play(mid);
	if(app.playerStatus=="superPlayer"){
		var albummid = songs[rowid].data.albummid;
		changeBG(albummid);
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
	$("title").text(songname+" - "+singername);
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

//播放结束
function audioEnded(){
	if(app.playerStatus == "miniPlayer"){
		$("#next").click();
	}else{
		$("#lyr_next").click();
	}
	
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
	var height = $("#player").height();
	if(height==50){
		$("#player").animate({"height":"100%"},function(){
			$("#player_normal").hide();
			$("#player_super").show();
			var rowid = $("#audio").attr("rowid");
			var row = songs[rowid];
			var songid = row.data.songid;
			var isplay = $("#play").attr("isplay");
			var playerUrl = "../music/player?songid="+songid+"&isplay="+isplay;
			$("#player_super").load(playerUrl);
		});
	}
}