package com.example.demo.bean;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class selectBean {

	protected Map<Integer, String> getDepartments = Collections.unmodifiableMap(new LinkedHashMap<Integer, String>() {
		{
			put(0, "未選択");
			put(1, "人事部");
			put(2, "営業部");
			put(3, "IT事業部");
			put(4, "研修生");
			put(5, "インターン");
		}
	});

	protected Map<String, String> getSex = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("men", "男");
			put("women", "女");
		}
	});

	protected static Map<String, String> getSelectColumns = Collections
			.unmodifiableMap(new LinkedHashMap<String, String>() {
				{
					put("default", "未選択");
					put("join_Date,ASC", "入社日昇順");
					put("join_Date,DESC", "入社日降順");
					put("username,ASC", "従業員名昇順");
					put("username,DESC", "従業員名降順");
				}
			});

	protected Date now = new Date();

}
