package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sud;
	@Autowired
	private SysUserRoleDao surd;
	@Autowired
	private SysRoleMenuDao srmd;
	@Autowired
	private SysMenuDao smd;
	/**授权:获取登录用户的权限信息并进行封装**/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("查权限");
		//获取登录用户信息
		SysUser user=(SysUser) principals.getPrimaryPrincipal();
		Integer userId = user.getId();
		//基于用户id获取角色信息
		List<Integer> roleIds = surd.findRoleIdsByUserId(userId);
		if(roleIds==null||roleIds.size()==0)
			throw new AuthorizationException();
		//基于角色id获取菜单信息
		Integer[] array= {};
		List<Integer> menuIds = srmd.findMenuIdsByRoleIds(roleIds.toArray(array));
		if(menuIds==null||menuIds.size()==0)
			throw new AuthorizationException();
		//基于菜单id获取权限标识
		List<String> permissions = smd.findPermissions(menuIds.toArray(array));
		//对权限信息进行封装并返回
		Set<String>set=new HashSet<>();
		for (String per : permissions) {
			if(!StringUtils.isEmpty(per)) {
			set.add(per);
		}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}

	/**认证*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户名(用户页面输入)
		UsernamePasswordToken upToken=
				(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		//2.基于用户名查询用户信息
		SysUser user=
				sud.findUserByUserName(username);
		//3.判定用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//4.判定用户是否已被禁用。
		if(user.getValid()==0)
			throw new LockedAccountException();
		//5.封装用户信息
		
		//错在这里
		ByteSource credentialsSalt=
				ByteSource.Util.bytes(user.getSalt());
		//记住：构建什么对象要看方法的返回值
		SimpleAuthenticationInfo info=
				new SimpleAuthenticationInfo(
						user,//principal (身份)
						user.getPassword(),//hashedCredentials
						credentialsSalt, //credentialsSalt
						getName());//realName
		//6.返回封装结果
		return info;//返回值会传递给认证管理器(后续
		//认证管理器会通过此信息完成认证操作)
	}
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//构建凭证匹配对象
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
		//设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
	
}
