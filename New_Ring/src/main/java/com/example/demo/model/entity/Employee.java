package com.example.demo.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.dto.EmployeeRequest;

import lombok.Data;

@Entity
@Data
public class Employee implements UserDetails {

	private static final long serialVersionUID = 1L;

	// 権限は一般ユーザ、マネージャ、システム管理者の３種類とする
	public enum Authority {
		ROLE_USER, ROLE_ADMIN
	}

	// 問１－２ プライマリーキーを設定するアノテーションを記述
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date birthday;

	@Column()
	private String sex;

	@ManyToOne
	private Department department;

	@Column()
	private String telephone_Number;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Details details;

	@Temporal(TemporalType.DATE)
	@Column()
	private Date join_Date;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;

	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date updated_at;

	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date deleted_at;

	@Column(nullable = false)
	private boolean enabled;

	private boolean isAccountNonExpired;

	private boolean isAccountNonLocked;

	private boolean isCredentialsNonExpired;

	// roleは複数管理できるように、Set<>で定義。
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Set<Authority> authorities;

	// JPA requirement
	protected Employee() {
	}

	// コンストラクタ
	public Employee(String username, String password, String email, Date birthday, String sex, Department department,
			String telephone_Number, Address address, Details details, Date join_Date, Date updated_at,
			Date deleted_at) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.birthday = birthday;
		this.sex = sex;
		this.department = department;
		this.telephone_Number = telephone_Number;
		this.address = address;
		this.details = details;
		this.join_Date = join_Date;
		this.updated_at = updated_at;
		this.deleted_at = deleted_at;
		this.enabled = true;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.authorities = EnumSet.of(Authority.ROLE_USER);

	}

	public Employee(EmployeeRequest employeeRequest) {
		this.enabled = true;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.authorities = EnumSet.of(Authority.ROLE_USER);
	}

	// 登録時に、日時を自動セットする
	@PrePersist
	public void prePersist() {
		this.created_at = new Date();
	}

	// admin権限チェック
	public boolean isAdmin() {
		return this.authorities.contains(Authority.ROLE_ADMIN);
	}

	// admin権限セット
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

}
