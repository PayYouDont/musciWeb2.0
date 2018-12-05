var app = {};
//顺序播放
app.order = 1;
//普通播放模式
app.playerStatus = "miniPlayer";
app.songList = new Array();
app.currentSong;
app.playid
$(function(){
	app.currentSong = songs[0];
	$('#myModal').on('show.bs.modal', function () {
		$(this).css({
			"top":"auto",
			"z-index":2000
		})
		$(".modal-dialog").css("margin",0)
	})
	//图片轮播
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
	//歌曲列表绑定点击事件
	$(".songs").on("click",function(){
		var rowid = $(this).attr("rowid");
		var song = songs[rowid];
		song.rowid = rowid;
		var playid = app.addPlayList(song);
		$("#audio").attr("playid",playid);
		app.currentSong = song;
		setPlayer(song);
		app.play();
	});
	//分享添加功能绑定点击事件
	$(".more").off("click").on("click",function(e){
		var $td = $(this).parent().prev();
		var rowid = $(this).parent().parent().attr("rowid");
		$("#helper").attr("rowid",rowid);
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
		if(app.songList.length==0){
			layer.msg("没有可播放的歌曲")
			return;
		}
		audio.paused?audio.play():audio.pause();
	});
	//下一曲
	$("#next").off("click").on("click",function(){
		if(app.songList.length>0){
			var playid = app.getNextSongId();
			var song = app.songList[playid];
			$("#audio").attr("playid",playid);
			cutSong(song);
		}else{
			setPlayer();
		}
	});
	// 键盘回车搜索
	$("#search").keydown(function(e) {
		var key = e.which;
		if (key == 13) {
			search();
		}
	});
	$('#myModal').on('show.bs.modal', function (e) {
		initPlayListTab();
	});
})
//初始化播放列表
function initPlayListTab(){
	var orderText = "顺序播放";
	if(app.order==2){
		orderText = "单曲循环";
	}else if(app.order==3){
		orderText = "随机播放";
	}
	var countText = "("+app.songList.length+"首)";
	var text = orderText + countText;
	if(app.order==2){
		text = orderText;
	}
	$("#table-count").text(text);
	var html = '';
	for(var i in app.songList){
		var song = app.songList[i].data;
		var songname = song.songname;
		var singername = song.singer[0].name;
		var tr = '<tr>';
		var img = "";
		if(song.songid==app.currentSong.data.songid){
			tr = '<tr class="play-activ">';
			img = '	<img src="../images/app/note.svg">';
		}
		html += tr+'<td></td>'+
				'<td>'+songname+' - '+singername+img+
					'</td>'+
					'<td>'+
					'	<img index="'+i+'" src="../images/app/close.svg" width="15px" onclick="removeToPlayList(this)">'+
					'</td></tr>';
	}
	$("#songPlayListTbody").html(html);
}
app.getPrevSongId = function(){
	if(this.songList.length==0){
		return 0;
	}
	var playid = $("#audio").attr("playid");
	if(this.order==1){
		if(playid==0){
			playid = this.songList.length-1;
		}else{
			playid--;
		}
	}else if(this.order==3){
		playid = Math.floor(Math.random()*this.songList.length);
	}
	return playid;
}
app.getNextSongId = function(){
	if(this.songList.length==0){
		return 0;
	}
	var playid = $("#audio").attr("playid");
	if(this.order==1){
		playid++;
		if(playid==this.songList.length){
			playid = 0;
		}
	}else if(this.order==3){
		playid = Math.floor(Math.random()*this.songList.length);
	}
	return playid;
}
function addPlayList(){
	var $helper = $("#helper");
	//添加到播放列表
	if($helper.is(":hidden")){
		return
	}
	var rowid = $helper.attr("rowid");
	var song = songs[rowid];
	var result = app.addPlayList(song);
	$helper.hide();
	if(result){
		layer.msg('已放入播放列表');
	}else{
		layer.msg('该歌曲已在播放列表中');
	}
}
function removeToPlayList(img){
	var index = $(img).attr("index");
	var playid = $("#audio").attr("playid");
	app.songList.splice(index, 1);
	initPlayListTab();
	if(index==playid){
		$("#next").click();
	}
}
//添加至播放列表
app.addPlayList = function(song){
	for(var i in app.songList){
		if(app.songList[i].data.songid==song.data.songid){//如果已经存在
			return i;
		}
	}
	app.songList.push(song);
	return app.songList.length-1;
}
//点击菜单
function clickModal(){
	$('#menu').click();
}
//搜索歌曲
function search(){
	var searchText = $("#search").val();
	if (searchText.trim() == "") {
		return;
	}
	window.location.href = "/music/search?w="+searchText;
}
//切歌
function cutSong(song){
	setPlayer(song);
	app.play();
	if(app.playerStatus=="superPlayer"){
		var albummid = song.data.albummid;
		changeBG(albummid);
	}
	if($("#myModal").is(":visible")){
		initPlayListTab();
	}
}
//设置播放器内容
function setPlayer(song){
	if(song){
		app.currentSong = song;
		$("#audio").attr("rowid",song.rowid);
		var songname = song.data.songname;
		$("#player_songname").text(songname);
		var singername = song.data.singer[0].name;
		$("#player_singername").text(singername);
		var  albummid = song.data.albummid;
		var imgUrl = getCover(albummid);
		$("#singer_cover").attr("src",imgUrl);
		$("title").text(songname+" - "+singername);
	}else{
		app.currentSong = null;
		$("#audio").attr("rowid","");
		$("#player_songname").text("无歌曲播放");
		$("#player_singername").text("播放列表为空");
		var imgUrl = "../images/app/note.svg";
		$("#singer_cover").attr("src",imgUrl);
		$("title").text("You - 音乐");
		isPlay(false);
		audio.paused?audio.play():audio.pause();
	}
}
//获取歌曲的相关信息并播放
app.play = function(){
	var mid = this.currentSong.data.songmid;
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
	var rowid = $("#audio").attr("rowid");
	if(rowid!=""&&height==50){
		$("#player").animate({"height":"100%"},function(){
			$("#player_normal").hide();
			$("#player_super").show();
			var row = songs[rowid];
			var songid = row.data.songid;
			var isplay = $("#play").attr("isplay");
			var playerUrl = "../music/player?songid="+songid+"&isplay="+isplay;
			$("#player_super").load(playerUrl);
		});
	}
}