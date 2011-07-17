/**
 * 
 */
package net.zeng.jxsite.module.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zeng.xiaoning
 * 
 */
public class BlockSetting implements Serializable, Comparable<BlockSetting> {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String key;
	private String name;
	private String value;
	private String usage;

	private BlockPrototype prototype;
	private BlockInstance instance;

	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockSetting() {
	}
	
	public BlockSetting(BlockSetting setting) {
		this.key = setting.key;
		this.name = setting.name;
		this.value = setting.value;
		this.usage = setting.usage;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public int compareTo(BlockSetting o) {
		return this.key.compareTo(o.key);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public BlockPrototype getPrototype() {
		return prototype;
	}

	public void setPrototype(BlockPrototype prototype) {
		this.prototype = prototype;
	}

	public BlockInstance getInstance() {
		return instance;
	}

	public void setInstance(BlockInstance instance) {
		this.instance = instance;
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
