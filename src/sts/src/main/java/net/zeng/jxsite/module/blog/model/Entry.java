/**
 * 
 */
package net.zeng.jxsite.module.blog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.SecureObject;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class Entry extends SecureObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DRAFT = 0;
	public static final int PUBLIC = 1;
	public static final int LOCKED = -1;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String digest;
	private String content;
	private UserInfo blogger;
	
	private int status = DRAFT;
	
	private String category;
	private String tag;
	private Entry referTo;
	private List<?> referred = new LinkedList<Comment>();
	
	private List<?> comments = new LinkedList<Comment>();
	
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public Entry() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public void publish() {
		setStatus(PUBLIC);
	}
	
	public void lock() {
		setStatus(LOCKED);
	}
	

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getBlogger() {
		return blogger;
	}

	public void setBlogger(UserInfo blogger) {
		this.blogger = blogger;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Entry getReferTo() {
		return referTo;
	}

	public void setReferTo(Entry referTo) {
		this.referTo = referTo;
	}

	public List<?> getReferred() {
		return referred;
	}

	public void setReferred(List<?> referred) {
		this.referred = referred;
	}

	public List<?> getComments() {
		return comments;
	}

	public void setComments(List<?> comments) {
		this.comments = comments;
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
