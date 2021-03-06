package com.studycandy.a2c.realm;

import com.studycandy.a2c.constant.Constants;
import com.studycandy.a2c.model.User;
import com.studycandy.a2c.service.AuthorizationService;
import com.studycandy.a2c.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Coding with Intellij IDEA
 * Author: Chenls
 * Time: 2017/3/24
 */
public class MysqlRealm extends AuthorizingRealm {
    private Logger logger = Logger.getLogger(MysqlRealm.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(
                authorizationService.findRoles(Constants.SERVER_APP_KEY, username)
        );
        authorizationInfo.setStringPermissions(
                authorizationService.findPermissions(Constants.SERVER_APP_KEY, username)
        );
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        logger.debug("username:" + username);
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UnknownAccountException();
        }

        if (Boolean.FALSE.equals(user.getAvailable())) {
            throw new LockedAccountException();
        }
        /**
         * TODO: 2017/3/27 Created By Chenls
         * 此处可变更实现方式
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
