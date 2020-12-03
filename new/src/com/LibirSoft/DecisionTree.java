package com.LibirSoft;

import java.util.ArrayList;

public class DecisionTree {

    TreeNode root;
    ArrayList<Patient> bigger = new ArrayList<>();
    ArrayList<Patient> smaller = new ArrayList<>();
    boolean[] calculatedindex;
    int malignant;
    int benign;


    public DecisionTree(ArrayList<Patient> list) {
        root = new TreeNode(list);
        calculatedindex = new boolean[10];
        for (int i = 0; i < 10; i++) {

            calculatedindex[i] = true;

        }
        // we dont need id index
        calculatedindex[0] = false;
    }

    public void compile() {


    }

    public ArrayList<Patient> getBigger() {
        return bigger;
    }

    public ArrayList<Patient> getSmaller() {
        return smaller;
    }

    // we are counting both patient type here
    public int[] countlist(ArrayList<Patient> _list) {
        malignant = 0;
        benign = 0;
        for (Patient p : _list) {
            if (p.mOrb() == true) {
                malignant++;

            } else {
                benign++;
            }
        }
        int[] result = new int[2];
        result[0] = malignant;
        result[1] = benign;
        return result;
    }

    // we are spliting list what we want to be
    public void splitlist(ArrayList<Patient> _list, int index, int threshold) {

        if (index >= 10 || index < 0) {
            System.out.println("index is wrong");
            return;
        }

        for (Patient p : _list) {
            if (p.attiributes[index] <= threshold) {
                smaller.add(p);
            } else {
                bigger.add(p);
            }


        }

    }

    //we divide the array imaginatively to determine the entropy
    public int[] testSplitlist(ArrayList<Patient> _list, int index, int threshold) {

        if (index >= 10 || index < 0) {
            System.out.println("index is wrong");
            return null;
        }
        int malignantSmaller = 0;
        int benignSmaller = 0;
        int malignantBigger = 0;
        int benignBigger = 0;

        for (Patient p : _list) {
            if (p.attiributes[index] <= threshold) {
                if (p.mOrb()) {
                    malignantSmaller++;
                } else {
                    benignSmaller++;
                }

            } else {
                if (p.mOrb()) {
                    malignantBigger++;
                } else {
                    benignBigger++;
                }

            }


        }

        int[] result = new int[4];
        result[0] = malignantSmaller;
        result[1] = benignSmaller;
        result[2] = malignantBigger;
        result[3] = benignBigger;


        return result;
    }

    public int findentropy(ArrayList<Patient> _list, int index) {
        int threshold = 1;
        float bestentropy;
        float _entropy;
        int[] presult = testSplitlist(_list, index, threshold);
        bestentropy = calculatEtropy(presult);
        for (int i = 2; i <= 10; i++) {
            presult = testSplitlist(_list, index, threshold);
            _entropy = calculatEtropy(presult);

            if (_entropy < bestentropy) {
                bestentropy = _entropy;
                threshold = i;
            }

        }

        return 0;
    }

    public float calculatEtropy(int[] _result) {
        int malignantSmaller = _result[0];
        int benignSmaller = _result[1];
        int malignantBigger = _result[2];
        int benignBigger = _result[3];

        float entropy;

        float allSmallers = malignantSmaller + benignSmaller;
        float allBiggers = malignantBigger + benignBigger;
// smallers malignant rate
        float sBrate = benignSmaller / allSmallers;
        float sMrate = 1f - sBrate;

        float bBrate = benignBigger / allBiggers;
        float bMrate = 1f - bBrate;


// entropy = - p(a) * log(p(a)) - p(b) * log(p(b))
        float pa = sBrate;
        float logpa;
        float pb = sMrate;
        float logpb;
        boolean sBenginrate = sBrate >= bBrate;
        if (sBenginrate) {
            // küçükler kümesindekilere zararsız, büyüklerdekine zararlı demiş olduk
            pa = sBrate;
            pb = bBrate;

            entropy = -pa * log2(pa) - pb * log2(pb);
        } else {
            // küçükler zararlı, büyükler zararsızdır
            pa = sMrate;
            pb = bMrate;

            entropy = -pa * log2(pa) - pb * log2(pb);
        }

        return entropy;
    }

    private float log2(float x) {
        if (x == 0f) {
            return 0f;
        } else {
            return (float) (Math.log((double) x) / Math.log((double) 2));
        }
    }

}