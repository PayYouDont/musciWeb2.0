$(function(){
	var $divs = $(".music-style-main");
	$divs.eq($divs.length-1).css("margin-bottom","50px");
});
function toHotList(div){
	var dissid = $(div).attr("dissid");
	var url = "../music/hotList?disstid="+dissid;
	$("#music_list_table").load(url);
}
function toRadioList(div){
	var radioid = $(div).attr("radioid");
	var url = "../music/radioList?radioid="+radioid;
	$("#music_list_table").load(url);
}