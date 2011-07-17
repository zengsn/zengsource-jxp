/**
 * 
 */
package net.zeng.jxsite.module.news.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int HIDDEN = -1;
	public static final int PUBLIC = 0;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String text;
	private String email;
	private String commenter;
	
	private Integer index = 0;
	private Integer status = PUBLIC;

	private Entry targetEntry;
	private Comment targetComment;
	private List<Comment> commentList;

	private Date createdTime; //
	private Date updatedTime; //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Comment() {
		this.commentList = new ArrayList<Comment>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCommenter() {
		return commenter;
	}

	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Entry getTargetEntry() {
		return targetEntry;
	}

	public void setTargetEntry(Entry targetEntry) {
		this.targetEntry = targetEntry;
	}

	public Comment getTargetComment() {
		return targetComment;
	}

	public void setTargetComment(Comment targetComment) {
		this.targetComment = targetComment;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
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
