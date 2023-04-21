package fr.vbillard.testintegrationtdp;

import org.apache.commons.lang3.RandomUtils;

public class TDPRandomUtils {

  public static <T extends Enum<T>> T randomEnum(Class<T> unitePoidsClass) {
    int randomIndex = RandomUtils.nextInt(0, unitePoidsClass.getEnumConstants().length);
    return unitePoidsClass.getEnumConstants()[randomIndex];
  }
}
