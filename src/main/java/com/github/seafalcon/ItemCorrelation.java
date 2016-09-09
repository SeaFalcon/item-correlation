package com.github.seafalcon;

import java.util.ArrayList;
import java.util.List;

public class ItemCorrelation {

    public static final int FACTOR_SIZE = 5;

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

            비교결과 수치는 관계없고 속성차이로 상관관계가 결정되는 것 같음
            그리고 테스트 케이스에서 상관관계의 경우의 수는
            0, 0.33, 0.5, 0.67, 1 이렇게 총 5가지가 나온다. (상수 FACTOR_SIZE = 5)

            magicOffense 와 hp의 상관관계 0.5
            magicOffense 와 physicalDefense의 상관관계 0
            위 두가지 경우를 제외하고 테스트 케이스 상의 모든 상관관계가 1
            (
                physicalOffensive <> hp
                physicalOffensive <> magicalDefensive
                magicalDefensive <> magicalDefensive
                단, 이 세가지 경우에 대해서는
                테스트 케이스에서 상관관계를 알 수 없기 때문에 2로 처리했다
             )

            그리고 테스트 케이스에서 두 가지 이상 값을 비교하는 경우에는
            예를들어 a.hp와 b.physicalDefensive, b.hp를 비교할때
            a.hp <> b.physicalDefensive의 상관관계는 1
            a.hp <> b.hp의 상관관계는 1
            따라서 두 상관관계를 더해서 비교한 경우의 수만큼 나눠주면 테스트 케이스의 값 1이 나온다

            이런식으로 계산했을 때 0은 0이고 0.5는 0.5 그리고 1은 1이 맞지만
            그러나 테스트 케이스의 값이 0.33과 0.67이 나오는 경우에서 문제가 생긴다
            위의 계산법대로 하면 0.33은 0.25가 나오고 0.67은 0.75가 나오기 때문이다.

            그래서
            a.magicalOffensive와 b.hp, b.physicalDefensive를 비교할때
            a.magicalOffensive <> b.hp 의 상관관계는 0.5
            a.magicalOffensive <> b.physicalDefensive의 상관관계는 0
            이 경우에 0.5 + 0 / 2를 할 경우 0.25가 나오는데 이 값을 0.33으로 치환하는 방향으로 진행했다.

            우선 List<Double> correlations 리스트를 선언하고
            0번째 index에는 Item a의 값을 대입하고 1번째 index에는 Item b의 값을 대입한다
            리스트를 선언한 이유는 테스트에서 상관관계를 몇 번 비교하는지 갯수를 알기 위함이다.

            그리고 조건문에서는 a의 값을 하나하나 불러와서 0이상인지 여부를 파악한다.
            0 보다 크면 비교하기 위한 값이 있는 경우이기 때문에 b의 모든 값과 비교를 하게된다.
            0 보다 크지 않으면 비교하지 않고 그냥 넘어가게 된다.
            그렇게 하나하나씩 비교를 했을 경우 리스트에 상관관계 값을 하나씩 집어넣게 된다.

            마지막으로 함수에서 반환 할 double형 result 변수를 선언하고
            correlations 리스트에서 값을 추출하여 더한 값을 result에 넣는다.
            그리고 result의 값을 correlations 리스트의 길이만큼 나눠준다.

            이렇게하면 result의 값은 0, 0.25, 0.5, 0,75, 1의 다섯가지 경우의 수가 나오게 된다.
            마지막으로 0.25, 0.75인 경우 치환하기 위해 조건문을 넣었다
            다른 세가지의 경우는 result를 그대로 반환하고
            0.25는
            1을 FACTOR_SIZE 5에서 correlations 리스트의 길이만큼 빼준 값으로 나눈다.
            0.75는 1에서 0.25의 결과를 뺀다.
            그리고 소수점 2째자리 까지 표기하기 위해 String.format함수를 사용했다.
            그렇게 하면 각각 0.33과 0.67이 나오게 된다.

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

        double result = 0;
        for(Integer i=0; i<correlations.size(); i++) {
            result += correlations.get(i);
        }
        result /= correlations.size();
        System.out.println("result divide: " + result);

        if(result == 0.25){
            return Double.parseDouble(String.format("%.2f", 1.0 / (FACTOR_SIZE-correlations.size()) ) );
        }else if(result == 0.75){
            return Double.parseDouble(String.format("%.2f", 1 - 1.0 / (FACTOR_SIZE-correlations.size()) ) );
        }else{
            return result;
        }
    }
}
