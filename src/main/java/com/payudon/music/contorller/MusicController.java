
package com.payudon.music.contorller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.payudon.common.base.controller.BaseController;
import com.payudon.common.entity.Page;
import com.payudon.music.entity.AllData;
import com.payudon.music.entity.AllData.Content;
import com.payudon.music.entity.AllData.V_hot;
import com.payudon.music.entity.FMData;
import com.payudon.music.entity.FMData.RadioList;
import com.payudon.music.entity.HotSongData;
import com.payudon.music.entity.MusicData.Songlist;
import com.payudon.music.entity.MusicStyleData;
import com.payudon.music.entity.RadioData;
import com.payudon.music.entity.RecommendData;
import com.payudon.music.entity.SearchData;
import com.payudon.music.service.MusicService;
import com.payudon.user.entity.User;
import com.payudon.user.service.UserService;
import com.payudon.util.FileUtil;
import com.payudon.util.JsonWrapper;
import com.payudon.util.ParseUtil;

/** 
* @ClassName: MusicController 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年8月23日 下午2:44:14 
*  
*/
@RestController
@RequestMapping("music")
public class MusicController extends BaseController{
	@Resource
	private UserService userService;
	@Resource
	private MusicService service;
	/**
	 * @Title: indexPC 
	 * @Description: TODO(电脑端首页) 
	 * @param model
	 * @param id
	 * @param pageIndex
	 * @return    设定文件 
	 * @return ModelAndView    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年11月28日 下午3:24:48
	 */
	@GetMapping("index_pc")
	public ModelAndView indexPC(Model model,Integer id,Integer pageIndex) {
		User user = new User();
		if(id!=null) {
			user = userService.findById(id);
		}
		List<Songlist> list = service.getMusicData().getSonglist();
		List<Songlist> songlist = new ArrayList<>();
		Page page = new Page();
		page.setTotal(list.size());
		if(pageIndex!=null) {
			page.setPageIndex(pageIndex);
		}
		for (int i=page.getBegin();i<page.getEnd();i++) {
			songlist.add(list.get(i));
		}
		model.addAttribute("user", user);
		model.addAttribute("songlist",songlist);
		model.addAttribute("page",page);
		return new ModelAndView("music/index_pc");
	}
	/**
	 * @Title: indexPhone 
	 * @Description: TODO(手机端首页) 
	 * @param model
	 * @param id
	 * @param pageIndex
	 * @return    设定文件 
	 * @return ModelAndView    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年11月28日 下午3:25:02
	 */
	@GetMapping("index_phone")
	public ModelAndView indexPhone(Model model,Integer id) {
		User user = new User();
		if(id!=null) {
			user = userService.findById(id);
		}
		ArrayList<Content> focusList = service.getAllData().getFocus().getData().getContent();
		model.addAttribute("user", user);
		model.addAttribute("focusList",focusList);
		return new ModelAndView("music/index_phone");
	}
	//推荐
	@GetMapping("topList")
	public ModelAndView topList(Model model) {
		List<Songlist> songlist = service.getMusicData().getSonglist();
		model.addAttribute("songlist",songlist);
		return new ModelAndView("music/table");
	}
	//流行
	@GetMapping("popularList")
	public ModelAndView popularList(Model model) {
		try {
			MusicStyleData data = service.getPopularData();
			model.addAttribute("songData",data);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
		return new ModelAndView("music/musicStyleTab");
	}
	//古典
	@GetMapping("classicalList")
	public ModelAndView classicalList(Model model) {
		try {
			MusicStyleData data = service.getClassicalData();
			model.addAttribute("songData",data);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
		return new ModelAndView("music/musicStyleTab");
	}
	//纯音乐
	@GetMapping("pureList")
	public ModelAndView pureList(Model model) {
		try {
			MusicStyleData data = service.getPureData();
			model.addAttribute("songData",data);
			return new ModelAndView("music/musicStyleTab");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	@GetMapping("recommendList")
	public ModelAndView recommendList(Model model) {
		try {
			AllData allData = service.getAllData();
			ArrayList<V_hot> v_hot = allData.getRecomPlaylist().getData().getV_hot();
			model.addAttribute("songData",v_hot);
			return new ModelAndView("music/recomTab");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	//电台内容列表
	@GetMapping("radioList")
	public ModelAndView radioList(Model model,Integer radioid) {
		try {
			RadioData radioData = service.getRadioData(radioid);
			List<Songlist> songlist = ParseUtil.parseSonglist(radioData);
			model.addAttribute("songlist",songlist);
			return new ModelAndView("music/table");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	@GetMapping("FMList")
	public ModelAndView FMList(Model model) {
		try {
			FMData data = service.getFMData();
			ArrayList<RadioList> radioList = data.getData().getData().getGroupList().get(0).getRadioList();
			model.addAttribute("songData",radioList);
			return new ModelAndView("music/FMTab");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	//热歌列表
	@GetMapping("hotList")
	public ModelAndView hotList(Model model,String disstid) {
		try {
			HotSongData hotSongData = service.getHotSongData(disstid);
			List<Songlist> songlist = service.getSonglist(hotSongData);
			model.addAttribute("songlist",songlist);
			return new ModelAndView("music/table");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	@GetMapping("albumList")
	public ModelAndView albumList(Model model,String albummid) {
		try {
			RecommendData recommendData = service.getRecommendData(albummid);
			List<Songlist> songlist = ParseUtil.parseSonglist(recommendData);
			model.addAttribute("songlist",songlist);
			return new ModelAndView("music/table");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ModelAndView("music/error");
		}
	}
	@GetMapping("list")
	public HashMap<String,Object> list(){
		try {
			return JsonWrapper.successWrapper(service.getAllData());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper(e.getMessage());
		}
	}
	/**
	 * @Title: player 
	 * @Description: TODO(播放器页面) 
	 * @param model
	 * @param rowid
	 * @param isplay
	 * @return    设定文件 
	 * @return ModelAndView    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年11月28日 下午3:25:21
	 */
	@GetMapping("player")
	public ModelAndView player(Model model,String songid,String isplay) {
		try {	
			String lyr = service.getlyr(songid).toString();
			System.out.println(lyr);
			model.addAttribute("lyr", lyr);
		} catch (Exception e) {
			logger.error("获取歌词error",e);
		}
		if(isplay==null) {
			isplay = "true";
		}
		model.addAttribute("isplay", isplay);
		return new ModelAndView("music/player");
	}
	/**
	 * @Title: search 
	 * @Description: TODO(搜索页面) 
	 * @param model
	 * @param w
	 * @return    设定文件 
	 * @return ModelAndView    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年11月28日 下午3:26:00
	 */
	@GetMapping("search")
	public ModelAndView search(Model model,String w) {
		try {
			SearchData searchData = service.getSearchData(w);
			List<Songlist> songlist = ParseUtil.parseSonglist(searchData);
			model.addAttribute("songlist",songlist);

			return new ModelAndView("music/table");
		} catch (Exception e) {
			logger.error("搜索error",e);
			return new ModelAndView("music/error");
		}
	}
	@PostMapping("getVkey")
	public HashMap<String, Object> getVkey(String songmid) {
		StringBuffer sb = new StringBuffer();
		String urlStr = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk=5381&jsonpCallback=MusicJsonCallback5068780022221746&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&callback=MusicJsonCallback5068780022221746&uin=0&songmid="+songmid+"&filename=C400"+songmid+".m4a&guid=8604243058";
		try {
			sb = service.getVkey(urlStr);
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
	@PostMapping("getMusicStream")
	public void getMusicStream(HttpServletRequest request,HttpServletResponse response,String url){
		try {
			url = "http://www.jq22.com/demo/jquery-APlayer20160104/music/Sugar.mp3";
			InputStream in = service.getMusicStream(url);
			response.setHeader("Content-Type", "audio/mpeg");
			OutputStream out =response.getOutputStream();
			FileUtil.wirteFile(in, out);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * @Title: getlyr 
	 * @Description: TODO(歌词) 
	 * @param songid
	 * @return    设定文件 
	 * @return HashMap<String,Object>    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年11月28日 下午3:26:35
	 */
	@GetMapping("getlyr")
	public HashMap<String, Object> getlyr(String songid) {
		try {
			return JsonWrapper.successWrapper(service.getlyr(songid));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
}
