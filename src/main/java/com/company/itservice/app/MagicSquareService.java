package com.company.itservice.app;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MagicSquareService {
    private static final MagicSquaresContainer squares = new MagicSquaresContainer();

    public int[] getMinPriceAndSquare(List<Integer> tstMagic) {
        int[] input = toPrimitiveArray(tstMagic);
        int minPrice = Integer.MAX_VALUE;
        int indexMinPrice = 0;
        for (int j = 0; j < 32; j++) {
            int price = getPrice(j, input);
            if (minPrice > price) {
                minPrice = price;
                indexMinPrice = j;
            }
        }
        int[] outResult = new int[10];
        outResult[0] = minPrice;
        for (int i = 0; i < 9; i++)
            outResult[i+1] =  squares.getMagics()[indexMinPrice][i];
        return outResult;
    }

    public int getMinPrice(List<Integer> tstMagic) {
        int[] input = toPrimitiveArray(tstMagic);
        int minPrice = Integer.MAX_VALUE;
        for (int j = 0; j < 32; j++) {
            int price = getPrice(j, input);
            if (minPrice > price) minPrice = price;
        }
        return minPrice;
    }

    private int[] toPrimitiveArray(List<Integer> tstMagic) {
        int[] input = new int[9];
        for (int i = 0; i < input.length; i++) {
            input[i]= tstMagic.get(i);
        }
        return input;
    }

    public int getPrice(int indexMagic, int[] table) {
        int price = 0;
        for (int i = 0; i < 9; i++) {
            price += Math.abs(table[i] - squares.getMagics()[indexMagic][i]);
        }
        return price;
    }

// МЕТОДЫ ДЛЯ ОТЛАДКИ
//    public int getPrice(int[] magic, int[] table) {
//        int price = 0;
//        for (int i = 0; i < 9; i++) {
//            price += Math.abs(magic[i] - table[i]);
//        }
//        return price;
//    }

//    public MagicType getMagicType(int[] magic) {
//        if ((magic[0] + magic[1] + magic[2] != 15)
//                || (magic[3] + magic[4] + magic[5] != 15)
//                || (magic[6] + magic[7] + magic[8] != 15)
//
//                || (magic[0] + magic[3] + magic[6] != 15)
//                || (magic[1] + magic[4] + magic[7] != 15)
//                || (magic[2] + magic[5] + magic[8] != 15)) return MagicType.MAGIC_NONE;
//
//        if (magic[4] == 5) return MagicType.MAGIC_FULL;
//
//        return MagicType.MAGIC_HALF;
//    }

//    public void testDoubleMagic() {
//        for (int m = 0; m < 31; m++) {
//            for (int n = m + 1; n < 32; n++) {
//                int count = 0;
//                for (int i = 0; i < 9; i++)
//                    if (magics[m][i] == magics[n][i]) count++;
//                if (count < 9) continue;
//                System.out.println("Magics number " + m + " equal magic number " + n);
//            }
//        }
//    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("\nMagics: ");
//        for (int i = 0; i < 32; i++)
//            sb.append("\n")
//                    .append(i)
//                    .append(":\t ")
//                    .append(Arrays.toString(magics[i]))
//                    .append("   Type ")
//                    .append(getMagicType(magics[i]));
//        return sb.toString();
//    }
}
