# Genshin-Artifact-Rater-Java
Base on [Genshin-Artifact-Rater](https://github.com/shrubin/Genshin-Artifact-Rater).   
Added 4* Artifact Scoring.  
Just drop the files into your projects, fix some imports and you're good to go.  
Sample app: [Genshin Equip](https://play.google.com/store/apps/details?id=com.kok_emm.genshinequip)
# Usage
```java
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
```
# Feedbacks
If you have any feedbacks, bug report please join the [Discord Channel](https://discord.gg/7eDJa3xEGj)
