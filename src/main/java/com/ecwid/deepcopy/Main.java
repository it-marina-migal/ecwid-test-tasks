package com.ecwid.deepcopy;

import com.ecwid.deepcopy.deep_copy.DeepCopy;
import com.ecwid.deepcopy.objects.Man;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Man man = new Man("John", 30, new ArrayList<>(){
            {
                add("1984");
                add("Brave New World");
            }
        });

        Man copiedMan = DeepCopy.copy(man);

        System.out.println("Original: " + man);
        System.out.println("Copied: " + copiedMan);

        copiedMan.setName("Doe");
        copiedMan.getFavoriteBooks().add("Fahrenheit 451");

        System.out.println("After modification:");
        System.out.println("Original: " + man);
        System.out.println("Copied: " + copiedMan);
    }
}
