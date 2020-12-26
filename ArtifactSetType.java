import java.util.Map;

public enum ArtifactSetType implements IntegerEnum {

    Adventurer(1, RarityType.THREE),

    Lucky(11, RarityType.THREE),

    Traveling(21, RarityType.THREE),

    Instructor(31, RarityType.FOUR),

    Berserker(41, RarityType.FOUR),

    Exile(51, RarityType.FOUR),

    Sojourner(61, RarityType.FOUR),

    Martial(71, RarityType.FOUR),

    Defender(81, RarityType.FOUR),

    Miracle(91, RarityType.FOUR),

    Brave(101, RarityType.FOUR),

    Gambler(111, RarityType.FOUR),

    Scholar(121, RarityType.FOUR),

    Gladiator(131, RarityType.FIVE),

    Maiden(141, RarityType.FIVE),

    Noblesse(151, RarityType.FIVE),

    Bloodstained(161, RarityType.FIVE),

    Wanderer(171, RarityType.FIVE),

    Viridescent(181, RarityType.FIVE),

    Thundering(191, RarityType.FIVE),

    Thundersoother(201, RarityType.FIVE),

    Crimson(211, RarityType.FIVE),

    Lavawalker(221, RarityType.FIVE),

    Archaic(231, RarityType.FIVE),

    Retracing(241, RarityType.FIVE),

    Blizzard(251, RarityType.FIVE),

    Heart(261, RarityType.FIVE),

    Wisdom(271, RarityType.FOUR),

    Springtime(281, RarityType.FOUR),

    Illumination(291, RarityType.FOUR),

    Destiny(301, RarityType.FOUR);

    private int val;
    private RarityType maxRarity;

    ArtifactSetType(int val, RarityType maxRarity) {
        this.val = val;
        this.maxRarity = maxRarity;
    }

    @Override
    public int getVal() {
        return val;
    }

    public RarityType getMaxRarity() {
        return maxRarity;
    }

    private static transient Map<Integer, ArtifactSetType> map = IntegerEnum.getValues(ArtifactSetType.class);

    public static ArtifactSetType fromVal(int val) {
        return (ArtifactSetType)map.get(val);
    }

}
