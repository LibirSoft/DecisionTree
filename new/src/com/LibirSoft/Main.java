package com.LibirSoft;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Patient> patientArrayList = new ArrayList<Patient>();

        try {
//reading data set
            BufferedReader in =
                    new BufferedReader(
                            new FileReader("breast-cancer-wisconsin.data")
                    );

            StringTokenizer st;

// our decleration for getting data and detecting null datas
            Patient currentpatient;

            int[] data;
            int attirbutecount = 11;
            int datapointer = 0;
            data = new int[attirbutecount];


            String line;
            String token;

            boolean isnull = true;

            while ((line = in.readLine()) != null) {
                datapointer = 0;
                isnull = true;


                st = new StringTokenizer(line, ",");


                while (st.hasMoreTokens()) {
                    token = st.nextToken();

                    if (!token.equals("?")) {
                        data[datapointer] =
                                Integer.parseInt(token);
                        datapointer++;
                    } else {

                        isnull = false;
                    }

                }


                if (isnull) {

                    currentpatient = new Patient(data);


                    patientArrayList.add(currentpatient);
                }
            }


            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int malignantcount = 0;
        int benign = 0;

// counting malignant patients
        for (Patient h : patientArrayList) {

            if (h.mOrb()) {
                malignantcount++;
            } else {
                benign++;
            }
        }

        System.out.println("Malignant: " + malignantcount
                + " Benign: " + benign);

// we need to sure abaout all data sets (training and test) have both patient
        int tariningMalignant = malignantcount / 2;
        int tariningBenign = benign / 2;

        ArrayList<Patient> training = new ArrayList<Patient>();
        ArrayList<Patient> test = new ArrayList<Patient>();


        Collections.sort(patientArrayList, new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                if (o1.mOrb() == true && o2.mOrb() == false) {
                    return 1;
                }
                if (o1.mOrb() == false && o2.mOrb() == true) {
                    return -1;
                } else {
                    return 0;
                }
            }


        });

        int listindex = 0;

        for (int i = 0; i < tariningBenign; i++) {
            training.add(patientArrayList.get(listindex));
            listindex++;
            test.add(patientArrayList.get(listindex));
            listindex++;
        }

        for (int i = 0; i < tariningMalignant; i++) {
            training.add(patientArrayList.get(listindex));
            listindex++;
            test.add(patientArrayList.get(listindex));
            listindex++;
        }

        System.out.println("Training dataset:");
        for (Patient h : training) {
            System.out.println(h);
        }

        System.out.println("********************************");
        System.out.println("Test dataset:");
        for (Patient h : test) {
            System.out.println(h);
        }
        System.out.println("Testing *********************************************");
        ArrayList<Patient> biggerlist= new ArrayList<>();
        ArrayList<Patient> smallerlist= new ArrayList<>();

        DecisionTree tree = new DecisionTree(patientArrayList);
        tree.splitlist(patientArrayList, 1, 4);

        biggerlist = tree.getBigger();
        smallerlist = tree.getSmaller();
        for (Patient p :smallerlist){
            System.out.println(p);
        }
    }
}

