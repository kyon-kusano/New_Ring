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

		if (baseService.findAllList().isEmpty()) {
			baseService.registerAdmin("admin", "secret", "admin@localhost", new Date(), "男", "IT事業部", "xxx-xxxx-xxxx",
					"東京都", new Date(), null, null);
		}
	}

	@Override
	// ここではSpringSecurityの制限を無視してほしい場所の指定
	// 例： 静的ファイルなどを置いている場所
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/image/**", "/js/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}