import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum StatType implements OrdinalEnum {

    ATK_BASE(1L, "A.B"),

    ATK_FLAT(1L << 1, "A.F"),

    ATK_PERCENT(1L << 2, "A+%"),

    CRIT_RATE(1L << 3, "Crit%"),

    CRIT_DMG(1L << 4, "CritD"),

    ELEMENTAL_MASTERY(1L << 5, "EM"),

    ENERY_RECHARGE(1L << 6, "EReC"),

    PYRO_BONUS(1L << 7, "Pyro+%"),

    PYRO_RES(1L << 8, "Pyro-%"),

    HYDRO_BONUS(1L << 9, "Hyro+%"),

    HYDRO_RES(1L << 10, "Hyro-%"),

    CRYO_BONUS(1L << 11, "Cryo+%"),

    CRYO_RES(1L << 12, "Cryo-%"),

    DENDRO_BONUS(1L << 13, "Dend+%"),

    DENDRO_RES(1L << 14, "Dend-%"),

    ANEMO_BONUS(1L << 15, "Anem+%"),

    ANEMO_RES(1L << 16, "Anem-%"),

    GEO_BONUS(1L << 17, "Geo+%"),

    GEO_RES(1L << 18, "Geo-%"),

    ELECTRO_BONUS(1L << 19, "Elec+%"),

    ELECTRO_RES(1L << 20, "Elec-%"),

    HEALING_BONUS(1L << 21, "Heal+%"),

    INCOMINGHEAL_BONUS(1L << 22, "Heal<-"),

    PHYSICAL_BONUS(1L << 23, "Phys+%"),

    PHYSICAL_RES(1L << 24, "Phys-%"),

    NORMAL_ATK_BONUS(1L << 25, "N.ATK+%"),

    CHARGED_ATK_BONUS(1L << 26, "C.ATK+%"),

    ELEMENTAL_SKILL_BONUS(1L << 27, "Skill+%"),

    ELEMENTAL_BURST_BONUS(1L << 28, "Burst+%"),

    EXTERNAL_BONUS(1L << 29, "Ext+%"),

    DEPEND_BONUS(1L << 30, "Depen+%"),

    BURNING_BONUS(1L << 31, "Burn+%"),

    CRYSTALIZE_BONUS(1L << 32, "Crys+%"),

    ELECTROCHARGED_BONUS(1L << 33, "ELC+%"),

    FROZEN_BONUS(1L << 34, "Froz+%"),

    MELT_BONUS(1L << 35, "Melt+%"),

    OVERLOADED_BONUS(1L << 36, "OvL+%"),

    SUPERCONDUCT_BONUS(1L << 37, "SpC+%"),

    SWIRL_BONUS(1L << 38, "SwL+%"),

    VAPORIZE_BONUS(1L << 39, "VaP+%"),

    HP_BASE(1L << 40, "HP.B"),

    HP_FLAT(1L << 41, "HP.F"),

    HP_PERCENT(1L << 42, "HP+%"),

    DEF_BASE(1L << 43, "DEF.B"),

    DEF_FLAT(1L << 44, "DEF.F"),

    DEF_PERCENT(1L << 45, "DEF+%");

    private long val;
    private String shortName;

    StatType(long val, String shortName) {
        this.val = val;
        this.shortName = shortName;
    }

    @Override
    public long getVal() {
        return val;
    }

    public static List<Long> fromCollection(long collection) {
        List<Long> statList = new ArrayList<>(46);
        for (StatType statType : map.values()) {
            if ((collection & statType.getVal()) == statType.getVal())
                statList.add(statType.getVal());
        }

        return statList;
    }

    public String getShortName() {
        return shortName;
    }

    private static transient Map<Long, StatType> map = OrdinalEnum.getValues(StatType.class);

    public static StatType fromVal(long val) {
        return (StatType)map.get(val);
    }
}
