/**
 * 
 */
package net.zeng.jxsite.module.system.model.security;

import java.util.Date;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclClass {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String description;
	private Long objectCount = 0L;
	private Date createdTime;
	private Date updatedTime;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclClass() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String clazz) {
		this.name = clazz;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(Long objectCount) {
		this.objectCount = objectCount;
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
