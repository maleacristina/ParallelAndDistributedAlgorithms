package com.company;

import mpi.*;

public class Main {

    public static final int INF = 99999;
    public static final int ARRAY_LENGTH = 5;

    public static void main(String[] args) {
        MPI.Init(args);
        int[][] Array = new int[][]{
                {0, 2, INF, 10, INF},
                {2, 0, 3, INF, INF},
                {INF, 3, 0, 1, 8},
                {10, INF, 1, 0, INF},
                {INF, INF, 8, INF, 0}
        };
        int[][] resultArray = new int[ARRAY_LENGTH][ARRAY_LENGTH];
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        for (int k = 0; k < ARRAY_LENGTH; k++) {
            for (int i = rank; i < ARRAY_LENGTH; i += size) {
                for (int j = 0; j < ARRAY_LENGTH; j++) {
                    if (Array[i][j] > Array[i][k] + Array[k][j]) {
                        Array[i][j] = Array[i][k] + Array[k][j];
                    }
                }
            }
            MPI.COMM_WORLD.Gather(Array, rank * ARRAY_LENGTH, ARRAY_LENGTH, MPI.INT, resultArray, rank * ARRAY_LENGTH, ARRAY_LENGTH, MPI.INT, 0);
            MPI.COMM_WORLD.Barrier();

            if (rank == 0) {
                for (int i = 0; i < ARRAY_LENGTH; i++) {
                    for (int j = 0; j < ARRAY_LENGTH; j++) {
                        Array[i][j] = resultArray[i][j];
                    }
                }
                MPI.COMM_WORLD.Bcast(Array, 0, ARRAY_LENGTH * ARRAY_LENGTH, MPI.INT, 0);
            }
            MPI.COMM_WORLD.Barrier();
        }
        if (rank == 0){
            for (int i = 0; i < resultArray.length; i++) {
                for (int j = 0; j < resultArray[i].length; j++) {
                    System.out.print(resultArray[i][j] + " ");
                }
                System.out.println();
            }
        }

        MPI.Finalize();
    }
}