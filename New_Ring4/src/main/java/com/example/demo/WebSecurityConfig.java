package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.base.BaseService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BaseService baseService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ここではユーザーの持っている権限によってみれるページを制限しています
		http.authorizeRequests().antMatchers("/login").permitAll() // ログイン画面 全てのユーザーがアクセス可
				.antMatchers("/admin/**").hasRole("ADMIN") // 管理画面 ADMIN"権限がないとアクセスできない
				.anyRequest().authenticated(); // 全てのURLリクエストは認証されているユーザーしかアクセスできないという記述です

		// 認証に関わるパラメータを指定
		http.formLogin().loginProcessingUrl("/login").loginPage("/login") // ログインの処理をするURL ログイン画面のURL
				.failureUrl("/login-error") // ログインに失敗した時のURL
				.defaultSuccessUrl("/", true) // ログインが成功した時のURL(ここではindex.htmlなので"/"のみで指定可) false :
												// (認証されてなくて一度ログイン画面に飛ばされても)ログインしたら指定したURLに飛んでくれる
				.usernameParameter("email").passwordParameter("password") // ログイン画面のhtmlのinputのname属性を見に行っている
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウトのURL
				.logoutSuccessUrl("/") // ログアウト成功したあとのURL
				.deleteCookies("JSESSIONID") // ログアウトしたら cookieの JSESSIONID を削除
				.invalidateHttpSession(true).permitAll(); // ログアウトしたらセッションを無効にする
		http.sessionManagement().invalidSessionUrl("/signin");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(baseService).passwordEncoder(passwordEncoder());
//		if (baseService.findAllDepartments().isEmpty()) {
//			baseService.registerDepartment(1, "未選択");
//			baseService.registerDepartment(2, "人事部");
//			baseService.registerDepartment(3, "営業部");
//			baseService.registerDepartment(4, "IT事業部");
//			baseService.registerDepartment(5, "研修生");
//			baseService.registerDepartment(6, "インターン");
//		}
		if (baseService.findAllList().isEmpty()) {
			baseService.registerAdmin("admin", "secret", "admin@localhost", new Date(), "男",
					baseService.findDepartment(1), "xxx-xxxx-xxxx", "東京都", new Date(), null, null);
		}

	}

	@Override
	// ここではSpringSecurityの制限を無視してほしい場所の指定
	// 例： 静的ファイルなどを置いている場所
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**", "/js/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

//insert into employee(
//		id, username, email, password, enabled,is_account_non_expired,is_account_non_locked,is_credentials_non_expired, join_date,birthday,department)values
//		(32,'大野智','sa1tosi@ono','password',true,true,true,true,'2020-1-2','1999-1-2','人事部'),
//		(33,'松本潤','j1yun@matsumoto','password',true,true,true,true,'2019-11-2','1962-1-2','人事部'),
//		(34,'二宮和也','1kazunari@ninomiya','password',true,true,true,true,'2020-3-2','1990-1-2','営業部'),
//		(35,'相葉雅紀','1masaki@ninomiya','password',true,true,true,true,'2021-1-2','1980-1-2','IT事業部'),
//		(36,'玉森裕太','1yuta@tamamori','password',true,true,true,true,'2021-1-2','1999-1-2','インターン'),
//		(37,'錦戸亮','1ryo@nishikido','password',true,true,true,true,'2019-1-2','1932-1-2','研修生'),
//		(38,'錦織圭','1kei@nishikori','password',true,true,true,true,'2020-1-2','1992-1-2','研修生'),
//		(39,'王貞治','1sadaharu@ou','password',true,true,true,true,'2020-4-7','1972-1-2','営業部'),
//		(40,'マイケルジャクソン','1jakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2','人事部'),
//		(41,'ウルトラマン','1uru@toraman','password',true,true,true,true,'2020-6-2','1962-12-2','研修生'),
//		(42,'小池栄子','1eiko@koike','password',true,true,true,true,'2020-4-2','1982-1-2','営業部')