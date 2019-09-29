package com.project.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DemandSupply {
    static ArrayList<String> ar = new ArrayList<String>(10);
    static int areas;
    static String[] Demand, SupplyPer, DriverPreferences, unBalanced;
    static int[] globalSuplyInNum, globalDemandInNum;
    static Scanner s;

    public static void main(String[] args) throws IOException {
        s = new Scanner(System.in);
        readFile();
        System.out.println(ar.get(0));
        Demand = ar.get(0).split("=")[1].split(",");
        //Demand = Demand[1].split(",");
        areas = Demand.length;

        System.out.println(ar.get(1));
        DriverPreferences = ar.get(1).split("=");
        DriverPreferences = DriverPreferences[1].split(",");

        System.out.println("No of Drivers = " + DriverPreferences.length);
        System.out.println("Supply of Drivers = " + Arrays.toString(DriverPreferences));
        CreateSupplyForEachArea();
        System.out.println("Demand for areas" + Arrays.toString(Demand));
        System.out.println("Supply of Drivers = " + Arrays.toString(SupplyPer));
        CreateUnbalancedSupplyDemand();
        System.out.println("unBalanced  = " + Arrays.toString(unBalanced));
        FindcoalitionPartners();
        globalDemandInNum = new int[areas];
        calculatiningDemandInNumbers();
        System.out.println("demand in number = " + Arrays.toString(globalDemandInNum));
        printingNashTable();
        costForSelfishGame();

        makingBalance();
        System.out.println("Supply of Drivers = " + Arrays.toString(DriverPreferences));

        while(  (maxOfUnbal() >= (100/DriverPreferences.length))&& Math.abs(minOfUnbal())>=(100/DriverPreferences.length)) {
            CreateSupplyForEachArea();
            System.out.println("Demand for areas" + Arrays.toString(Demand));
            System.out.println("Supply of Drivers = " + Arrays.toString(SupplyPer));
            CreateUnbalancedSupplyDemand();
            System.out.println("unBalanced  = " + Arrays.toString(unBalanced));
            FindcoalitionPartners();
            globalDemandInNum = new int[areas];
            calculatiningDemandInNumbers();
            System.out.println("demand in number = " + Arrays.toString(globalDemandInNum));
            costForSelfishGame();

            makingBalance();
            System.out.println("Supply of Drivers = " + Arrays.toString(DriverPreferences));

        }



    }



    private static int maxOfUnbal() {
        // TODO Auto-generated method stub
        int max = 0;
        for(String each:unBalanced) {
            if(Integer.parseInt(each)>max) {max=Integer.parseInt(each);}
        }
        return max;
    }

    private static int minOfUnbal() {
        // TODO Auto-generated method stub
        int min = 0;
        for(String each:unBalanced) {
            if(Integer.parseInt(each)<min) {min=Integer.parseInt(each);}
        }
        return min;
    }


    private static void makingBalance() {
        // TODO Auto-generated method stub
        System.out.println("inside breakloop"+(maxOfUnbal()-minOfUnbal())*100/DriverPreferences.length );
        int i = 1;
        for (String each : unBalanced) {
            if (Integer.parseInt(each) >= (100/DriverPreferences.length)) {
                ArrayList<Integer> temp = new ArrayList<>();
                int j = 1;
                System.out.print("Coalition Partners of Area  A" + i + " =");
                for (String EachDriver : DriverPreferences) {
                    if (Integer.parseInt(EachDriver.replace(" ", "")) == i) {
                        System.out.print("Driver" + j + " ");
                        temp.add(j);
                    }
                    j++;
                }
                int ar=0;System.out.println("");
                System.out.println("select an alternative area for a driver "+temp.get(0));
                for (String each2 : unBalanced) {

                    if(Integer.parseInt(each2)<=-(100/DriverPreferences.length)) {System.out.print("Area");System.out.print(ar+1+", ");
                    }ar++;
                }
                System.out.println("\nselect an area = ");
                int input = s.nextInt();
                DriverPreferences[temp.get(0)-1] = ""+input;
            }
            i++;
        }
    }

    private static void printingNashTable() {
        // TODO Auto-generated method stub
        System.out.print("s\\D    ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + 1 + "         ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(i + 1 + " - ");
            for (int j = 0; j < 10; j++) {
                if (i > j) {
                    System.out.print((i - j) * 5 + 10 + " , " + 10);
                } else if (j > i) {
                    System.out.print("10 , ");
                    System.out.print((j - i) * 5 + 10);
                } else {
                    System.out.print("10 , 10");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    private static void calculatiningDemandInNumbers() {
        // TODO Auto-generated method stub
        // globalSuplyInNum,SupplyPer
        // globalDemandInNum,Demand
        int i = 0;
        for (String each : Demand) {
            float t = Float.parseFloat(each.replace(" ", "")) * DriverPreferences.length / 100;
            // System.out.println("t = "+t);
            globalDemandInNum[i] = Math.round(t);
            i++;
        }

    }

    private static void costForSelfishGame() {
        // TODO Auto-generated method stub
        System.out.println();
        // globalSuplyInNum;
        // globalDemandInNum;
        // DriverPreferences;//string
        int costForAll = 0;
        for (int i = 0; i < DriverPreferences.length;) {
            int driver = Integer.parseInt(DriverPreferences[i].replace(" ", ""));
            int numOfDriverInSelectedDriverArea = globalSuplyInNum[driver - 1];
            int numOfCusmersInSelectedDriverArea = globalDemandInNum[driver - 1];
            int d = numOfDriverInSelectedDriverArea, c = numOfCusmersInSelectedDriverArea;
            int totalCost = 0;
            if (d > c) {
                totalCost = (d - c) * 5 + 20;
            } else if (d < c) {
                totalCost = (c - d) * 5 + 20;
            } else {
                totalCost = 20;
            }
            i++;
            System.out.println("driver" + "(c=" + c + "d=" + d + ")" + i + " = " + totalCost);
            costForAll = costForAll + totalCost;
        }
        System.out.println("totalCostForAll = " + costForAll);

    }

    private static void FindcoalitionPartners() {
        // TODO Auto-generated method stub
        int i = 1;
        for (String each : unBalanced) {
            if (Integer.parseInt(each) > 0) {
                int j = 1;
                System.out.print("Coalition Partners of Area  A" + i + " =");
                for (String EachDriver : DriverPreferences) {
                    if (Integer.parseInt(EachDriver.replace(" ", "")) == i) {
                        System.out.print("Driver" + j + " ");
                    }
                    j++;
                }
                System.out.println("");
            }
            i++;
        }

    }

    private static void CreateUnbalancedSupplyDemand() {
        int i = 0;
        unBalanced = new String[areas];
        for (String each : Demand) {

            unBalanced[i] = "" + (Integer.parseInt(SupplyPer[i]) - Integer.parseInt(each.replace(" ", "")));
            i++;
        }

    }

    private static void CreateSupplyForEachArea() {
        int[] Supply = new int[areas];
        for (String each : DriverPreferences) {
            int value = Integer.parseInt(each.replace(" ", ""));
            Supply[value - 1] = Supply[value - 1] + 1;
        }
        System.out.println("supply = " + Arrays.toString(Supply));
        SupplyPer = new String[areas];
        int sum = IntStream.of(Supply).sum();
        System.out.println("sum =" + sum);
        int i = 0;
        for (float each : Supply) {
            SupplyPer[i] = "" + Math.round(each * 100 / sum);
            i++;
        }
        globalSuplyInNum = Supply;
        System.out.println("percent array" + Arrays.toString(SupplyPer));

    }

    private static void readFile() throws IOException {

        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            ar.add(line);

        }
    }
}

