package io.object;

import java.io.*;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/14 6:28
 */
public class Person implements Serializable {

    private String id;
    private String name;
    private int age;



    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        java.io.ObjectInputStream.GetField fields =
                in.readFields();

        String name = (String) fields.get("name", "default");
        this.name = name;

//        this.id = in.readUTF();
//        this.name = in.readUTF();
//        this.age = in.readInt();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {


//        out.writeUTF(id);
//        out.writeUTF(name);
//        out.writeInt(age);
    }


    private final static String file = "F:\\dev\\workspace\\demo-1.8.0_311\\resource\\person";
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

//        ObjectOutputStream out = new BufferedOutputStream(System.out);
        out.writeObject(new Person("1", "shm", 33));
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Person person = (Person) in.readObject();

        System.out.println(person);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                '}';
    }
}
