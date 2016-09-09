package com.github.seafalcon;

public class ItemCorrelation {

    public static double compute(Item a, Item b) {
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

            테스트 케이스에서 알 수 있는 것은
            - 수치보다는 속성차이로 상관관계가 결정되는 것 같다
            - 마법공격과 물리방어처럼 속성이 다른 공격/방어 수치는 상관관계가 0이다.
            - 반대로 속성이 같은 공격/방어 수치는 상관관계가 1이다.
            - 체력이 단독으로 붙은 아이템은 공격 수치와 0.5의 상관관계를 가지고 있다.
            - 그러나 체력 수치에 방어력이 추가되면 차이가 생긴다.
            - 마법공격-마법방어 아이템에서 방어아이템에 체력이 붙으면 상관관계가 0.67이 된다. (반대의 경우도 동일하게 추정)
            - 마법공격-물리방어 아이템에서 방어아이템에 체력이 붙으면 상관관계가 0.33이 된다. (반대의 경우도 동일하 추정)
            - 공격은 물리/마법 속성이 같거나 달라도 상관관계가 1이다. (다른 수치에 영향받지 않는다.)
            - 같은 속성끼리는 상관관계가 1이다.
            - 방어 아이템도 물리나 마법에 상관없이 상관관계가 1이다.

            복합적인 옵션을 가지고 있는 아이템을 비교하는 과정에서
            AP 120 <> HP 500, MR 50 상관관계는 0.67
            AP 70 <> HP 500, DP 50 사이의 상관관계는 0.33

            공격력과 체력은 0.5의 상관관계가 있다.
            마법공격력과 체력사이에서 물리방어/마법방어 옵션이 추가될때 상관관계가 변화되므로
            마법방어 / 물리방어에 따른 가중치를 부여했다

            offensiveToHpSameDefenseWeight, offensiveToHpOtherDefenseWeight 변수를 선언하여
            0.5 * offensiveToHpSameDefenseWeight = 0.67
            0.5 * offensiveToHpOtherDefenseWeight = 0.33
            위 결과가 나오도록 각각의 변수에 값을 대입했다.

            아래 조건문에서는 a의 값을 하나하나 불러와서 0이상인지 여부를 파악한다.
            0 보다 크면 비교하기 위한 값이 있는 경우이기 때문에 b의 모든 값과 비교를 하게된다.
            0 보다 크지 않으면 비교하지 않고 그냥 넘어가게 된다.
            그리고 테스트 케이스에서 발견한 조건들을 활용하여 상관관계를 분석한다
            우선적으로 서로 같은 속성이면 1을 반환하고 만약 공격속성을 비교할 경우에는 속성이 달라도 1을 반환시킨다
            다른 속성일 경우 상관관계가 1이나 0이 나오는 수치들은 그대로 반환시키고
            복합옵션을 가지고 있는 아이템에는 가중치를 넣어 반환시킨다.

            마지막으로 소수점 둘째자리 까지 표기하기 위해 String.format함수를 사용하여 반환했다.


        */

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
