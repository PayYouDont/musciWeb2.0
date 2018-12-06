$(function(){
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
})
function addPlayList(){
	var $helper = $("#helper");
	//添加到播放列表
	if($helper.is(":hidden")){
		return
	}
	var rowid = $helper.attr("rowid");
	var song = songs[rowid];
	song.rowid = rowid;
	var index = app.addPlayList(song);
	$helper.hide();
	if(!app.currentSong&&app.songList.length==1){
		setPlayer(song);
		$("#audio").attr("playid",index);
		app.play();
	}
	if(index==app.songList.length-1){
		layer.msg('已放入播放列表');
	}else{
		layer.msg('该歌曲已在播放列表中');
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