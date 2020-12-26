import java.util.ArrayList;
import java.util.List;

public class Artifact {

    public StatContainerType statContainerType;

    public ArtifactSetType artifactSetType;

    public RarityType rarityType;

    public int level;

    public List<Stat> stats;

    public StatContainerType getStatContainerType() {
        if (statContainerType == null)
            statContainerType = StatContainerType.ARTIFACT_FLOWER;

        return statContainerType;
    }

    public void setStatContainerType(StatContainerType statContainerType) {
        if (statContainerType == null)
            return;

        this.statContainerType = statContainerType;
    }

    public ArtifactSetType getArtifactSetType() {
        if (artifactSetType == null)
            artifactSetType = ArtifactSetType.Adventurer;

        return artifactSetType;
    }

    public void setArtifactSetType(ArtifactSetType artifactSetType) {
        if (artifactSetType == null)
            return;

        this.artifactSetType = artifactSetType;
    }

    public RarityType getRarityType() {
        if (rarityType == null)
            rarityType = getArtifactSetType().getMaxRarity();

        return rarityType;
    }

    public void setRarityType(RarityType rarityType) {
        if (rarityType == null)
            return;

        this.rarityType = rarityType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        level = Math.min(level, getRarityType().getMaxLevel());
        level = Math.max(level, 0);
        this.level = level;
    }

	public List<Stat> getFlatStats() {
        if (stats == null)
            stats = new ArrayList<>();

		return stats;
	}

    public void addStat(Stat stat) {
        getFlatStats().add(stat);
    }

    public void removeStat(Stat stat) {
        if (stats == null)
            return;

        getFlatStats().remove(stat);
    }

    public void replaceStat(Stat oldStat, Stat newStat) {
        if (stats == null)
            return;

        int index = getFlatStats().indexOf(oldStat);

        if (index != -1)
            getFlatStats().set(index, newStat);
    }
}
