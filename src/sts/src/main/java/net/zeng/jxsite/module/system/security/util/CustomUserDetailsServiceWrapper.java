/* 
*/

package net.zeng.jxsite.module.system.security.util;

import org.springframework.security.userdetails.hierarchicalroles.RoleHierarchy;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class CustomUserDetailsServiceWrapper extends HibernateDaoSupport implements UserDetailsService {

    private static Log log = LogFactory.getLog(CustomUserDetailsServiceWrapper.class);
    
    private UserDetailsService userDetailsService = null;
    private RoleHierarchy roleHierarchy = null;
    private Class<?>[] userInfoObjectTypes;

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Assert.notEmpty(userInfoObjectTypes);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        for(Class<?> clazz: userInfoObjectTypes) {
            DetachedCriteria query = DetachedCriteria.forClass(clazz).add(Restrictions.eq("user.username", username));
            try {
                Object info = getHibernateTemplate().findByCriteria(query).get(0);
                return new CustomUserDetailsWrapper(userDetails, roleHierarchy, info);
            } catch (IndexOutOfBoundsException ex) {
                //log Not necessary, for informational purpose only
                log.info("Did not find any " + clazz.getSimpleName() + " objects");
            }
        }
        return new CustomUserDetailsWrapper(userDetails, roleHierarchy);
    }

    public UserDetailsService getWrappedUserDetailsService() {
        return userDetailsService;
    }

    public void setUserInfoObjectTypes(Class<?>[] userInfoObjectTypes) {
        this.userInfoObjectTypes = userInfoObjectTypes;
    }
}
