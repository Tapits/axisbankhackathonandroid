package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the device_apks database table.
 * 
 */
@Entity
@Table(name="device_apks")
@NamedQuery(name="DeviceApk.findAll", query="SELECT d FROM DeviceApk d")
public class DeviceApk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int version;

	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private String url;

	public DeviceApk() {
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}