package com.sikesonline.scheduling.enums;

import java.util.Map;

public enum UserStatus implements CodeAndDisplayNameEnum<Integer>
{
	ACTIVE(1,"Active"),
	INACTIVE(2,"Inactive");

	private final Integer code;
	private final String displayName;

	private UserStatus(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(UserStatus.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(UserStatus.class);
	}

	public static UserStatus toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( UserStatus.class, code);
	}

	public static UserStatus toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(UserStatus.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
}