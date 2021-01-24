package com.example.demo.bean;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface selectBean {

	static Map<String, String> getDepartments = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("0", "未選択");
			put("1", "人事部");
			put("2", "営業部");
			put("3", "IT事業部");
			put("4", "研修生");
			put("5", "インターン");
		}
	});
	static Map<String, String> getSex = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("men", "男");
			put("women", "女");
		}
	});

	Date now = new Date();

}
