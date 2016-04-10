package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the city_master database table.
 * 
 */
@Entity
@Table(name="city_master")
@NamedQuery(name="CityMaster.findAll", query="SELECT c FROM CityMaster c")
public class CityMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="city_id")
	private int cityId;

	@Column(name="active_flag")
	private int activeFlag;

	private String city;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timesatmp;

	//bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name="state")
	private StateMaster stateMaster;

	//bi-directional many-to-one association to MerchantMaster
	@OneToMany(mappedBy="cityMaster")
	private List<MerchantMaster> merchantMasters;

	public CityMaster() {
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getTimesatmp() {
		return this.timesatmp;
	}

	public void setTimesatmp(Date timesatmp) {
		this.timesatmp = timesatmp;
	}

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	public List<MerchantMaster> getMerchantMasters() {
		return this.merchantMasters;
	}

	public void setMerchantMasters(List<MerchantMaster> merchantMasters) {
		this.merchantMasters = merchantMasters;
	}

	public MerchantMaster addMerchantMaster(MerchantMaster merchantMaster) {
		getMerchantMasters().add(merchantMaster);
		merchantMaster.setCityMaster(this);

		return merchantMaster;
	}

	public MerchantMaster removeMerchantMaster(MerchantMaster merchantMaster) {
		getMerchantMasters().remove(merchantMaster);
		merchantMaster.setCityMaster(null);

		return merchantMaster;
	}

}