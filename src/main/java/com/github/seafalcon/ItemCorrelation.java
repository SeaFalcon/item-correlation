package com.github.seafalcon;

public class ItemCorrelation {

    public static double compute(Item a, Item b) {
        double result = 0.0;
        double offensiveToHpOtherDefenseWeight  = 2.0/3.0;
        double offensiveToHpSameDefenseWeight = offensiveToHpOtherDefenseWeight  * 2;

        if(a.getMagicalOffensive() > 0){
            if(b.getMagicalOffensive() > 0 || b.getPhysicalOffensive() > 0) {
                result = 1.0;
            }else{
                if (b.getHp() > 0){
                    result = 0.5;
                    if (b.getPhysicalDefensive() > 0) {
                        result = 0.5 * offensiveToHpOtherDefenseWeight;
                    }
                    if (b.getMagicalDefensive() > 0) {
                        result = 0.5 * offensiveToHpSameDefenseWeight;
                    }
                }else {
                    if (b.getPhysicalDefensive() > 0) {
                        result = 0.0;
                    }
                    if (b.getMagicalDefensive() > 0) {
                        result = 1.0;
                    }
                }
            }
        }
        if(a.getPhysicalOffensive() > 0){
            if(b.getPhysicalOffensive() > 0 || b.getMagicalOffensive() > 0){
                result = 1.0;
            }else{
                if (b.getHp() > 0) {
                    result = 0.0;
                    if (b.getPhysicalDefensive() > 0) {
                        result = 0.5 * offensiveToHpSameDefenseWeight;
                    }
                    if (b.getMagicalDefensive() > 0) {
                        result = 0.5 * offensiveToHpOtherDefenseWeight;
                    }
                }else {
                    if (b.getPhysicalDefensive() > 0) {
                        result = 1.0;
                    }
                    if (b.getMagicalDefensive() > 0) {
                        result = 0.0;
                    }
                }
            }
        }
        if(a.getHp() > 0){
            if(b.getHp() > 0){
                result = 1.0;
            }else{
                if (b.getMagicalOffensive() > 0) {
                    result = 0.5;
                }
                if (b.getPhysicalOffensive() > 0) {
                    result = 0.5;
                }
                if (b.getPhysicalDefensive() > 0 || b.getMagicalDefensive() > 0) {
                    result = 1.0;
                }
            }
        }
        if(a.getPhysicalDefensive() > 0){
            if(b.getPhysicalDefensive() > 0){
                result = 1.0;
            }else {
                if (b.getMagicalOffensive() > 0) {
                    result = 0.0;
                    if(a.getHp() > 0){
                        result = 0.5 * offensiveToHpOtherDefenseWeight;
                    }
                }
                if (b.getPhysicalOffensive() > 0) {
                    result = 1.0;
                    if(a.getHp() > 0){
                        result = 0.5 * offensiveToHpSameDefenseWeight;
                    }
                }
                if (b.getHp() > 0 || b.getMagicalDefensive() > 0) {
                    result = 1.0;
                }
            }
        }
        if(a.getMagicalDefensive() > 0) {
            if (b.getMagicalDefensive() > 0){
                result = 1.0;
            }else{
                if (b.getMagicalOffensive() > 0) {
                    result = 1.0;
                    if(a.getHp() > 0){
                        result = 0.5 * offensiveToHpSameDefenseWeight;
                    }
                }
                if (b.getPhysicalOffensive() > 0) {
                    result = 0.0;
                    if(a.getHp() > 0){
                        result = 0.5 * offensiveToHpOtherDefenseWeight;
                    }
                }
                if (b.getHp() > 0 || b.getPhysicalDefensive() > 0) {
                    result = 1.0;
                }
            }
        }

        return Double.parseDouble(String.format("%.2f",result));
    }
}
