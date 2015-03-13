package com.vasa.scheduling.enums;

import java.util.Map;

public enum Carrier implements CodeAndDisplayNameEnum<Integer>{

	ATT(1,"AT&T"),
	SPRINT(2,"Sprint"),
	TMOBIL(3,"T-Modile"),
	VERIZON(4,"Verizon"),
	NEXTTEL(5,"Nextel"),
	CRICKET(6,"Cricket");

	private final Integer code;
	private final String displayName;

	private Carrier(Integer code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	public Integer getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static Map<?, String> getDisplayNames() {
		return EnumFactory.getEnumDisplayNames(Carrier.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(Carrier.class);
	}

	public static Carrier toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( Carrier.class, code);
	}

	public static Carrier toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(Carrier.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
}
