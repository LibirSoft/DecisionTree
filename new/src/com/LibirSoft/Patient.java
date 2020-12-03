package com.LibirSoft;

public class Patient {
    // girdiler
    int[] attiributes;
    int attiributecount = 10;

    // çıktı
    boolean ismalignant;

    public Patient( int[] gelenVeriler ) {

        attiributes = new int[attiributecount];
        for(int i = 0; i < attiributecount; i++) {
            attiributes[i] = gelenVeriler[i];
        }

        if(gelenVeriler[attiributecount] == 2) {
            ismalignant = false;
        } else {
            ismalignant = true;
        }

    }
// this method return string to our patient attirbute
    public String toString() {
        String result = "";

        for(int i = 0; i < attiributecount; i++) {
            result += "" + attiributes[i] + " ";
        }

        result += "" + ismalignant;

        return result;
    }
// this method return us what is the cancer type malignant or bening
    public boolean mOrb() {


        return ismalignant;
    }
}
