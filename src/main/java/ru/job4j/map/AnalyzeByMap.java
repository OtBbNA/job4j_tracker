package ru.job4j.map;

import java.util.*;

public class AnalyzeByMap {

    private static Map<String, Double> averageMapByPupil(List<Pupil> pupils) {
        Map<String, Double> pupilAvg = new HashMap<>();
        for (Pupil pupil : pupils) {
            double avg = 0D;
            for (Subject subject : pupil.subjects()) {
                avg += subject.getScore();
            }
            pupilAvg.put(pupil.name(), avg);
        }
        return pupilAvg;
    }

    public static double averageScore(List<Pupil> pupils) {
        Map<String, Double> pupilsAvg = AnalyzeByMap.averageMapByPupil(pupils);
        double rsl = 0D;
        int numItems = 0;
        for (String key : pupilsAvg.keySet()) {
            rsl += pupilsAvg.get(key);
        }
        for (Pupil pupil : pupils) {
            numItems += pupil.subjects().size();
        }
        return rsl / numItems;
    }

    public static List<Label> averageScoreByPupil(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Double> pupilsAvg = AnalyzeByMap.averageMapByPupil(pupils);
        for (String key : pupilsAvg.keySet()) {
             rsl.add(new Label(key, pupilsAvg.get(key) / pupils.size()));
        }
        return rsl;
    }

    private static Map<String, Integer> averageMapBySubject(List<Pupil> pupils) {
        Map<String, Integer> res = new LinkedHashMap<>();
        for (Pupil pupil : pupils) {
            for (Subject subject : pupil.subjects()) {
                res.merge(subject.getName(), subject.getScore(), (o, n) -> o + n);
            }
        }
        return res;
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