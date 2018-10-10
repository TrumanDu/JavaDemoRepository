package com.aibibang.demo.LanguageDemo;
/** 
* @author: Truman.P.Du 
* @since: 2016年11月24日 下午11:28:56 
* @version: v1.0
* @description:
*/
import java.math.BigInteger;  
import java.util.StringTokenizer;  
public class SimHash {  
    private String tokens;  
    private BigInteger strSimHash;  
    private int hashbits = 128;  
    public SimHash(String tokens) {  
        this.tokens = tokens;  
        this.strSimHash = this.simHash();  
    }  
    public SimHash(String tokens, int hashbits) {  
        this.tokens = tokens;  
        this.hashbits = hashbits;  
        this.strSimHash = this.simHash();  
    }  
    public BigInteger simHash() {  
        int[] v = new int[this.hashbits];  
        StringTokenizer stringTokens = new StringTokenizer(this.tokens);  
        while (stringTokens.hasMoreTokens()) {  
            String temp = stringTokens.nextToken();  
            BigInteger t = this.hash(temp);  
            for (int i = 0; i < this.hashbits; i++) {  
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);  
                 if (t.and(bitmask).signum() != 0) {  
                    v[i] += 1;  
                } else {  
                    v[i] -= 1;  
                }  
            }  
        }  
        BigInteger fingerprint = new BigInteger("0");  
        for (int i = 0; i < this.hashbits; i++) {  
            if (v[i] >= 0) {  
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));  
            }  
        }  
        return fingerprint;  
    }  
    private BigInteger hash(String source) {  
        if (source == null || source.length() == 0) {  
            return new BigInteger("0");  
        } else {  
            char[] sourceArray = source.toCharArray();  
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);  
            BigInteger m = new BigInteger("1000003");  
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(  
                    new BigInteger("1"));  
            for (char item : sourceArray) {  
                BigInteger temp = BigInteger.valueOf((long) item);  
                x = x.multiply(m).xor(temp).and(mask);  
            }  
            x = x.xor(new BigInteger(String.valueOf(source.length())));  
            if (x.equals(new BigInteger("-1"))) {  
                x = new BigInteger("-2");  
            }  
            return x;  
        }  
    }  
    public int hammingDistance(SimHash other) {  
        BigInteger m = new BigInteger("1").shiftLeft(this.hashbits).subtract(  
                new BigInteger("1"));  
        BigInteger x = this.strSimHash.xor(other.strSimHash).and(m);  
        int tot = 0;  
         while (x.signum() != 0) {  
            tot += 1;  
            x = x.and(x.subtract(new BigInteger("1")));  
        }  
        return tot;  
    }  
    public static void main(String[] args) {  
        String s = "出售:7111厂区 3室2厅1卫 35万元——————此信息来自手机发布";  
        SimHash hash1 = new SimHash(s, 128);  
        System.out.println(hash1.strSimHash + "  " + hash1.strSimHash.bitLength());  
        s = "出售:7111厂区 3室2厅1卫 38万元——————此信息来自手机发布";  
        SimHash hash2 = new SimHash(s, 128);  
        System.out.println(hash2.strSimHash+ "  " + hash2.strSimHash.bitCount());  
        s = "家电齐全，可直接入祝（另外还有一车位我买成55000元，和房一起卖直降15000元）有意者联系我！！！";  
        SimHash hash3 = new SimHash(s, 128);  
        System.out.println(hash3.strSimHash+ "  " + hash3.strSimHash.bitCount());  
        System.out.println("============================");  
        System.out.println(hash1.hammingDistance(hash2));  
        System.out.println(hash1.hammingDistance(hash3));  
    }  
} 
