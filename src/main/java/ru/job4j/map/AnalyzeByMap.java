package ru.job4j.map;

import java.util.*;

public class AnalyzeByMap {

    private static Map averageMapByPupil(List<Pupil> pupils) {
        Map<String, Double> pupilAvg = new HashMap<>();
        for (Pupil i : pupils) {
            double avg = 0D;
            for (Subject j : i.subjects()) {
                avg += j.getScore();
            }
            pupilAvg.put(i.name(), avg);
        }
        return pupilAvg;
    }

    public static double averageScore(List<Pupil> pupils) {
        Map<String, Double> pupilsAvg = AnalyzeByMap.averageMapByPupil(pupils);
        double rsl = 0D;
        for (String key : pupilsAvg.keySet()) {
            rsl += pupilsAvg.get(key);
        }
        return rsl / (pupils.size() * pupils.get(0).subjects().size());
    }

    public static List<Label> averageScoreByPupil(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Double> pupilsAvg = AnalyzeByMap.averageMapByPupil(pupils);
        for (String key : pupilsAvg.keySet()) {
             rsl.add(new Label(key, pupilsAvg.get(key) / pupils.size()));
        }
        return rsl;
    }

    private static Map averageMapBySubject(List<Pupil> pupils) {
        Map<String, Integer> interimMap = new LinkedHashMap<>();
        List<Label> rsl = new ArrayList<>();
        for (Pupil i : pupils) {
            for (Subject j : i.subjects()) {
                interimMap.computeIfPresent(j.getName(), (k, t) -> t + j.getScore());
                interimMap.putIfAbsent(j.getName(), j.getScore());
            }
        }
        return interimMap;
    }

    public static List<Label> averageScoreBySubject(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Integer> interimMap = AnalyzeByMap.averageMapBySubject(pupils);
        for (String key : interimMap.keySet()) {
            rsl.add(new Label(key, interimMap.get(key) / pupils.size()));
        }
        return rsl;
    }

    public static Label bestStudent(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Double> interimMap = averageMapByPupil(pupils);
        for (String key : interimMap.keySet()) {
            rsl.add(new Label(key, interimMap.get(key)));
        }
        Collections.sort(rsl);
        return rsl.get(rsl.size() - 1);
    }

    public static Label bestSubject(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Integer> interimMap = AnalyzeByMap.averageMapBySubject(pupils);
        for (String key : interimMap.keySet()) {
            rsl.add(new Label(key, interimMap.get(key)));
        }
        Collections.sort(rsl);
        return rsl.get(rsl.size() - 1);
    }
}