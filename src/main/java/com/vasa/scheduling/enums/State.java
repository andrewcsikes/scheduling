package com.vasa.scheduling.enums;

import java.util.ArrayList;
import java.util.Map;

public enum  State implements CodeAndDisplayNameEnum<String>
{
	ALABAMA("AL","Alabama"),
	ALASKA("AK","Alaska"),
	ARIZONA ("AZ","Arizona"),
	ARKANSAS("AR","Arkansas"),
	CALIFORNIA ("CA","California"),
	COLORADO ("CO","Colorado"),
	CONNECTICUT("CT","Connecticut"),
	DELAWARE("DE","Delaware"),
	FLORIDA("FL","Florida"),
	GEORGIA("GA","Georgia"),
	HAWAII("HI","Hawaii"),
	IDAHO("ID","Idaho"),
	ILLINOIS("IL","Illinois"),
	INDIANA("IN","Indiana"),
	IOWA("IA","Iowa"),
	KANSAS("KS","Kansas"),
	KENTUCKY("KY","Kentucky"),
	LOUISIANA("LA","Louisiana"),
	MAINE("ME","Maine"),
	MARYLAND("MD","Maryland"),
	MASSACHUSETTS("MA","Massachusetts"),
	MICHIGAN("MI","Michigan"),
	MINNESOTA("MN","Minnesota"),
	MISSISSIPPI("MS","Mississippi"),
	MISSOURI("MO","Missouri"),
	MONTANA("MT","Montana"),
	NEBRASKA("NE","Nebraska"),
	NEVADA("NV","Nevada"),
	NEW_HAMPSHIRE("NH","New Hampshire"),
	NEW_JERSEY("NJ","New Jersey"),
	NEW_MEXICO("NM","New Mexico"),
	NEW_YORK("NY","New York"),
	NORTH_CAROLINA("NC","North Carolina"),
	NORTH_DAKOTA("ND","North Dakota"),
	OHIO("OH","Ohio"),
	OKLAHOMA("OK","Oklahoma"),
	OREGON("OR","Oregon"),
	PENNSYLVANIA("PA","Pennsylvania"),
	RHODE_ISLAND("RI","Rhode Island"),
	SOUTH_CAROLINA("SC","South Carolina"),
	SOUTH_DAKOTA("SD","South Dakota"),
	TENNESSEE("TN","Tennessee"),
	TEXAS("TX","Texas"),
	UTAH("UT","Utah"),
	VERMONT("VT","Vermont"),
	VIRGINIA ("VA","Virginia"),
	WASHINGTON("WA","Washington"),
	WEST_VIRGINIA("WV","West Virginia"),
	WISCONSIN("WI","Wisconsin"),
	WYOMING("WY","Wyoming"),
	ALBERTA("AB","Alberta"),
	BRITISH_COLUMBIA("BC","British Columbia"),
	MANITOBA("MB","Manitoba"),
	NEW_BRUNSWICK("NB","New Brunswick"),
	NEW_FOUNDLAND_AND_LABRADOR("NL","Newfoudland and Labrador"),
	NORTHWEST_TERRITORIES("NT","Northwest Territories"),
	NOVA_SCOTIA("NS","Nova Scotia"),
	NUNAVUT("NU","Nunavut"),
	ONTARIO("ON","Ontario"),
	PRINCE_EDWARD_ISLAND("PE","Prince Edward Island"),
	QUEBEC("QC","Quebec"),
	SASKATCHEWAN("SK","Saskatchewan"),
	YUKON("YT","Yukon");

	private final String code;
	private final String displayName;

	private State(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public static Map<?, String> getDisplayNames() {
		return EnumFactory.getEnumDisplayNames(State.class);
	}

	public static State toEnumFromCode(String code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( State.class, code);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
  public static ArrayList<String> getCodes() {
		ArrayList<String> map = new ArrayList();
		Class<? extends CodeAndDisplayNameEnum> clazz = State.class;
		for (CodeAndDisplayNameEnum e : (clazz.getEnumConstants())) {
			map.add((String)e.getCode());
		}
		return map;
	}
}