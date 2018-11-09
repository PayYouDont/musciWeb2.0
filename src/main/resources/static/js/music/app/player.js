$(function(){
	var data = lyr.substring(7,lyr.length-1);
	var data = JSON.parse(data);
	var lrc = data.lyric;
	var ly = lrc.replace(/&#(\d+);/g, (str, match) => String.fromCharCode(match));
	console.log(data)
	console.log(ly)
})