package com.company.itservice;

class MagicSquaresContainer {
    private final int[][] magics = new int[32][9];

    private final int[][] baseMagics = {
            {4, 9, 2, 3, 5, 7, 8, 1, 6},
            {2, 7, 6, 9, 5, 1, 4, 3, 8},
            {6, 1, 8, 7, 5, 3, 2, 9, 4},
            {8, 3, 4, 1, 5, 9, 6, 7, 2}
    };

    private final int[][] fullMagics = new int[12][9];

    public MagicSquaresContainer() {
        // Формируем все магические квадраты из базовых + перестановки верх/низ и право/лево
        copyMagic(baseMagics[0], fullMagics[0]);
        copyMagicRow(baseMagics[0], fullMagics[1], 0, 2);
        copyMagicColumn(baseMagics[0], fullMagics[2], 0, 2);

        copyMagic(baseMagics[1], fullMagics[3]);
        copyMagicRow(baseMagics[1], fullMagics[4], 0, 2);
        copyMagicColumn(baseMagics[1], fullMagics[5], 0, 2);

        // то же самое - но исключаем дублирование
        copyMagic(baseMagics[2], fullMagics[6]);
        copyMagic(baseMagics[3], fullMagics[7]);

        // Формируем из всех магических все полумагические квадраты
        allHalfMagics();
    }

    public void allHalfMagics() {
        int indexHalfMagic = 0;
        for (int indexMagic = 0; indexMagic < 8; indexMagic++)
            indexHalfMagic = halfMagicsFromMagic(indexMagic, indexHalfMagic);
    }

    private int halfMagicsFromMagic(int indexMagic, int inbexFirstHalfMagic) {
        copyMagicRow(fullMagics[indexMagic], magics[inbexFirstHalfMagic], 0, 1);
        copyMagicRow(fullMagics[indexMagic], magics[++inbexFirstHalfMagic], 2, 1);
        copyMagicColumn(fullMagics[indexMagic], magics[++inbexFirstHalfMagic], 0, 1);
        copyMagicColumn(fullMagics[indexMagic], magics[++inbexFirstHalfMagic], 2, 1);
        return ++inbexFirstHalfMagic;
    }


    private void copyMagic(int[] srcMagic, int[] dstMagic) {
        System.arraycopy(srcMagic, 0, dstMagic, 0, 9);
    }

    private void copyMagicRow(int[] srcMagic, int[] dstMagic, int srcRow, int dstRow) {
        copyMagic(srcMagic, dstMagic);
        for (int i = 0; i < 3; i++) {
            dstMagic[i + dstRow * 3] = srcMagic[i + srcRow * 3];
            dstMagic[i + srcRow * 3] = srcMagic[i + dstRow * 3];
        }
    }

    private void copyMagicColumn(int[] srcMagic, int[] dstMagic, int srcColumn, int dstColumn) {
        copyMagic(srcMagic, dstMagic);
        for (int i = 0; i < 9; i += 3) {
            dstMagic[i + dstColumn] = srcMagic[i + srcColumn];
            dstMagic[i + srcColumn] = srcMagic[i + dstColumn];
        }
    }

    public int[][] getMagics() {
        return magics;
    }

    public int[][] getBaseMagics() {
        return baseMagics;
    }

    public int[][] getFullMagics() {
        return fullMagics;
    }
}


