#include <stdio.h>
#include <cuda.h>
#include <stdlib.h>
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#define DIMENSION 5;
#define INF 999999

typedef struct {
	int rows;
	int cols;
	float* elements;
}Matrix;

//typedef struct Matrix Matrix;

//void Matrix* GenMatrix(int rows, int cols)
//{
//	struct Matrix matrix;
//	matrix.rows = rows;
//	matrix.cols = cols;
//	matrix.elements = (double**)malloc(sizeof(double*)* rows);
//	for (int x = 0; x < rows; x++){
//		matrix.elements[x] = (double*)calloc(cols, sizeof(double));
//	}
//	struct Matrix *m;
//	m = &matrix;
//	return m;
//}

__global__ void RoyFloyd(Matrix M,int k)
{
	int i,j, k;
	int row = blockIdx.y * blockDim.y + threadIdx.y;
	int col = blockIdx.x * blockDim.x + threadIdx.x;

	for (k = 0; k < DIMENSION; k++)
	{
		for (i = 0; i < M; i++)
		{
			for (j = 0; j < M; j++){
				if (M.elements[row*M.rows + k] + M.elements[k*A.rows + col] < M.elements[row*M.rows + k])
				{
					M.elements[row*M.rows + col] = M.elements[row*M.rows + k] + M.elements[k*M.rows + col];
				}
			}	
		}
	}
	__syncthreads();
}


int main(int argc, char **argv)
{
	Matrix M;
	M.rows = DIMENSION;
    M.cols = DIMENSION;

	size_t size = M.rows * M.cols * sizeof(float);
	M.elements = (float*)malloc(size);

	M.elements[0] = 0;
	M.elements[1] = 2;
	M.elements[2] = INF;
	M.elements[3] = 10;
	M.elements[4] = INF;
	M.elements[5] = 2;
	M.elements[6] = 0;
	M.elements[7] = 3;
	M.elements[8] = INF;
	M.elements[9] = INF;
	M.elements[10] = INF;
	M.elements[11] = 3;
	M.elements[12] = 0;
	M.elements[13] = 1;
	M.elements[14] = 8;
	M.elements[15] = 10;
	M.elements[16] = INF;
	M.elements[17] = 1;
	M.elements[18] = 0;
	M.elements[19] = INF;
	M.elements[20] = INF;
	M.elements[21] = INF;
	M.elements[22] = 8;
	M.elements[23] = INF;
	M.elements[24] = 0;
	

	// Allocate C in device memory
	Matrix N;
	N.rows = N.rows;
	N.cols = N.cols;
	size_t size = N.rows * N.cols * sizeof(float);
	cudaMalloc(&N.elements, size);

	// Read C from device memory
	cudaMemcpy(N.elements, M.elements, size, cudaMemcpyHostToDevice);

	// Invoke kernel
	dim3 dimBlock(DIMENSION, DIMENSION);
	dim3 dimGrid(N.rows / dimBlock.x, N.cols / dimBlock.y);

	RoyFloyd << <dimGrid, dimBlock >> >(N, k);
	cudaMemcpy(M.elements, M.elements, size, cudaMemcpyDeviceToHost);	
	cudaMemcpy(N.elements, M.elements, size, cudaMemcpyHostToDevice);

	cudaFree(N.elements);
	free(M.elements);
}