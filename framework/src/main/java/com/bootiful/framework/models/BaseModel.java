package com.bootiful.framework.models;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@PrePersist
	protected void onPersist(){
		if(this.createTime == null){
			this.createTime = new Date();
		}
		this.updateTime = this.createTime;
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.updateTime = new Date();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseModel baseModel = (BaseModel) o;

		return getId() == baseModel.getId();

	}

	@Override
	public int hashCode() {
		return (int) (getId() ^ (getId() >>> 32));
	}
}
