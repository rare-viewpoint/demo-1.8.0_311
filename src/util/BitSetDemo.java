package util;

import java.nio.LongBuffer;
import java.util.BitSet;

/**
 * @author shm
 * @desc ******
 * @Date 2022/2/18 5:11
 */
public class BitSetDemo {

    public static void main(String[] args) {
        long[] buffer = new long[2];
        buffer[0] = 22L;
        buffer[1] = 22L;
        BitSet bitSet = BitSet.valueOf(buffer);
        System.out.println(bitSet);
//        bitSet.clear();
//        System.out.println(bitSet);
//        bitSet.stream().forEach(System.out::println);
//        System.out.println(bitSet.cardinality());
        bitSet.xor(new BitSet());
        System.out.println(bitSet);
    }

}
