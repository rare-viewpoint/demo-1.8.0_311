package util;

import jdk.nashorn.internal.runtime.BitVector;
import sun.security.util.BitArray;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.BitSet;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 5:14
 */
public final class PrintUtil {

    private PrintUtil(){}

    public static void circleScanner(Consumer<String> consumer){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            consumer.accept(scanner.nextLine());
        }
    }

    public static void main(String[] args) {

        PrintStream out = new PrintStream(new FileOutputStream(FileDescriptor.out));
        out.println("123123");

        PrintStream err = new PrintStream(new FileOutputStream(FileDescriptor.err));
        err.println("error");

        Scanner in = new Scanner(new FileInputStream(FileDescriptor.in));

        while (in.hasNextLine()){
            System.out.println(in.nextLine());
        }

    }
}


