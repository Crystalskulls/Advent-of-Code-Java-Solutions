package com.github.crystalskulls.aoc.common;

import java.math.BigInteger;

public class Math {

    public static BigInteger lcm(BigInteger n, BigInteger m) {
        BigInteger gcd = n.gcd(m);
        BigInteger product = n.multiply(m).abs();
        return product.divide(gcd);
    }

    public static BigInteger lcm(BigInteger... bigIntegers) {
        BigInteger lcm = lcm(bigIntegers[0], bigIntegers[1]);
        for (int i = 2; i < bigIntegers.length; i++) {
            lcm = lcm(lcm, bigIntegers[i]);
        }
        return lcm;
    }
}
