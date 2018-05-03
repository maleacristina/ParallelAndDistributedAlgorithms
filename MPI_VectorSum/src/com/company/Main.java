package com.company;
import mpi.*;

public class Main
{
    private static final double[] Vector = { 0, 1, -2.87, 3.5, 4, -5, 6.21, 7.99, 8, -9.55};
    private static final int MASTER = 0;
    private static final int PROCESSORS_NUMBER = 4;

    public static void main(String[] args)
    {
        MPI.Init(args);
        int vector1[] = new int[2];
        double vector2[] = new double[1];
        int rank = MPI.COMM_WORLD.Rank();
        double sum = 0;
        if(rank == MASTER){
            for(int k = 1; k < PROCESSORS_NUMBER; k ++) {
                vector1[0] = (int) ((float) (k * Vector.length - k) / PROCESSORS_NUMBER) + 1;
                vector1[1] = (int) ((float) ((k + 1) * Vector.length - (k + 1)) / PROCESSORS_NUMBER);
                MPI.COMM_WORLD.Send(vector1, 0, 2, MPI.INT, k, 0);
            }
            int i,j;
            i = 0;
            j = (int) ((float)(( Vector.length - 1)) / PROCESSORS_NUMBER);
            for(int k = i; k <= j; k++){
                sum = sum + Vector[k];
            }
            System.out.println("[MASTER]: The partial sum of " + i +" to " + j +" is " + sum);
        }
        if(rank != MASTER) {
            MPI.COMM_WORLD.Recv(vector1, 0, 2, MPI.INT, 0, 0);
            int i,j;
            i = vector1[0];
            j = vector1[1];
            for(int k = i; k <= j; k++){
                sum = sum + Vector[k];
            }
            vector2[0] = sum;
            System.out.println("[SLAVE"+ rank +"]: The partial sum of " + i +" to " + j +" is " + vector2[0]);
            MPI.COMM_WORLD.Send(vector2,0,1, MPI.DOUBLE,0,0);
        }
        if(rank == MASTER){
            for(int k = 1; k < PROCESSORS_NUMBER; k ++) {
                MPI.COMM_WORLD.Recv(vector2, 0, 1, MPI.DOUBLE, k, 0);
                sum = sum + vector2[0];
            }
            System.out.println("[MASTER]: The final sum is " + sum);
        }
        MPI.Finalize();
    }
}