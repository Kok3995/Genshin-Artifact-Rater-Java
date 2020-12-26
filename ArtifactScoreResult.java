public class ArtifactScoreResult {
    private StatContainerType statContainerType;
    private double mainScore;
    private double subScore;
    private double mainWeight;
    private double subWeight;

    public ArtifactScoreResult() {
    }

    public ArtifactScoreResult(double mainScore, double subScore, double mainWeight, double subWeight) {
        this.mainScore = mainScore;
        this.subScore = subScore;
        this.mainWeight = mainWeight;
        this.subWeight = subWeight;
    }

    //region Getters/Setters

    public StatContainerType getStatContainerType() {
        return statContainerType;
    }

    public void setStatContainerType(StatContainerType statContainerType) {
        this.statContainerType = statContainerType;
    }

    public double getMainScore() {
        return mainScore;
    }

    public void setMainScore(double mainScore) {
        this.mainScore = mainScore;
    }

    public double getSubScore() {
        return subScore;
    }

    public void setSubScore(double subScore) {
        this.subScore = subScore;
    }

    public double getMainWeight() {
        return mainWeight;
    }

    public void setMainWeight(double mainWeight) {
        this.mainWeight = mainWeight;
    }

    public double getSubWeight() {
        return subWeight;
    }

    public void setSubWeight(double subWeight) {
        this.subWeight = subWeight;
    }

    //endregion

    public double getTotalWeight() {
        return mainWeight + subWeight;
    }

    public double getScore() {
        return mainScore + subScore;
    }

    public double getScorePercent() {
        if (getScore() == 0)
            return 0;

        if (getTotalWeight() > 0)
            return getScore() / getTotalWeight();
        else
            return 1;
    }

    public double getMainScorePercent() {
        if (statContainerType == StatContainerType.ARTIFACT_FLOWER || statContainerType == StatContainerType.ARTIFACT_FEATHER)
            return 1;

        if (mainScore == 0)
            return 0;

        double mainScorePercent = 1;

        if (mainWeight > 0)
            mainScorePercent = mainScore / mainWeight;

        if (mainScore > 99)
            mainScorePercent = 100;

        return mainScorePercent;
    }

    public double getSubScorePercent() {
        if (subScore == 0)
            return 0;

        double subScorePercent = 1;

        if (subWeight > 0)
            subScorePercent = subScore / subWeight;

        return subScorePercent;
    }

    public static ArtifactScoreResult create() {
        return new ArtifactScoreResult();
    }
}
