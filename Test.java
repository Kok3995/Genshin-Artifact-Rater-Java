import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        Artifact artifact = new Artifact();
        artifact.setStatContainerType(StatContainerType.ARTIFACT_CUP);
        artifact.setArtifactSetType(ArtifactSetType.Gladiator);
        artifact.setRarityType(RarityType.FIVE);
        artifact.setLevel(20);
        artifact.addStat(new Stat(StatType.ATK_PERCENT).setStatValue(46.6));
        artifact.addStat(new Stat(StatType.CRIT_RATE).setStatValue(3.1));
        artifact.addStat(new Stat(StatType.CRIT_DMG).setStatValue(18.8));
        artifact.addStat(new Stat(StatType.HP_FLAT).setStatValue(747));
        artifact.addStat(new Stat(StatType.ELEMENTAL_MASTERY).setStatValue(47));

        ArtifactScore artifactScore = new ArtifactScore();
        ArtifactScoreResult result = artifactScore.score(artifact);

        System.out.printf("Score: %s (%s)\nMain Score: %s (%s)\nSub Score: %s (%s)", 
            ordinal(result.getScore() * 100), percent(result.getScorePercent()),
            ordinal(result.getMainScore() * 100), percent(result.getMainScorePercent()),
            ordinal(result.getSubScore() * 100), percent(result.getSubScorePercent()));
    }

    public static String ordinal(double value) {
        return ordinalFormat.format(value);
    }

    public static String percent(double value) {
        return percentFormat.format(value);
    }
    
    private static final DecimalFormat percentFormat = new DecimalFormat("#.#%");

    private static final DecimalFormat ordinalFormat = new DecimalFormat("#");
}