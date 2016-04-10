package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the state_master database table.
 * 
 */
@Entity
@Table(name="state_master")
@NamedQuery(name="StateMaster.findAll", query="SELECT s FROM StateMaster s")
public class StateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="state_id")
	private int stateId;

	private String state;

	//bi-directional many-to-one association to CityMaster
	@OneToMany(mappedBy="stateMaster")
	private List<CityMaster> cityMasters;

	//bi-directional many-to-one association to MerchantMaster
	@OneToMany(mappedBy="stateMaster")
	private List<MerchantMaster> merchantMasters;

	public StateMaster() {
	}

	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<CityMaster> getCityMasters() {
		return this.cityMasters;
	}

	public void setCityMasters(List<CityMaster> cityMasters) {
		this.cityMasters = cityMasters;
	}

	public CityMaster addCityMaster(CityMaster cityMaster) {
		getCityMasters().add(cityMaster);
		cityMaster.setStateMaster(this);

		return cityMaster;
	}

	public CityMaster removeCityMaster(CityMaster cityMaster) {
		getCityMasters().remove(cityMaster);
		cityMaster.setStateMaster(null);

		return cityMaster;
	}

	public List<MerchantMaster> getMerchantMasters() {
		return this.merchantMasters;
	}

	public void setMerchantMasters(List<MerchantMaster> merchantMasters) {
		this.merchantMasters = merchantMasters;
	}

	public MerchantMaster addMerchantMaster(MerchantMaster merchantMaster) {
		getMerchantMasters().add(merchantMaster);
		merchantMaster.setStateMaster(this);

		return merchantMaster;
	}

	public MerchantMaster removeMerchantMaster(MerchantMaster merchantMaster) {
		getMerchantMasters().remove(merchantMaster);
		merchantMaster.setStateMaster(null);

		return merchantMaster;
	}

}