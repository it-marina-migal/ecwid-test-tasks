package com.ecwid.deepcopy.deep_copy.objects;

public class Family {

    private String surname;
    private Person child;

    public Family(String surname, com.ecwid.deepcopy.deep_copy.objects.Person child) {
        this.surname = surname;
        this.child = child;
    }

    public String getSurname() {
        return surname;
    }

    public com.ecwid.deepcopy.deep_copy.objects.Person getChild() {
        return child;
    }
}
