package com.vasa.scheduling.enums;

import java.util.Map;

public enum UserType  implements CodeAndDisplayNameEnum<Integer>{

	ADMIN(1,"ADMIN"),
	COACH(2,"Coach"),
	USER(3, "User");

	private final Integer code;
	private final String displayName;

	private UserType(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(UserType.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(UserType.class);
	}

	public static UserType toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( UserType.class, code);
	}

	public static UserType toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(UserType.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }


}
