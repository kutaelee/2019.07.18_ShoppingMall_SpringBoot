package com.shop.www.account;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//FRST_REG_IP,LAST_MOD_IP,LAST_MOD_ID
public class AccountDTO implements UserDetails{
	private String id;
	private String pass;
	private String nick;
	private String frstRegIp;
	private String lastModIp;
	private String lastModId;
	private String email;
	private String grade;
	private String point;
	private String pointAll;
	private String seq;

	private boolean isAccountNonExpired; 
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired; 
	private boolean isEnabled;
	private Collection <GrantedAuthority> authorities;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", pass=" + pass + ", nick=" + nick + ", frstRegIp=" + frstRegIp
				+ ", lastModIp=" + lastModIp + ", lastModId=" + lastModId + ", email=" + email
				+ ", isAccountNonExpired=" + isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked
				+ ", isCredentialsNonExpired=" + isCredentialsNonExpired + ", isEnabled=" + isEnabled + ", authorities="
				+ authorities + "]";
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getPointAll() {
		return pointAll;
	}
	public void setPointAll(String pointAll) {
		this.pointAll = pointAll;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getFrstRegIp() {
		return frstRegIp;
	}
	public void setFrstRegIp(String frstRegIp) {
		this.frstRegIp = frstRegIp;
	}
	public String getLastModIp() {
		return lastModIp;
	}
	public void setLastModIp(String lastModIp) {
		this.lastModIp = lastModIp;
	}
	public String getLastModId() {
		return lastModId;
	}
	public void setLastModId(String lastModId) {
		this.lastModId = lastModId;
	}

	
	@Override
	public Collection <GrantedAuthority>  getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.pass;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.isAccountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.isAccountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.isCredentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isEnabled;
	}
	public void setUserName(String id) {
		this.id = id;
	}
	public void setPass(String password) {
		this.pass = password;
	}
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
 



}
