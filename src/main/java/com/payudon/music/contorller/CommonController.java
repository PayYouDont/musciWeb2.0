
package com.payudon.music.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.payudon.common.base.controller.BaseController;

/** 
* @ClassName: CommonController 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年11月6日 上午10:10:57 
*  
*/
@RestController
@RequestMapping("/")
public class CommonController extends BaseController{
	
	@GetMapping("index")
	public ModelAndView toIndex() {
		return new ModelAndView("music/index");
				
	}
	
}
