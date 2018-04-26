package com.company;

import mpi.*;
import java.util.Random;

public class Main {


    public static void main(String[] args)
    {
        MPI.Init(args);
        Random random = new Random();
        int buffer[] = new int[2];
        int size = MPI.COMM_WORLD.Size() ;
        int myRank = MPI.COMM_WORLD.Rank();
        int generatedNumber = random.nextInt(10);
        System.out.println("PROCESS"+ ' ' + myRank +" : The generated number is " + generatedNumber);
        if(myRank > 0){
            buffer[0] = myRank;
            buffer[1] = generatedNumber;
            MPI.COMM_WORLD.Send(buffer,0,2, MPI.INT,0,0);
        }
        if(myRank == 0) {
            int rank = 0;
            int maxGeneratedNumber = generatedNumber;
            for (int i = 1; i < size; i++) {
                MPI.COMM_WORLD.Recv(buffer, 0, 2, MPI.INT, i, 0);
                if(buffer[1] > maxGeneratedNumber){
                    maxGeneratedNumber = buffer[1];
                    rank = i;
                } else {
                    if((buffer[1] == maxGeneratedNumber) && (i > rank)) {
                        rank = i;
                    }
                }
            }
            System.out.println("PROCESS LEADER is "+ rank +" with the generated number " + maxGeneratedNumber);
        }
        MPI.Finalize();
    }
}