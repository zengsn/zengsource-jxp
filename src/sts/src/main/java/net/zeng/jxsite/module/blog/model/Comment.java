/**
 * 
 */
package net.zeng.jxsite.module.blog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.zeng.jxsite.module.system.model.security.SecureObject;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class Comment extends SecureObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String text;
	private Entry entry;
	private Comment target;

	private List<?> follows = new LinkedList<Comment>();

	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Comment() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Comment getTarget() {
		return target;
	}

	public void setTarget(Comment target) {
		this.target = target;
	}

	public List<?> getFollows() {
		return follows;
	}

	public void setFollows(List<?> follows) {
		this.follows = follows;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
