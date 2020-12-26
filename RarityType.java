import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum RarityType implements IntegerEnum {

    THREE(1, "3-star", 12),

    FOUR(1 << 1, "4-star", 16),

    FIVE(1 << 2, "5-star", 20);

    private int val;
    private String friendlyName;
    private int maxLevel;

    RarityType(int val, String friendlyName, int maxLevel) {
        this.val = val;
        this.friendlyName = friendlyName;
        this.maxLevel = maxLevel;
    }

    @Override
    public int getVal() {
        return val;
    }

    public static List<Integer> fromCollection(int collection) {
        List<Integer> rarityTypeList = new ArrayList<>(3);
        for (RarityType rarityType : map.values()) {
            if ((collection & rarityType.getVal()) == rarityType.getVal())
                rarityTypeList.add(rarityType.getVal());
        }

        return rarityTypeList;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getNumberOfUpgrade(int currentLevel) {
        int value = (int)Math.ceil((maxLevel - currentLevel) / 4.0);
        return Math.max(value, 0);
    }

    private static transient Map<Integer, RarityType> map = IntegerEnum.getValues(RarityType.class);

    public static RarityType fromVal(int val) {
        return (RarityType)map.get(val);
    }
}
