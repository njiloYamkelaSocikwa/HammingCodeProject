import java.util.Arrays;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {

        String options[] = { "Encode", "Decode", "Exit" };
        String message = "(12, 8) - Hamming Code";

        int option = 3;
        while (option != -1) {
            option = JOptionPane.showOptionDialog(null, null, message, 1, 1, null, options, 0);
            System.out.println("Picked" + option);
            if (option == 0) {
                String dataword = getDataword();
                String codeword = encode(dataword);
                String encodeMessage = "Your codeword is: " + codeword;
                JOptionPane.showConfirmDialog(null, encodeMessage, dataword, 2);

            } else if (option == 1) {
                String codeword = getCodeWord();

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
        String decoded = "";
        for (int b = 0; b < bits.length; b++) {
            decoded += bits[b];
        }
        return decoded;
    }

    public static String decode(String codeword) {

        return null;

    }

    public static String getCodeWord() {
        return JOptionPane.showInputDialog("Enter your codeword (Word to Decode)");
    }

    public static String getDataword() {
        return JOptionPane.showInputDialog("Enter your word to decode (Word to Encode)");
    }
}
