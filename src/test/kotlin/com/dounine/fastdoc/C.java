package com.dounine.fastdoc;

import java.util.ArrayList;
import java.util.List;

public class C {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("");

        list.stream().filter(l->l.toString().equals("`"));
    }
}
