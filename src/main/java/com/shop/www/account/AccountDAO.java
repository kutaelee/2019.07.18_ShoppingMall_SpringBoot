package com.shop.www.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
	@Autowired
	JdbcTemplate template;
	
	public void insertUser(AccountDTO account) {
		String SQL = "INSERT INTO MEMBER(ID,PASS,EMAIL"
				+ ",FRST_REG_IP,LAST_MOD_IP,LAST_MOD_ID"
				+ ",NICK,FRST_REG_DT,LAST_MOD_DT)"
				+ " VALUES(?,?,?,?,?,?,?,now(),now())";

		template.update(SQL,account.getUsername(),account.getPassword(),account.getEmail(),
				account.getFrstRegIp(),account.getLastModIp(),account.getLastModId(),
				account.getNick());
	}
	public void insertUserAutority(String username,String role) {
		String SQL = "INSERT INTO AUTHORITY(USERNAME,AUTHORITY_NAME) VALUES(?,?)";
		template.update(SQL,username,role);
	}

	//회원가입 중복 체크
	public String doubleCheck(HashMap<String, Object> map) {
		String key=map.get("key").toString();
		String value=map.get("value").toString();
		
		String SQL="SELECT "+key+" FROM MEMBER"
				+" WHERE "+key+"=?";

		try {
			return template.queryForMap(SQL,value).toString();
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	
	}

	public Map<String,Object> getPw(HashMap<String,Object> map) {
		String SQL="SELECT PASS FROM MEMBER"
				+" WHERE ID=?";
		try {
			return template.queryForMap(SQL,map.get("id"));
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	public Map<String,Object> selectUser(String id) {
		String SQL="SELECT ID,PASS FROM MEMBER WHERE ID=?";
		return template.queryForMap(SQL,id);
	}

	public Map<String,Object> getGrade(String id) {
		String SQL="SELECT GRADE FROM MEMBER WHERE ID=?";
		
		return template.queryForMap(SQL,id);
	}

	public AccountDTO findById(String id) {
		String SQL="SELECT * FROM MEMBER WHERE ID=?";
		try {
			return (AccountDTO) template.queryForObject(SQL,new AccountMapper(),id);
		}catch(NullPointerException e) {
			return null;
		}
	}
	private static final class AccountMapper implements RowMapper{

        public Object mapRow(ResultSet rs, int rowCnt) throws SQLException{

             AccountDTO account = new AccountDTO();

     		account.setEmail(rs.getString("EMAIL"));
  		account.setUserName(rs.getString("ID"));
  		account.setFrstRegIp(rs.getString("FRST_REG_IP"));
  		account.setNick(rs.getString("NICK"));
  		account.setLastModId(rs.getString("LAST_MOD_ID"));
  		account.setLastModIp(rs.getString("LAST_MOD_IP"));
  		account.setPass(rs.getString("PASS"));
  		account.setGrade(rs.getString("GRADE"));
  		account.setPoint(rs.getString("POINT"));
  		account.setPointAll(rs.getString("POINT_ALL"));
  		account.setSeq(rs.getString("SEQ"));
             return account;

        }
	}

	public Collection<GrantedAuthority> getAuthorities(String id) {
		String SQL="SELECT AUTHORITY_NAME FROM AUTHORITY WHERE USERNAME=?";
		
		try {
			return template.queryForObject(SQL,Collection.class,id);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<String> findAuthoritiesById(String id) {
	String SQL="SELECT AUTHORITY_NAME FROM AUTHORITY WHERE USERNAME=?";
		
		try {
			return template.queryForObject(SQL,List.class,id);
		}catch(EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
		}
	}


}
