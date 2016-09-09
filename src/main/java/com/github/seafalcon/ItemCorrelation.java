package com.github.seafalcon;

import java.util.ArrayList;
import java.util.List;

public class ItemCorrelation {

    public static final int FACTOR_SIZE = 5;

    public static double compute(Item a, Item b){
        // 같은 속성들끼리 비교
        // 같은 속성의 값을 비교하여 값의 차이가 크면 클수록 연관성이 작아짐
        // 값의 차이가 작을수록 연관성이 커짐
        // 속성에 따라서 차이의 가중치는 달라짐

        /*
            AP 70 <> HP 500, DP 50 사이의 상관관계는 33%
            AP 70 <> DP 100  상관관계는 0
            AP 70 <> HP 800 상관관계는 50%
            AP 120 <> HP 500, MR 50 상관관계는 67%
            AP 120 <> AD 80 상관관계가 서로 1이다 (반대로 테스트해도 같다)
            AP 70, MR 50 <> AP 120 상관관계 서로 1
            AD 60, MR 50 <> AP 50 상관관계 서로 1 (b,a)
            AD 60 <> AD 80 상관관계 서로 1
            AD 60 <> DP 100 상관관계 서로 1
            AD 60 <> AP 100 상관관계 서로 1
            HP 500 <> DP 100, HP 100 상관관계 서로 1
            DP 100 <> DP 100 상관관계 서로 1
            DP 100 <> DP 60, MR 60 상관관계 서로 1
            */

        List<Double> correlations = new ArrayList<Double>();

        if(a.getMagicalOffensive() > 0){
            if(b.getMagicalOffensive() > 0){
                correlations.add(1.0);
            }
            if(b.getPhysicalOffensive() > 0){
                correlations.add(1.0);
            }
            if(b.getHp() > 0){
                correlations.add(0.5);
            }
            if(b.getPhysicalDefensive() > 0){
                correlations.add(0.0);
            }
            if(b.getMagicalDefensive() > 0){
                correlations.add(1.0);
            }
        }
        if(a.getPhysicalOffensive() > 0){
            if(b.getMagicalOffensive() > 0){
                correlations.add(1.0);
            }
            if(b.getPhysicalOffensive() > 0){
                correlations.add(1.0);
            }
            if(b.getHp() > 0){
                correlations.add(2.0);
            }
            if(b.getPhysicalDefensive() > 0){
                correlations.add(1.0);
            }
            if(b.getMagicalDefensive() > 0){
                correlations.add(2.0);
            }
        }
        if(a.getHp() > 0){
            if(b.getMagicalOffensive() > 0){
                correlations.add(0.5);
            }
            if(b.getPhysicalOffensive() > 0){
                correlations.add(2.0);
            }
            if(b.getHp() > 0){
                correlations.add(1.0);
            }
            if(b.getPhysicalDefensive() > 0){
                correlations.add(1.0);
            }
            if(b.getMagicalDefensive() > 0){
                correlations.add(2.0);
            }
        }
        if(a.getPhysicalDefensive() > 0){
            if(b.getMagicalOffensive() > 0){
                correlations.add(0.0);
            }
            if(b.getPhysicalOffensive() > 0){
                correlations.add(1.0);
            }
            if(b.getHp() > 0){
                correlations.add(1.0);
            }
            if(b.getPhysicalDefensive() > 0){
                correlations.add(1.0);
            }
            if(b.getMagicalDefensive() > 0){
                correlations.add(1.0);
            }
        }
        if(a.getMagicalDefensive() > 0) {
            if (b.getMagicalOffensive() > 0) {
                correlations.add(1.0);
            }
            if (b.getPhysicalOffensive() > 0) {
                correlations.add(2.0);
            }
            if (b.getHp() > 0) {
                correlations.add(1.0);
            }
            if (b.getPhysicalDefensive() > 0) {
                correlations.add(1.0);
            }
            if (b.getMagicalDefensive() > 0){
                correlations.add(2.0);
            }
        }

        double sum = 0;
        for(Integer i=0; i<correlations.size(); i++) {
            sum += correlations.get(i);
        }
        sum /= correlations.size();
        System.out.println("sum divide: " + sum);

        if(sum == 0.25){
            return 1 / (FACTOR_SIZE-correlations.size());
        }else if(sum == 0.75){
            return 1 - (FACTOR_SIZE / (5-correlations.size()));
        }else{
            return sum;
        }
    }
}
