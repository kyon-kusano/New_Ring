package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name= "employee")
public class Employee implements UserDetails {

	private static final long serialVersionUID = 1L;

	//権限は一般ユーザ、マネージャ、システム管理者の３種類とする
	public enum Authority {ROLE_USER, ROLE_ADMIN}

	//問１－２ プライマリーキーを設定するアノテーションを記述
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date birthday;

	@Column()
	private int sex;

	@Column()
	private String department;

	@Column()
	private String telephone_number;

	@Column()
	private String address;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date join_date;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date updated_at;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date deleted_at;

	@Column(nullable = false)
	private boolean enabled;




	// roleは複数管理できるように、Set<>で定義。
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Set<Authority> authorities;

	// JPA requirement
	protected Employee() {}

	//コンストラクタ
		public Employee(String username, String password, String email, Date birthday, int sex, String department, String telephone_number,
					   String address, Date join_date, Date updated_at, Date deleted_at) {
			this.username = username;
			this.password = password;
			this.email = email;
			this.birthday = birthday;
			this.sex = sex;
			this.department = department;
			this.telephone_number = telephone_number;
			this.address = address;
			this.join_date = join_date;
			this.updated_at = updated_at;
			this.deleted_at = deleted_at;
			this.enabled = true;
			this.authorities = EnumSet.of(Authority.ROLE_USER);
		}


		//登録時に、日時を自動セットする
		@PrePersist
		public void prePersist() {
			this.created_at = new Date();
		}



		//admin権限チェック
		public boolean isAdmin() {
			return this.authorities.contains(Authority.ROLE_ADMIN);
		}

		//admin権限セット
		public void setAdmin(boolean isAdmin) {
			if (isAdmin) {
				this.authorities.add(Authority.ROLE_ADMIN);
			} else {
				this.authorities.remove(Authority.ROLE_ADMIN);
			}
		}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Authority authority : this.authorities) {
			authorities.add(new SimpleGrantedAuthority(authority.toString()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartmentl(String department) {
		this.department = department;
	}

	public String getTelephone_Number() {
		return telephone_number;
	}

	public void setTelephone_Number(String telephone_number) {
		this.telephone_number = telephone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getJoin_Date() {
		return join_date;
	}

	public void setJoin_Date(Date join_date) {
		this.join_date = join_date;
	}

	public Date getCreated_At() {
		return created_at;
	}
	public void setCreated_At(Date created_at) {
		this.created_at = created_at;
	}


	public Date getUpdated_At() {
		return updated_at;
	}

	public void setUpdated_At(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getDeleted_At() {
		return deleted_at;
	}

	public void setDeleted_At(Date deleted_at) {
		this.deleted_at = deleted_at;
	}


	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}
