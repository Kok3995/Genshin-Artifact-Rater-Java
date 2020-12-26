import java.util.Map;

public enum StatContainerType implements IntegerEnum {

    ARTIFACT_FLOWER(1 << 3, "FLO", 1),

    ARTIFACT_FEATHER(1 << 4, "PLU", 1 << 1),

    ARTIFACT_SAND(1 << 5, "SAN", 1 << 2),

    ARTIFACT_CUP(1 << 6, "GOB", 1 << 3),

    ARTIFACT_HAT(1 << 7, "CIR", 1 << 4);

    private final int val;
    private final String shortName;
    private int artifactVal;

    StatContainerType(int val, String shortName, int artifactVal) {
        this.val = val;
        this.shortName = shortName;
        this.artifactVal = artifactVal;
    }

    @Override
    public int getVal() {
        return val;
    }

    public String getShortName() {
        return shortName;
    }

    public int getArtifactVal() {
        return artifactVal;
    }

    private static transient Map map = IntegerEnum.getValues(StatContainerType.class);

    public static StatContainerType fromVal(int val) {
        return (StatContainerType)map.get(val);
    }

}
