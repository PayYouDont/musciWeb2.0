package com.payudon;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payudon.music.config.UrlConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Resource
	private UrlConfig urlConfig;
	
	@Test
	public void contextLoads() {
		String urlStr = urlConfig.getSearchDataUrl();
		urlStr = urlStr.replace("{searchData}", "aaaaaaaaaaaa");
		System.out.println(urlStr);
	}
}
