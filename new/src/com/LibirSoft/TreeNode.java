package com.LibirSoft;

import java.util.ArrayList;

public class TreeNode {

    int patientcount;

    int branch;

    ArrayList<Patient> list;

    TreeNode smallernode;
    TreeNode biggerbode;

    public TreeNode(ArrayList<Patient> patientArrayList) {
        this.list = patientArrayList;
    }

}
