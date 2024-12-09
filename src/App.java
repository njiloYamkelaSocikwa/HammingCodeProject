import java.util.Arrays;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {

        String options[] = { "Encode", "Decode", "Exit" };
        String message = "(12, 8) - Hamming Code";
        String optionMessage = "Welcome to the (12,8)-Hamming Code Encoder and Decode.\n" +
                "Would you like to  Encode or Decode a word?";

        int option = 3;
        while (option != -1) {
            option = JOptionPane.showOptionDialog(null, optionMessage, message, 1, 1, null, options, 0);
            // System.out.println("Picked" + option);
            if (option == 0) {
                String dataword = getDataword();
                String codeword = encode(dataword);
                String encodeMessage = dataword + " is encoded with " + codeword;
                JOptionPane.showConfirmDialog(null, encodeMessage, dataword, 2);

            } else if (option == 1) {
                String codeword = getCodeWord();
                String dataword = decode(codeword);
                String decodeMessage = codeword + " is decoded to " + dataword;
                JOptionPane.showConfirmDialog(null, decodeMessage, codeword, 2);

            } else {
                option = -1; // EXIT
            }
        }

    }

    public static String encode(String dataword) {

        System.out.println("Encoding Procedure Running: ");
        int redunancy = 4;
        int[] bits = new int[dataword.length() + redunancy];

        int d = 0;

        for (int i = 1; i <= dataword.length() + redunancy; i++) {

            if (i != 1 && i != 2 && i != 4 && i != 8) {

                bits[i - 1] = Integer.parseInt(Character.toString(dataword.charAt(d)));
                d++;
            } else {
                bits[i - 1] = -1;
            }
        }
        int xoring = 0b0000;
        System.out.println("-----------------");
        for (int i = 1; i <= bits.length; i++) {
            if (bits[i - 1] == 1 && (i != 1 && i != 2 && i != 4 && i != 8)) {
                System.out.println(
                        String.format("%4s", Integer.toBinaryString(i)).replace(" ", "0") + " \t| " + i + "\t|");
                xoring = xoring ^ i;
            }
        }
        System.out.println("-----------------");
        String xorResultBinString = String.format("%4s", Integer.toBinaryString(xoring)).replace(" ", "0");
        System.out.println(xorResultBinString + " \t| " + xoring + "\t|");
        System.out.println("-----------------");
        for (int b = 0; b < xorResultBinString.length(); b++) {
            bits[(int) Math.pow(2, b) - 1] = xorResultBinString.charAt(4 - b - 1) - 48;
        }
        String encoded = "";
        for (int b = 0; b < bits.length; b++) {
            encoded += bits[b];
        }
        return encoded;
    }

    public static String decode(String codeword) {

        int bits[] = new int[codeword.length()];
        String decoded = "";
        int xorCalc = 0b0000;

        StringBuilder parityBits = new StringBuilder();

        // 001100100110
        for (int b = 1, i = b - 1; b < codeword.length(); b++, i++) {
            bits[i] = (int) codeword.charAt(i) - 48;

            if ((Math.log(b) / Math.log(2)) % 1 == 0) {
                parityBits.append(bits[i]);
            } else if (bits[i] == 1) {
                xorCalc = xorCalc ^ b;
            }
        }
        parityBits.reverse();
        int parityBitsNum = Integer.parseInt(parityBits.toString(), 2);
        System.out.println(Arrays.toString(bits));

        int error = xorCalc ^ parityBitsNum;

        if (error != 0 && error < 13) {
            System.out.println("The error is at position: " + error);
            if (bits[error - 1] == 1) {
                bits[error - 1]--;
            } else {
                bits[error - 1]++;
            }
        } else {
            System.out.println("No error in transmission!");
        }
        for (int i = 0; i < bits.length; i++) {

            if ((Math.log(i + 1) / Math.log(2)) % 1 != 0) {
                decoded += bits[i];
            }
        }
        return decoded;
    }

    public static String getCodeWord() {
        return JOptionPane.showInputDialog("Enter your codeword (Word to Decode)");
    }

    public static String getDataword() {
        return JOptionPane.showInputDialog("Enter your word to decode (Word to Encode)");
    }
}
