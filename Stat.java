public class Stat {
    private static final long ORDINAL_STAT =
        StatType.ATK_BASE.getVal() |
        StatType.ATK_FLAT.getVal() |
        StatType.ELEMENTAL_MASTERY.getVal() |
        StatType.HP_BASE.getVal() |
        StatType.HP_FLAT.getVal() |
        StatType.DEF_BASE.getVal() |
        StatType.DEF_FLAT.getVal();

    protected double value;

    protected StatType statType;

    public Stat(StatType statType) {
        this.statType = statType;
    }

    public StatType getStatType() {
        return statType;
    }

    public boolean isOrdinal() {
        return (getStatType().getVal() & ORDINAL_STAT) == getStatType().getVal();
    }

    public double getValue() {
        return value;
    }

    public Stat setStatValue(double value) {
        if (isOrdinal())
            this.value = value;
        else
            this.value = value / 100;

        return this;
    }

    public Stat upgrade(double value) {
        this.value += value;
        return this;
    }
}