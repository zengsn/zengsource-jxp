/**
 * 
 */
package net.zeng.jxsite.module.news.dao.orm;

import net.zeng.jxsite.module.news.dao.CommentDao;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateCommentDao extends HibernateDaoTemplate implements CommentDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/* (non-Javadoc)
	 * @see net.zeng.spring.dao.orm.HibernateDaoTemplate#getPrototypeClass()
	 */
	@Override
	public Class<?> getPrototypeClass() {
		// TODO Auto-generated method stub
		return null;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
}
