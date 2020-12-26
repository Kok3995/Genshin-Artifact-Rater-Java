public class ArtifactHealpers {
    private static StatType[] allSubStats;

    public static StatType[] getAllSubStats() {
        if (allSubStats == null) {
            allSubStats = new StatType[] {
                    StatType.HP_FLAT, StatType.ATK_FLAT, StatType.DEF_FLAT,
                    StatType.HP_PERCENT, StatType.ATK_PERCENT, StatType.DEF_PERCENT,
                    StatType.ELEMENTAL_MASTERY, StatType.ENERY_RECHARGE,
                    StatType.CRIT_RATE, StatType.CRIT_DMG
            };
        }

        return allSubStats;
    }
}
