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
    protected void configure (HttpSecurity http) throws Exception {
        // ここではユーザーの持っている権限によってみれるページを制限しています
        http.authorizeRequests ().antMatchers ("/login").permitAll ().antMatchers ("/admin/**").hasRole ("ADMIN")
                .anyRequest ().authenticated ();
        
        http.formLogin ().loginProcessingUrl ("/login").loginPage ("/login").failureUrl ("/login-error")
                .defaultSuccessUrl ("/", true).usernameParameter ("email").passwordParameter ("password").and ()
                .logout ().logoutRequestMatcher (new AntPathRequestMatcher ("/logout**")).deleteCookies ("JSESSIONID")
                .invalidateHttpSession (true).permitAll ();
        
    }
    
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (baseService).passwordEncoder (passwordEncoder ());
        
        if (baseService.findAllList ().isEmpty ()) {
            baseService.createDepartment ("未選択");
            baseService.createDepartment ("人事部");
            baseService.createDepartment ("IT事業部");
            baseService.createDepartment ("営業部");
            baseService.createDepartment ("研修生");
            baseService.createDepartment ("インターン");
            baseService.registerAdmin ("admin", "secret", "admin@localhost", new Date (), "男",
                    baseService.findDepartment (2), "xxx-xxxx-xxxx",
                    baseService.createAddress ("751-0832", "山口県下関市生野町", "一丁目4番40号"), new Date (), null, null);
            
        }
        
    }
    
    @Override
    
    public void configure (WebSecurity web) throws Exception {
        web.ignoring ().antMatchers ("/css/**", "/images/**", "/js/**");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder ();
    }
    
}
// insert into
//
// employee(
// id, username, email, password,
// enabled,is_account_non_expired,is_account_non_locked,is_credentials_non_expired,
// join_date,birthday,department_id)values
// --
// (32,'大野智','sa1tosi@ono','password',true,true,true,true,'2020-1-2','1999-1-2',2),
// --
// (33,'松本潤','j1yun@matsumoto','password',true,true,true,true,'2019-11-2','1962-1-2',2),
// --
// (34,'二宮和也','1kazunari@ninomiya','password',true,true,true,true,'2020-3-2','1990-1-2',3),
// --
// (35,'相葉雅紀','1masaki@ninomiya','password',true,true,true,true,'2021-1-2','1980-1-2',4),
// --
// (36,'玉森裕太','1yuta@tamamori','password',true,true,true,true,'2021-1-2','1999-1-2',5),
// --
// (37,'錦戸亮','1ryo@nishikido','password',true,true,true,true,'2019-1-2','1932-1-2',5),
// --
// (38,'錦織圭','1kei@nishikori','password',true,true,true,true,'2020-1-2','1992-1-2',5),
// --
// (39,'王貞治','1sadaharu@ou','password',true,true,true,true,'2020-4-7','1972-1-2',3),
// --
// (40,'マイケルジャクソン','1jakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6),
// --
// (41,'ウルトラマン','1uru@toraman','password',true,true,true,true,'2020-6-2','1962-12-2',6),
// --
// (42,'小池栄子','1eiko@koike','password',true,true,true,true,'2020-4-2','1982-1-2',6),
// (52,'大野智','sa1tosie@ono','password',true,true,true,true,'2020-1-2','1999-1-2',2),
// (53,'松本潤','j1yun@matsumoteo','password',true,true,true,true,'2019-11-2','1962-1-2',2),
// (54,'二宮和也','1kazunari@ninomieya','password',true,true,true,true,'2020-3-2','1990-1-2',3),
// (55,'相葉雅紀','1masaki@ninomiyea','password',true,true,true,true,'2021-1-2','1980-1-2',4),
// (56,'玉森裕太','1yuta@tamamorie','password',true,true,true,true,'2021-1-2','1999-1-2',5),
// (57,'錦戸亮','1ryo@nishikidoew','password',true,true,true,true,'2019-1-2','1932-1-2',5),
// (58,'錦織圭','1kei@nishikorwei','password',true,true,true,true,'2020-1-2','1992-1-2',5),
// (59,'王貞治','1sadaharu@oue','password',true,true,true,true,'2020-4-7','1972-1-2',3),
// (60,'マイケルジャクソン','1jakcsodn@mikel','password',true,true,true,true,'2020-1-2','1992-1-2',6),
// (61,'ウルトラマン','1uru@tosraman','password',true,true,true,true,'2020-6-2','1962-12-2',6),
// (72,'小池栄子','1eiko@koikwse','password',true,true,true,true,'2020-4-2','1982-1-2',6)
// insert into
//
// employee(
// id, username, email, password,
// enabled,is_account_non_expired,is_account_non_locked,is_credentials_non_expired,
// join_date,birthday,department)values
// (32,'大野智','sa1tosi@ono','password',true,true,true,true,'2020-1-2','1999-1-2','人事部'),
// (33,'松本潤','j1yun@matsumoto','password',true,true,true,true,'2019-11-2','1962-1-2','人事部'),
// (34,'二宮和也','1kazunari@ninomiya','password',true,true,true,true,'2020-3-2','1990-1-2','営業部'),
// (35,'相葉雅紀','1masaki@ninomiya','password',true,true,true,true,'2021-1-2','1980-1-2','IT事業部'),
// (36,'玉森裕太','1yuta@tamamori','password',true,true,true,true,'2021-1-2','1999-1-2','インターン'),
// (37,'錦戸亮','1ryo@nishikido','password',true,true,true,true,'2019-1-2','1932-1-2','研修生'),
// (38,'錦織圭','1kei@nishikori','password',true,true,true,true,'2020-1-2','1992-1-2','研修生'),
// (39,'王貞治','1sadaharu@ou','password',true,true,true,true,'2020-4-7','1972-1-2','営業部'),
// (40,'マイケルジャクソン','1jakcson@mikel','password',true,true,true,true,'2020-1-2','1992-1-2','人事部'),
// (41,'ウルトラマン','1uru@toraman','password',true,true,true,true,'2020-6-2','1962-12-2','研修生'),
// (42,'小池栄子','1eiko@koike','password',true,true,true,true,'2020-4-2','1982-1-2','営業部')