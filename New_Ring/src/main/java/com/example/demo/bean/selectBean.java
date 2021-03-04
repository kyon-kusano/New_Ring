package com.example.demo.bean;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class selectBean {


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
