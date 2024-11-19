package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();

            if (operation.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else if (operation.equals("encode")) {
                System.out.println("Input string:");
                String input = scanner.nextLine();
                String encodedString = encode(input);
                System.out.println("Encoded string:");
                System.out.println(encodedString);
            } else if (operation.equals("decode")) {
                System.out.println("Input encoded string:");
                String input = scanner.nextLine();
                if (isValidEncodedString(input)) {
                    String decodedString = decode(input);
                    System.out.println("Decoded string:");
                    System.out.println(decodedString);
                } else {
                    System.out.println("Encoded string is not valid.");
                }
            } else {
                System.out.println("There is no '" + operation + "' operation");
            }
        }
    }

    private static String encode(String input) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            String binaryChar = String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryString.append(binaryChar);
        }

        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < binaryString.length(); ) {
            char bit = binaryString.charAt(i);
            int count = 0;
            while (i < binaryString.length() && binaryString.charAt(i) == bit) {
                count++;
                i++;
            }
            encoded.append(bit == '0' ? "00 " : "0 ");
            encoded.append("0".repeat(count));
            if (i < binaryString.length()) {
                encoded.append(" ");
            }
        }
        return encoded.toString();
    }

    private static String decode(String input) {
        String[] blocks = input.split(" ");
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < blocks.length; i += 2) {
            String prefix = blocks[i];
            String zeros = blocks[i + 1];
            char bit = prefix.equals("0") ? '1' : '0';
            for (int j = 0; j < zeros.length(); j++) {
                binaryString.append(bit);
            }
        }

        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            String binaryChar = binaryString.substring(i, Math.min(i + 7, binaryString.length()));
            int charCode = Integer.parseInt(binaryChar, 2);
            decodedMessage.append((char) charCode);
        }
        return decodedMessage.toString();
    }

    private static boolean isValidEncodedString(String input) {
        if (!input.matches("[0 ]+")) {
            return false;
        }
        String[] blocks = input.split(" ");
        if (blocks.length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < blocks.length; i += 2) {
            if (!blocks[i].equals("0") && !blocks[i].equals("00")) {
                return false;
            }
        }
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < blocks.length; i += 2) {
            String prefix = blocks[i];
            String zeros = blocks[i + 1];
            char bit = prefix.equals("0") ? '1' : '0';
            for (int j = 0; j < zeros.length(); j++) {
                binaryString.append(bit);
            }
        }
        return binaryString.length() % 7 == 0;
    }
}