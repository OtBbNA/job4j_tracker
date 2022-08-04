package ru.job4j.map;

import java.util.*;

public class AnalyzeByMap {

    private static double averageIfKey0SumIfKey1ByPupil(Pupil pupil, int key) {
        double pupilavg = 0D;
        for (Subject j : pupil.subjects()) {
            pupilavg += j.getScore();
        }
        return key == 0 ? pupilavg / pupil.subjects().size() : pupilavg;
    }

    public static double averageScore(List<Pupil> pupils) {
        double rsl = 0D;
        for (Pupil i : pupils) {
            rsl += AnalyzeByMap.averageIfKey0SumIfKey1ByPupil(i, 0);
        }
        return rsl / pupils.size();
    }

    public static List<Label> averageScoreByPupil(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        for (Pupil i : pupils) {
            rsl.add(new Label(i.name(), AnalyzeByMap.averageIfKey0SumIfKey1ByPupil(i, 0)));
        }
        return rsl;
    }

    private static List<Label> averageIfKey0SumIfKey1ScoreBySubject(List<Pupil> pupils, int key) {
        Map<String, Integer> interimMap = new LinkedHashMap<>();
        List<Label> rsl = new ArrayList<>();
        for (Pupil i : pupils) {
            for (Subject j : i.subjects()) {
                interimMap.computeIfPresent(j.getName(), (k, t) -> t + j.getScore());
                interimMap.putIfAbsent(j.getName(), j.getScore());
            }
        }
        if (key == 0) {
            for (String i : interimMap.keySet()) {
                rsl.add(new Label(i, interimMap.get(i) / pupils.size()));
            }
        } else if (key == 1) {
            for (String i : interimMap.keySet()) {
                rsl.add(new Label(i, interimMap.get(i)));
            }
        }
        return rsl;
    }

    public static List<Label> averageScoreBySubject(List<Pupil> pupils) {
        return AnalyzeByMap.averageIfKey0SumIfKey1ScoreBySubject(pupils, 0);
    }

    public static Label bestStudent(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        for (Pupil i : pupils) {
            rsl.add(new Label(i.name(), AnalyzeByMap.averageIfKey0SumIfKey1ByPupil(i, 1)));
        }
        Collections.sort(rsl);
        return rsl.get(rsl.size() - 1);
    }

    public static Label bestSubject(List<Pupil> pupils) {
        List<Label> rsl = AnalyzeByMap.averageIfKey0SumIfKey1ScoreBySubject(pupils, 1);
        Collections.sort(rsl);
        return rsl.get(rsl.size() - 1);
    }
}