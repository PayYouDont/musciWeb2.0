
package com.payudon.music.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
* @ClassName: UrlConfig 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年12月10日 上午9:48:50 
*  
*/
@Configuration
@PropertySource(value = {"classpath:config/interface.properties"},
	ignoreResourceNotFound = true,encoding = "utf-8")
@Component
@Data
public class UrlConfig {
	//搜索音乐(参数:{searchData})
	@Value("${searchDataUrl}")
	private String searchDataUrl;
	//用于前端展示的歌单(参数:{date})
	@Value("${songDataUrl}")
	private String songDataUrl;
	//总歌单(无参数)
	@Value("${allData}")
	private String allData;
	//各个分类热歌(参数:{disstid})
	@Value("${hotDataUrl}")
	private String hotDataUrl;
	//流行音乐(无参数)
	@Value("${popularDataUrl}")
	private String popularDataUrl;
	//古典音乐(无参数)
	@Value("${classicalDataUrl}")
	private String classicalDataUrl;
	//纯音乐(无参数)
	@Value("${pureDataUrl}")
	private String pureDataUrl;
	//电台音乐(无参数)
	@Value("${FMDataUrl}")
	private String FMDataUrl;
	//电台详情(参数:{radioid})
	@Value("${raidoDataUrl}")
	private String raidoDataUrl;
	//专辑(参数:{albumId})
	@Value("${albumDataUrl}")
	private String albumDataUrl;
	//歌词(参数:{songid})
	@Value("${lyrDataUrl}")
	private String lyrDataUrl;
	
	public String getSearchDataUrl(String search) {
		return searchDataUrl.replace("{searchData}", search);
	}
	public String getSongDataUrl() {
		Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return songDataUrl.replace("{date}", dateStr);
	}
	public String getHotDataUrl(String disstid) {
		return hotDataUrl.replace("{disstid}", disstid);
	}
	public String getRaidoDataUrl(Integer radioid) {
		return raidoDataUrl.replace("{radioid}", radioid.toString());
	}
	public String getAlbumDataUrl(String albummId) {
		return albumDataUrl.replace("{albummId}", albummId);
	}
	public String getLyrDataUrl(String songid) {
		return lyrDataUrl.replace("{songid}", songid);
	}
}
