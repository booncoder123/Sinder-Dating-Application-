package com.example.login_register.Model;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author tipsu
 */
public class RandomCollection<E>  {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public void add(double weight, E result) {
        if (weight <= 0 || map.containsValue(result))
            return;
        total += weight;
        map.put(total, result);
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }

}
