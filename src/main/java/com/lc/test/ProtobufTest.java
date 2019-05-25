package com.lc.test;

public class ProtobufTest {
    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("廖翀").setAddress("北京").build();
        byte[] bytes = student.toByteArray();
        DataInfo.Student lc = DataInfo.Student.parseFrom(bytes);
        System.out.println(lc);
    }
}
