
package com.payudon.music.contorller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.payudon.music.entity.MusicList.Songlist;
import com.payudon.music.service.MusicService;
import com.payudon.user.entity.User;
import com.payudon.user.service.UserService;
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
public class MusicController {
	@Resource
	private UserService userService;
	@Resource
	private MusicService service;
	
	
	@GetMapping("toIndex")
	public ModelAndView toIndex(Model model,Integer id) {
		User user = new User();
		if(id!=null) {
			user = userService.findById(id);
		}
		ArrayList<Songlist> songlist = service.getList().getSonglist();
		model.addAttribute("user", user);
		model.addAttribute("songlist",songlist);
		return new ModelAndView("music/index");
	}
	@GetMapping("list")
	public HashMap<String,Object> list(){
		try {
			return JsonWrapper.successWrapper(service.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonWrapper.failureWrapper(e.getMessage());
		}
	}
	@PostMapping("/getVkey")
	public HashMap<String, Object> getVkey(String songmid) {
		// https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk=5381&jsonpCallback=MusicJsonCallback5068780022221746&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&callback=MusicJsonCallback5068780022221746&uin=0&songmid=000edAg12jLBrN&filename=C400000edAg12jLBrN.m4a&guid=8604243058
		StringBuffer sb = new StringBuffer();
		String urlStr = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk=5381&jsonpCallback=MusicJsonCallback5068780022221746&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&callback=MusicJsonCallback5068780022221746&uin=0&songmid="+songmid+"&filename=C400"+songmid+".m4a&guid=8604243058";
		try {
			sb = service.getVkey(urlStr);
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
}
