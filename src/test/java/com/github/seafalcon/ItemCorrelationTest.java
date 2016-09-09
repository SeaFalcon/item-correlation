package com.github.seafalcon;

import org.junit.Test;

public class ItemCorrelationTest {
    @Test
    public void apToAntiAdHp(){
        Item a = new Item();
        a.setMagicalOffensive(70);
        Item b = new Item();
        b.setHp(500);
        b.setPhysicalDefensive(50);

        assert(ItemCorrelation.compute(a, b) == 0.33);
    }

    @Test
    public void apToAntiAd(){
        Item a = new Item();
        Item b = new Item();
        a.setMagicalOffensive(70);
        b.setPhysicalDefensive(100);

        assert(ItemCorrelation.compute(a, b) == 0);
    }

    @Test
    public void apToHp(){
        Item a = new Item();
        Item b = new Item();
        a.setMagicalOffensive(70);
        b.setHp(800);

        assert(ItemCorrelation.compute(a, b) == 0.5);
    }

    @Test
    public void apToAntiApHp(){
        Item a = new Item();
        Item b = new Item();
        a.setMagicalOffensive(120);
        b.setHp(500);
        b.setMagicalDefensive(50);

        assert(ItemCorrelation.compute(a, b) == 0.67);
    }

    @Test
    public void apToAd(){
        Item a = new Item();
        Item b = new Item();
        a.setMagicalOffensive(120);
        b.setPhysicalOffensive(80);

        assert(ItemCorrelation.compute(a, b) == 1);
        assert(ItemCorrelation.compute(b, a) == 1);
    }

    @Test
    public void apAntiApToAp(){
        Item a = new Item();
        Item b = new Item();
        a.setMagicalOffensive(70);
        a.setMagicalDefensive(50);
        b.setMagicalOffensive(120);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void adAntiApToAp(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalOffensive(60);
        a.setMagicalDefensive(50);
        b.setMagicalOffensive(50);

        assert(ItemCorrelation.compute(b, a) == 1);
    }

    @Test
    public void adToAd(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalOffensive(60);
        b.setPhysicalOffensive(80);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void adToAntiAd(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalOffensive(60);
        b.setPhysicalDefensive(100);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void adToAp(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalOffensive(60);
        b.setMagicalOffensive(100);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void hpToAntiAd(){
        Item a = new Item();
        Item b = new Item();
        a.setHp(500);
        b.setPhysicalDefensive(100);
        b.setHp(100);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void antiAdToAntiAd(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalDefensive(100);
        b.setPhysicalDefensive(100);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

    @Test
    public void antiAdToAntiAdAntiAp(){
        Item a = new Item();
        Item b = new Item();
        a.setPhysicalDefensive(100);
        b.setPhysicalDefensive(60);
        b.setMagicalDefensive(60);

        assert(ItemCorrelation.compute(a, b) == 1);
    }

}