package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the device_apk_versions database table.
 * 
 */
@Entity
@Table(name="device_apk_versions")
@NamedQuery(name="DeviceApkVersion.findAll", query="SELECT d FROM DeviceApkVersion d")
public class DeviceApkVersion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="device_apk_version")
	private String deviceApkVersion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to PosDevice
	@ManyToOne
	@JoinColumn(name="device_id")
	private PosDevice posDevice;

	public DeviceApkVersion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceApkVersion() {
		return this.deviceApkVersion;
	}

	public void setDeviceApkVersion(String deviceApkVersion) {
		this.deviceApkVersion = deviceApkVersion;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public PosDevice getPosDevice() {
		return this.posDevice;
	}

	public void setPosDevice(PosDevice posDevice) {
		this.posDevice = posDevice;
	}

}