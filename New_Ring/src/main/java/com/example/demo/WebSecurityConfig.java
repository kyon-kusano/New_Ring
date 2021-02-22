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
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.example.demo.service.base.BaseService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BaseService baseService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ここではユーザーの持っている権限によってみれるページを制限しています
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated();

		http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login-error")
				.defaultSuccessUrl("/", true).usernameParameter("email").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")).deleteCookies("JSESSIONID")
				.invalidateHttpSession(true).permitAll();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(baseService).passwordEncoder(passwordEncoder());

		if (baseService.findAllList().isEmpty()) {
			baseService.createDepartment("未選択");
			baseService.createDepartment("人事部");
			baseService.createDepartment("IT事業部");
			baseService.createDepartment("営業部");
			baseService.createDepartment("研修生");
			baseService.createDepartment("インターン");
			baseService.registerAdmin("admin", "secret", "admin@localhost", new Date(), "男",
					baseService.findDepartment(2), "xxx-xxxx-xxxx",
					baseService.createAddress("751-0832", "山口県下関市生野町", "一丁目4番40号"),
					baseService.createDetails("images/nonImage.png", ""), new Date(), null, null);

		}

	}

	@Override

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**", "/js/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

}

//insert into
//employee(
// id, username, email, password,
// enabled,is_account_non_expired,is_account_non_locked,is_credentials_non_expired,
// join_date,birthday,department_id,sex)values
// (2,'大野智','sa1tosi@ono','password',true,true,true,true,'2020-1-2','1999-1-2',2,'男'),
// (3,'松本潤','j1yun@matsumoto','password',true,true,true,true,'2019-11-2','1962-1-2',2,'男'),
// (4,'二宮和也','1kazunari@ninomiya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'男'),
// (5,'相葉雅紀','1masaki@ninomiya','password',true,true,true,true,'2021-1-2','1980-1-2',4,'男'),
// (6,'玉森裕太','1yuta@tamamori','password',true,true,true,true,'2021-1-2','1999-1-2',5,'男'),
// (7,'錦戸亮','1ryo@nishikido','password',true,true,true,true,'2019-1-2','1932-1-2',5,'男'),
// (8,'錦織圭','1kei@nishikori','password',true,true,true,true,'2020-1-2','1992-1-2',5,'男'),
// (9,'王貞治','1sadaharu@ou','password',true,true,true,true,'2020-4-7','1972-1-2',3,'男'),
// (10,'マイケルジャクソン','1jakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'男'),
// (11,'ウルトラマン','1uru@toraman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'男'),
// (12,'小池栄子','1eiko@koike','password',true,true,true,true,'2020-4-2','1982-1-2',6,'男'),
// (13,'大野智','sa1tosie@ono','password',true,true,true,true,'2020-1-2','1999-1-2',2,'女'),
// (14,'松本潤','j1yun@matsumoteo','password',true,true,true,true,'2019-11-2','1962-1-2',2,'女'),
// (15,'二宮和也','1kazunari@ninomieya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'女'),
// (16,'相葉雅紀','1masaki@ninomiyea','password',true,true,true,true,'2021-1-2','1980-1-2',4,'女'),
// (17,'玉森裕太','1yuta@tamamorie','password',true,true,true,true,'2021-1-2','1999-1-2',5,'女'),
// (18,'錦戸亮','1ryo@nishikidoew','password',true,true,true,true,'2019-1-2','1932-1-2',5,'女'),
// (19,'錦織圭','1kei@nishikorwei','password',true,true,true,true,'2020-1-2','1992-1-2',5,'女'),
// (20,'王貞治','1sadaharu@oue','password',true,true,true,true,'2020-4-7','1972-1-2',3,'女'),
// (21,'マイケルジャクソン','1jakcsodn@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'女'),
// (22,'ウルトラマン','1uru@tosraman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'女'),
// (23,'小池栄子','1eiko@koikwse','password',true,true,true,true,'2020-4-2','1982-1-2',6,'女'),
// (24,'二宮和也','1kazfunari@nineomiya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'男'),
// (25,'相葉雅紀','1masaki@ninegeeomiya','password',true,true,true,true,'2021-1-2','1980-1-2',4,'男'),
// (26,'玉森裕太','1yuta@teeamamoriege','password',true,true,true,true,'2021-1-2','1999-1-2',5,'男'),
// (27,'錦戸亮','1ryo@nishikerido','password',true,true,true,true,'2019-1-2','1932-1-2',5,'男'),
// (28,'錦織圭','1kei@nishiskoeri','password',true,true,true,true,'2020-1-2','1992-1-2',5,'男'),
// (29,'王貞治','1sadahareru@ou','password',true,true,true,true,'2020-4-7','1972-1-2',3,'男'),
// (30,'マイケルジャクソン','1jgrakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'男'),
// (31,'ウルトラマン','1uru@torgeaman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'男'),
// (32,'小池栄子','1eiko@koikeeg','password',true,true,true,true,'2020-4-2','1982-1-2',6,'男'),
// (33,'大野智','sa1tosie@onoeerr','password',true,true,true,true,'2020-1-2','1999-1-2',2,'女'),
// (34,'松本潤','j1yun@matsumoegteo','password',true,true,true,true,'2019-11-2','1962-1-2',2,'女'),
// (35,'二宮和也','1kazunari@ninregomieya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'女'),
// (36,'相葉雅紀','1masaki@ninomiyreea','password',true,true,true,true,'2021-1-2','1980-1-2',4,'女'),
// (37,'玉森裕太','1yuta@tamamoriere','password',true,true,true,true,'2021-1-2','1999-1-2',5,'女'),
// (38,'錦戸亮','1ryo@nishikidoeww','password',true,true,true,true,'2019-1-2','1932-1-2',5,'女'),
// (39,'錦織圭','1kei@nishikorwtwei','password',true,true,true,true,'2020-1-2','1992-1-2',5,'女'),
// (40,'王貞治','1sadaharwtu@oue','password',true,true,true,true,'2020-4-7','1972-1-2',3,'女'),
// (41,'マイケルジャクソン','1jrtakcsodn@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'女'),
// (42,'ウルトラマン','1uru@tosrrgaman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'女'),
// (43,'小池栄子','1eiko@koikwsgee','password',true,true,true,true,'2020-4-2','1982-1-2',6,'女'),
// (44,'二宮和也','1kazunari@ninhgewromiya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'男'),
// (45,'相葉雅紀','1masaki@ninomierhya','password',true,true,true,true,'2021-1-2','1980-1-2',4,'男'),
// (46,'玉森裕太','1yuta@ewrgtamamori','password',true,true,true,true,'2021-1-2','1999-1-2',5,'男'),
// (47,'錦戸亮','1ryo@nishikergido','password',true,true,true,true,'2019-1-2','1932-1-2',5,'男'),
// (48,'錦織圭','1kei@nishikoregwi','password',true,true,true,true,'2020-1-2','1992-1-2',5,'男'),
// (49,'王貞治','1sadahaehru@ou','password',true,true,true,true,'2020-4-7','1972-1-2',3,'男'),
// (50,'マイケルジャクソン','1hewjakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'男'),
// (51,'ウルトラマン','1uru@torehwhaman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'男'),
// (52,'小池栄子','1eiko@ewrhkoike','password',true,true,true,true,'2020-4-2','1982-1-2',6,'男'),
// (53,'大野智','sa1tosie@ewhono','password',true,true,true,true,'2020-1-2','1999-1-2',2,'女'),
// (54,'松本潤','j1yun@matsuehmoteo','password',true,true,true,true,'2019-11-2','1962-1-2',2,'女'),
// (55,'二宮和也','1kazunari@erhninomieya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'女'),
// (56,'相葉雅紀','1masaki@ninohewmiyea','password',true,true,true,true,'2021-1-2','1980-1-2',4,'女'),
// (57,'玉森裕太','1yuta@tamamoewhrrie','password',true,true,true,true,'2021-1-2','1999-1-2',5,'女'),
// (58,'錦戸亮','1ryo@nishikidoeeew','password',true,true,true,true,'2019-1-2','1932-1-2',5,'女'),
// (59,'錦織圭','1kei@nishikorwewhei','password',true,true,true,true,'2020-1-2','1992-1-2',5,'女'),
// (60,'王貞治','1sadaharuerg@oue','password',true,true,true,true,'2020-4-7','1972-1-2',3,'女'),
// (61,'マイケルジャクソン','1jakøˆøcsodn@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'女'),
// (62,'ウルトラマン','1uru@tosram∆øan','password',true,true,true,true,'2020-6-2','1962-12-2',6,'女'),
// (63,'小池栄子','1eiko@koikwseolbi','password',true,true,true,true,'2020-4-2','1982-1-2',6,'女'),
// (64,'二宮和也','1kazunari@ninomiefrlnoya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'男'),
// (65,'相葉雅紀','1masaki@ninwefomiya','password',true,true,true,true,'2021-1-2','1980-1-2',4,'男'),
// (66,'玉森裕太','1yuta@tamamwrgori','password',true,true,true,true,'2021-1-2','1999-1-2',5,'男'),
// (67,'錦戸亮','1ryo@nisrgvwhikido','password',true,true,true,true,'2019-1-2','1932-1-2',5,'男'),
// (68,'錦織圭','1kei@nishikrewgori','password',true,true,true,true,'2020-1-2','1992-1-2',5,'男'),
// (69,'王貞治','1sadaharrgequ@ou','password',true,true,true,true,'2020-4-7','1972-1-2',3,'男'),
// (70,'マイケルジャクソン','1jakerrrrrgcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'男'),
// (71,'ウルトラマン','1uru@toraegrrman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'男'),
// (72,'小池栄子','1eiko@wregvkoike','password',true,true,true,true,'2020-4-2','1982-1-2',6,'男'),
// (73,'大野智','sa1tosieebq@ono','password',true,true,true,true,'2020-1-2','1999-1-2',2,'女'),
// (74,'松本潤','j1yun@matsebumoteo','password',true,true,true,true,'2019-11-2','1962-1-2',2,'女'),
// (75,'二宮和也','1kazunariebeb@ninomieya','password',true,true,true,true,'2020-3-2','1990-1-2',3,'女'),
// (76,'相葉雅紀','1masaki@ninobebemiyea','password',true,true,true,true,'2021-1-2','1980-1-2',4,'女'),
// (77,'玉森裕太','1yuta@tamamewbworie','password',true,true,true,true,'2021-1-2','1999-1-2',5,'女'),
// (78,'錦戸亮','1ryo@nishikidwrthoew','password',true,true,true,true,'2019-1-2','1932-1-2',5,'女'),
// (79,'錦織圭','1kei@nishiwrthhwrkorwei','password',true,true,true,true,'2020-1-2','1992-1-2',5,'女'),
// (80,'王貞治','1sadahawrethwhru@oue','password',true,true,true,true,'2020-4-7','1972-1-2',3,'女'),
// (81,'マイケルジャクソン','1jawrhthkcsodn@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6,'女'),
// (82,'ウルトラマン','1uru@toswetrhwhraman','password',true,true,true,true,'2020-6-2','1962-12-2',6,'女'),
// (83,'小池栄子','1eiko@kthwwoikwse','password',true,true,true,true,'2020-4-2','1982-1-2',6,'女');
//
// insert into employee_authorities(
// employee_id,authorities)values
// (2,'ROLE_USER'),
// (3,'ROLE_USER'),
// (4,'ROLE_USER'),
// (5,'ROLE_USER'),
// (6,'ROLE_USER'),
// (7,'ROLE_USER'),
// (8,'ROLE_USER'),
// (9,'ROLE_USER'),
// (10,'ROLE_USER'),
// (11,'ROLE_USER'),
// (12,'ROLE_USER'),
// (13,'ROLE_USER'),
// (14,'ROLE_USER'),
// (15,'ROLE_USER'),
// (16,'ROLE_USER'),
// (17,'ROLE_USER'),
// (18,'ROLE_USER'),
// (19,'ROLE_USER'),
// (20,'ROLE_USER'),
// (21,'ROLE_USER'),
// (22,'ROLE_USER'),
// (23,'ROLE_USER'),
// (24,'ROLE_USER'),
// (25,'ROLE_USER'),
// (26,'ROLE_USER'),
// (27,'ROLE_USER'),
// (28,'ROLE_USER'),
// (29,'ROLE_USER'),
// (30,'ROLE_USER'),
// (31,'ROLE_USER'),
// (32,'ROLE_USER'),
// (33,'ROLE_USER'),
// (34,'ROLE_USER'),
// (35,'ROLE_USER'),
// (36,'ROLE_USER'),
// (37,'ROLE_USER'),
// (38,'ROLE_USER'),
// (39,'ROLE_USER'),
// (40,'ROLE_USER'),
// (41,'ROLE_USER'),
// (42,'ROLE_USER'),
// (43,'ROLE_USER'),
// (44,'ROLE_USER'),
// (45,'ROLE_USER'),
// (46,'ROLE_USER'),
// (47,'ROLE_USER'),
// (48,'ROLE_USER'),
// (49,'ROLE_USER'),
// (50,'ROLE_USER'),
// (51,'ROLE_USER'),
// (52,'ROLE_USER'),
// (53,'ROLE_USER'),
// (54,'ROLE_USER'),
// (55,'ROLE_USER'),
// (56,'ROLE_USER'),
// (57,'ROLE_USER'),
// (58,'ROLE_USER'),
// (59,'ROLE_USER'),
// (60,'ROLE_USER'),
// (61,'ROLE_USER'),
// (62,'ROLE_USER'),
// (63,'ROLE_USER'),
// (64,'ROLE_USER'),
// (65,'ROLE_USER'),
// (66,'ROLE_USER'),
// (67,'ROLE_USER'),
// (68,'ROLE_USER'),
// (69,'ROLE_USER'),
// (70,'ROLE_USER'),
// (71,'ROLE_USER'),
// (72,'ROLE_USER'),
// (73,'ROLE_USER'),
// (74,'ROLE_USER'),
// (75,'ROLE_USER'),
// (76,'ROLE_USER'),
// (77,'ROLE_USER'),
// (78,'ROLE_USER'),
// (79,'ROLE_USER'),
// (80,'ROLE_USER'),
// (81,'ROLE_USER'),
// (82,'ROLE_USER'),
// (83,'ROLE_USER');
//
// insert into address(
// id,post_number,address1,address2)values
// (2,'000-0000','宇宙','地球'),
// (3,'000-0000','宇宙','地球'),
// (4,'000-0000','宇宙','地球'),
// (5,'000-0000','宇宙','地球'),
// (6,'000-0000','宇宙','地球'),
// (7,'000-0000','宇宙','地球'),
// (8,'000-0000','宇宙','地球'),
// (9,'000-0000','宇宙','地球'),
// (10,'000-0000','宇宙','地球'),
// (11,'000-0000','宇宙','地球'),
// (12,'000-0000','宇宙','地球'),
// (13,'000-0000','宇宙','地球'),
// (14,'000-0000','宇宙','地球'),
// (15,'000-0000','宇宙','地球'),
// (16,'000-0000','宇宙','地球'),
// (17,'000-0000','宇宙','地球'),
// (18,'000-0000','宇宙','地球'),
// (19,'000-0000','宇宙','地球'),
// (20,'000-0000','宇宙','地球'),
// (21,'000-0000','宇宙','地球'),
// (22,'000-0000','宇宙','地球'),
// (23,'000-0000','宇宙','地球'),
// (24,'000-0000','宇宙','地球'),
// (25,'000-0000','宇宙','地球'),
// (26,'000-0000','宇宙','地球'),
// (27,'000-0000','宇宙','地球'),
// (28,'000-0000','宇宙','地球'),
// (29,'000-0000','宇宙','地球'),
// (30,'000-0000','宇宙','地球'),
// (31,'000-0000','宇宙','地球'),
// (32,'000-0000','宇宙','地球'),
// (33,'000-0000','宇宙','地球'),
// (34,'000-0000','宇宙','地球'),
// (35,'000-0000','宇宙','地球'),
// (36,'000-0000','宇宙','地球'),
// (37,'000-0000','宇宙','地球'),
// (38,'000-0000','宇宙','地球'),
// (39,'000-0000','宇宙','地球'),
// (40,'000-0000','宇宙','地球'),
// (41,'000-0000','宇宙','地球'),
// (42,'000-0000','宇宙','地球'),
// (43,'000-0000','宇宙','地球'),
// (44,'000-0000','宇宙','地球'),
// (45,'000-0000','宇宙','地球'),
// (46,'000-0000','宇宙','地球'),
// (47,'000-0000','宇宙','地球'),
// (48,'000-0000','宇宙','地球'),
// (49,'000-0000','宇宙','地球'),
// (50,'000-0000','宇宙','地球'),
// (51,'000-0000','宇宙','地球'),
// (52,'000-0000','宇宙','地球'),
// (53,'000-0000','宇宙','地球'),
// (54,'000-0000','宇宙','地球'),
// (55,'000-0000','宇宙','地球'),
// (56,'000-0000','宇宙','地球'),
// (57,'000-0000','宇宙','地球'),
// (58,'000-0000','宇宙','地球'),
// (59,'000-0000','宇宙','地球'),
// (60,'000-0000','宇宙','地球'),
// (61,'000-0000','宇宙','地球'),
// (62,'000-0000','宇宙','地球'),
// (63,'000-0000','宇宙','地球'),
// (64,'000-0000','宇宙','地球'),
// (65,'000-0000','宇宙','地球'),
// (66,'000-0000','宇宙','地球'),
// (67,'000-0000','宇宙','地球'),
// (68,'000-0000','宇宙','地球'),
// (69,'000-0000','宇宙','地球'),
// (70,'000-0000','宇宙','地球'),
// (71,'000-0000','宇宙','地球'),
// (72,'000-0000','宇宙','地球'),
// (73,'000-0000','宇宙','地球'),
// (74,'000-0000','宇宙','地球'),
// (75,'000-0000','宇宙','地球'),
// (76,'000-0000','宇宙','地球'),
// (77,'000-0000','宇宙','地球'),
// (78,'000-0000','宇宙','地球'),
// (79,'000-0000','宇宙','地球'),
// (80,'000-0000','宇宙','地球'),
// (81,'000-0000','宇宙','地球'),
// (82,'000-0000','宇宙','地球'),
// (83,'000-0000','宇宙','地球');
