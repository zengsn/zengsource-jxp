/**
 * 
 */
package net.zeng.jxsite.module.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author snzeng
 * 
 */
public class Page implements Serializable, Comparable<Page> {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	Logger logger = Logger.getLogger(getClass());

	private String id;
	private String name;
	private String url;
	private String cls;
	private String style;
	private Integer columns = 1;
	private Module module;
	private BlockPrototype defaultBlock;
	private Set<BlockInstance> blockInstances;

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Page() {
		blockInstances = new HashSet<BlockInstance>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public int compareTo(Page other) {
		return this.id.compareTo(other.id);
	}

	public void addBlockInstance(BlockInstance instance) {
		this.blockInstances.add(instance);
		instance.addPage(this);
	}

	public BlockInstance removeBlockInstance(String instanceId) {
		if(instanceId == null) {
			return null;
		}
		for(BlockInstance instance : this.blockInstances) {
			if(instance != null && instanceId.equals(instance.getId())) {
				this.blockInstances.remove(instance);
				instance.removePage(this);
				return instance;
			}
		}		
		return null;
	}

	public List<BlockInstance> getSortedBlockInstances() {
		List<BlockInstance> sorted = new ArrayList<BlockInstance>();
		sorted.addAll(this.blockInstances);
		Collections.sort(sorted, new Comparator<BlockInstance>() {
			public int compare(BlockInstance o1, BlockInstance o2) {
				return o1.getIndex() - o2.getIndex();
			}
		});
		return sorted;
	}

	public Integer getBaseCells() {
		int baseCells = 0;
		for (BlockInstance block : this.blockInstances) {
			if (block != null) {
				baseCells += block.getColspan() + block.getRowspan() - 1;
			}
		}
		logger.info("Base cell number: " + baseCells);
		return baseCells;
	}

	public void moveBlock(int oldIndex, int newIndex) {
		if (newIndex > 0 && newIndex < this.getBlockInstances().size()) {
			BlockInstance oldBlock = getBlockInstance(oldIndex);
			BlockInstance newBlock = getBlockInstance(newIndex);
			if(oldBlock != null && newBlock != null) {
				oldBlock.setIndex(newIndex);
				newBlock.setIndex(oldIndex);
			}
		} else {

		}
	}
	
	public BlockInstance getBlockInstance(int index) {
		for(BlockInstance ins : this.blockInstances) {
			if(ins.getIndex() == index) {
				return ins;
			}
		}
		return null;
	}

	public String getCurrentBlocks() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (BlockInstance ins : this.blockInstances) {
			if (ins != null && ins.getPrototype() != null) {
				count++;
				sb.append(ins.getName()).append(", ");
			}
		}
		if (sb.lastIndexOf(",") > -1) {
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.insert(0, "[" + count + "] ");
		return sb.toString();
	}
	
	public int getNumOfBlocks() {
		int count = 0;
		for (BlockInstance ins : this.blockInstances) {
			if (ins != null && ins.getPrototype() != null) {
				count++;
			}
		}
		return count;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public BlockPrototype getDefaultBlock() {
		return defaultBlock;
	}

	public void setDefaultBlock(BlockPrototype defaultBlock) {
		this.defaultBlock = defaultBlock;
	}

	public Set<BlockInstance> getBlockInstances() {
		return blockInstances;
	}

	public void setBlockInstances(Set<BlockInstance> blockInstances) {
		this.blockInstances = blockInstances;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
