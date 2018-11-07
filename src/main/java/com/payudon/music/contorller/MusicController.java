
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
import com.payudon.music.entity.MusicList.Songlist;
import com.payudon.music.service.MusicService;
import com.payudon.user.entity.User;
import com.payudon.user.service.UserService;
import com.payudon.util.FileUtil;
import com.payudon.util.JsonWrapper;

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
	
	
	@GetMapping("index_pc")
	public ModelAndView indexPC(Model model,Integer id,Integer pageIndex) {
		User user = new User();
		if(id!=null) {
			user = userService.findById(id);
		}
		List<Songlist> list = service.getList().getSonglist();
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
	@GetMapping("index_phone")
	public ModelAndView indexPhone(Model model,Integer id,Integer pageIndex) {
		User user = new User();
		if(id!=null) {
			user = userService.findById(id);
		}
		List<Songlist> list = service.getList().getSonglist();
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
		return new ModelAndView("music/index_phone");
	}
	@GetMapping("list")
	public HashMap<String,Object> list(){
		try {
			return JsonWrapper.successWrapper(service.getList());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper(e.getMessage());
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
}
