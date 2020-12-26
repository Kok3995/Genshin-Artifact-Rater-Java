import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ArtifactScore {
    private boolean compareToMaxRarity;

    protected Map<StatType, double[]> minMainMap;
    protected Map<StatType, double[]> maxMainMap;
    protected Map<StatType, double[]> maxSubMap;
    protected Map<StatType, Double> weights;

    public boolean isCompareToMaxRarity() {
        return compareToMaxRarity;
    }

    public void setCompareToMaxRarity(boolean compareToMaxRarity) {
        this.compareToMaxRarity = compareToMaxRarity;
    }

    public Map<StatType, double[]> getMinMainMap() {
        if (minMainMap == null)
            init();

        return minMainMap;
    }

    public Map<StatType, double[]> getMaxMainMap() {
        if (maxMainMap == null)
            init();

        return maxMainMap;
    }

    public Map<StatType, double[]> getMaxSubMap() {
        if (maxSubMap == null)
            init();

        return maxSubMap;
    }

    public Map<StatType, Double> getWeights() {
        if (weights == null)
            init();

        return weights;
    }

    public Map<StatType, Double> getDefaultWeights() {
        Map<StatType, Double> defaultWeights = new HashMap<>();
        defaultWeights.put(StatType.HP_FLAT, 0d);
        defaultWeights.put(StatType.HP_PERCENT, 0d);
        defaultWeights.put(StatType.DEF_FLAT, 0d);
        defaultWeights.put(StatType.DEF_PERCENT, 0d);
        defaultWeights.put(StatType.ATK_FLAT, 0.5d);
        defaultWeights.put(StatType.ATK_PERCENT, 1d);
        defaultWeights.put(StatType.ENERY_RECHARGE, 0.5d);
        defaultWeights.put(StatType.ELEMENTAL_MASTERY, 0.5d);
        defaultWeights.put(StatType.PHYSICAL_BONUS, 1d);
        defaultWeights.put(StatType.CRIT_RATE, 1d);
        defaultWeights.put(StatType.CRIT_DMG, 1d);
        defaultWeights.put(StatType.PYRO_BONUS, 1d);
        defaultWeights.put(StatType.HYDRO_BONUS, 1d);
        defaultWeights.put(StatType.CRYO_BONUS, 1d);
        defaultWeights.put(StatType.ELECTRO_BONUS, 1d);
        defaultWeights.put(StatType.GEO_BONUS, 1d);
        defaultWeights.put(StatType.ANEMO_BONUS, 1d);
        defaultWeights.put(StatType.DENDRO_BONUS, 1d);
        defaultWeights.put(StatType.HEALING_BONUS, 0d);

        return defaultWeights;
    }

    public void setWeights(Map<StatType, Double> weights) {
        this.weights = weights;
    }

    public void setWeight(StatType statType, double value) {
        getWeights().put(statType, value);
    }

    public void resetWeight() {
        for (Map.Entry<StatType, Double> entry : getWeights().entrySet()) {
            entry.setValue(0d);
        }
    }

    public ArtifactScoreResult score(Artifact artifact) {
        if (artifact == null)
            throw new IllegalArgumentException("Invalid Artifact");

        ArtifactScoreResult result = ArtifactScoreResult.create();
        result.setStatContainerType(artifact.getStatContainerType());

        if (artifact.getFlatStats().size() < 1)
            return result;

        scoreMainStat(artifact, result);
        scoreSubStat(artifact, result);

        return result;
    }

    protected void scoreMainStat(Artifact artifact, ArtifactScoreResult artifactScoreResult) {
        artifactScoreResult.setMainWeight(3 + (int)(artifact.getLevel() / 4));

        Stat main = artifact.getFlatStats().get(0);
        StatType mainType = main.getStatType();
        RarityType maxRarity = isCompareToMaxRarity() ? artifact.getArtifactSetType().getMaxRarity() : RarityType.FIVE;

        double[] maxMains = getMaxMainMap().get(mainType);
        if (maxMains == null)
            return;

        double maxMain = maxMains[maxRarity.ordinal()];

        double[] minMains = getMinMainMap().get(mainType);
        if (minMains == null)
            return;

        double minMain = minMains[maxRarity.ordinal()];

        double maxMainAvg = maxMain - (maxMain - minMain) * (1 - artifact.getLevel() / (maxRarity.getMaxLevel() + 0d));

        double value = validate(main.getValue(), maxMainAvg);

        double weight = getWeights().getOrDefault(mainType, 0d);

        if (maxMainAvg > 0)
            artifactScoreResult.setMainScore(value / maxMainAvg * weight * artifactScoreResult.getMainWeight());

        if (mainType == StatType.ATK_FLAT || mainType == StatType.HP_FLAT) {
            artifactScoreResult.setMainWeight(artifactScoreResult.getMainWeight() * weight);
        }
    }

    protected void scoreSubStat(Artifact artifact, ArtifactScoreResult artifactScoreResult) {
        double result = 0;
        for (int i = 1; i < artifact.getFlatStats().size(); i++) {
            Stat sub = artifact.getFlatStats().get(i);
            StatType subType = sub.getStatType();
            RarityType maxRarity = isCompareToMaxRarity() ? artifact.getArtifactSetType().getMaxRarity() : RarityType.FIVE;

            double[] maxSubs = getMaxSubMap().get(subType);

            if (maxSubs == null)
                continue;

            double maxSub = maxSubs[maxRarity.ordinal()];
            double value = validate(sub.getValue(), maxSub * getMaxUpgradePerSubStat(maxRarity, artifact.getLevel()));
            double weight = getWeights().getOrDefault(subType, 0d);

            if (maxSub > 0)
                result += value / maxSub * weight;
        }

        artifactScoreResult.setSubScore(result);
        artifactScoreResult.setSubWeight(getSubWeight(artifact));
    }

    protected double getSubWeight(Artifact artifact) {
        double largestWeight = 0;
        double subWeight = 0;

        StatType maintype = artifact.getFlatStats().get(0).getStatType();
        PriorityQueue<Double> queue = new PriorityQueue<>(10, Collections.reverseOrder());

        for (StatType subType : ArtifactHealpers.getAllSubStats()) {
            if (subType == maintype)
                continue;

            double weight = getWeights().getOrDefault(subType, 0d);
            queue.offer(weight);
        }

        int maxSlotByLevel = getSlotByLevel(artifact.getRarityType(), artifact.getLevel());
        for (int i = 0; i < maxSlotByLevel; i++) {
            Double weight = queue.poll();

            if (weight == null)
                break;

            subWeight += weight;
            largestWeight = Math.max(weight, largestWeight);
        }

        return subWeight + largestWeight * (getMaxUpgradePerSubStat(isCompareToMaxRarity() ? artifact.getArtifactSetType().getMaxRarity() : RarityType.FIVE, artifact.getLevel()) - 1);
    }

    private int getSlotByLevel(RarityType rarityType, int level) {
        int upgradeNum = level / 4;
        int initSlot = getInitSlot(rarityType);

        return Math.min(4, initSlot + upgradeNum);
    }

    protected double getMaxUpgradePerSubStat(RarityType rarityType, int level) {
        int upgradeNum = level / 4;
        int initSlot = getInitSlot(rarityType);

        int freeSlot = 4 - initSlot;

        if (upgradeNum > freeSlot)
            return upgradeNum - freeSlot + 1;
        return 1;
    }

    private int getInitSlot(RarityType rarityType) {
        int initSlot = 4;
        switch (rarityType) {
            case THREE: initSlot = 2; break;
            case FOUR: initSlot = 3; break;
            default: break;
        }

        return initSlot;
    }

    protected void init() {
        if (minMainMap == null) {
            minMainMap = new HashMap<>();
            minMainMap.put(StatType.HP_FLAT, new double[]{430d, 645d, 717d});
            minMainMap.put(StatType.HP_PERCENT, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.DEF_PERCENT, new double[]{0.066d, 0.079d, 0.087d});
            minMainMap.put(StatType.ATK_FLAT, new double[]{28d, 42d, 47d});
            minMainMap.put(StatType.ATK_PERCENT, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.ENERY_RECHARGE, new double[]{0.058d, 0.07d, 0.078d});
            minMainMap.put(StatType.ELEMENTAL_MASTERY, new double[]{21d, 25d, 28d});
            minMainMap.put(StatType.PHYSICAL_BONUS, new double[]{0.066d, 0.079d, 0.087d});
            minMainMap.put(StatType.CRIT_RATE, new double[]{0.035d, 0.042d, 0.047d});
            minMainMap.put(StatType.CRIT_DMG, new double[]{0.07d, 0.084d, 0.093d});
            minMainMap.put(StatType.PYRO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.HYDRO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.CRYO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.ELECTRO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.GEO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.ANEMO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.DENDRO_BONUS, new double[]{0.052d, 0.063d, 0.07d});
            minMainMap.put(StatType.HEALING_BONUS, new double[]{0.04d, 0.048d, 0.054d});
        }

        if (maxMainMap == null) {
            maxMainMap = new HashMap<>();
            maxMainMap.put(StatType.HP_FLAT, new double[]{1893d, 3571d, 4780d});
            maxMainMap.put(StatType.HP_PERCENT, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.DEF_PERCENT, new double[]{0.288d, 0.435d, 0.583d});
            maxMainMap.put(StatType.ATK_FLAT, new double[]{123d, 232d, 311d});
            maxMainMap.put(StatType.ATK_PERCENT, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.ENERY_RECHARGE, new double[]{0.256d, 0.387d, 0.518d});
            maxMainMap.put(StatType.ELEMENTAL_MASTERY, new double[]{92d, 139d, 187d});
            maxMainMap.put(StatType.PHYSICAL_BONUS, new double[]{0.288d, 0.435d, 0.583d});
            maxMainMap.put(StatType.CRIT_RATE, new double[]{0.154d, 0.232d, 0.311d});
            maxMainMap.put(StatType.CRIT_DMG, new double[]{0.0308d, 0.464d, 0.622d});
            maxMainMap.put(StatType.PYRO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.HYDRO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.CRYO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.ELECTRO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.GEO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.ANEMO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.DENDRO_BONUS, new double[]{0.231d, 0.348d, 0.466d});
            maxMainMap.put(StatType.HEALING_BONUS, new double[]{0.178d, 0.268d, 0.359d});
        }

        if (maxSubMap == null) {
            maxSubMap = new HashMap<>();
            maxSubMap.put(StatType.HP_FLAT, new double[]{143, 239, 299});
            maxSubMap.put(StatType.ATK_FLAT, new double[]{9, 16, 19});
            maxSubMap.put(StatType.DEF_FLAT, new double[]{11, 19, 23});
            maxSubMap.put(StatType.HP_PERCENT, new double[]{0.035, 0.047, 0.058});
            maxSubMap.put(StatType.ATK_PERCENT, new double[]{0.035, 0.047, 0.058});
            maxSubMap.put(StatType.DEF_PERCENT, new double[]{0.044, 0.058, 0.073});
            maxSubMap.put(StatType.ELEMENTAL_MASTERY, new double[]{14, 19, 23});
            maxSubMap.put(StatType.ENERY_RECHARGE, new double[]{0.039, 0.052, 0.065});
            maxSubMap.put(StatType.CRIT_RATE, new double[]{0.023, 0.031, 0.039});
            maxSubMap.put(StatType.CRIT_DMG, new double[]{0.047, 0.062, 0.078});
        }

        if (weights == null) {
            weights = getDefaultWeights();
        }
    }

    protected double validate(double value, double max) {
        value = Math.min(max, value);

        return value;
    }
}
