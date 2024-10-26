package com.compass.excercise.comparator.base;

import java.util.ArrayList;
import java.util.List;

public class BaseServiceTest {

    public static List<String[]> getContactInformation(){
        List<String[]> contactInformation = new ArrayList<>();

        contactInformation.add(new String[] { "contactID", "name", "name1", "email","postalZip","address" });
        contactInformation.add(new String[] { "1","Ciara","French","mollis.lectus.pede@outlook.net","39746","449-6990 Tellus. Rd." });
        contactInformation.add(new String[] { "2","Charles","Pacheco","nulla.eget@protonmail.couk","76837","Ap #312-8611 Lacus. Ave" });
        contactInformation.add(new String[] { "2","Charles","Pacheco","nulla.eget@protonmail.couk","76837","Ap #312-8611 Lacus. Ave" });
        contactInformation.add(new String[] { "2","Carlos" });
        contactInformation.add(new String[] { "2","Ciara","French", });

        return contactInformation;
    }

    public static List<String[]> getContactComparatorResult(){
        List<String[]> contactComparatorResult = new ArrayList<>();
        contactComparatorResult.add(new String[] { "ContactID Source","ContactID Match","Accuracy" });
        contactComparatorResult.add(new String[] { "1","2","Low"});
        contactComparatorResult.add(new String[] { "1","3","Low"});

        return contactComparatorResult;
    }
}
