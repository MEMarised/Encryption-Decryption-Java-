package encryptdecrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";
        String inFile = "";
        String outFile = "";
        String alg = "shift";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    i++;
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    i++;
                    break;
                case "-data":
                    data = args[i + 1];
                    i++;
                    break;
                case "-in":
                    inFile = args[i + 1];
                    i++;
                    break;
                case "-out":
                    outFile = args[i + 1];
                    i++;
                    break;
                case "-alg":
                    alg = args[i + 1];
                    i++;
                    break;
            }
        }

        if (data.isEmpty() && !inFile.isEmpty()) {
            try {
                data = new String(Files.readAllBytes(Paths.get(inFile)));
            } catch (IOException e) {
                System.out.println("Error: Unable to read the input file");
                return;
            }
        }

        String result;
        if ("enc".equals(mode)) {
            if ("shift".equals(alg)) {
                result = shiftEncrypt(data, key);
            } else {
                result = unicodeEncrypt(data, key);
            }
        } else if ("dec".equals(mode)) {
            if ("shift".equals(alg)) {
                result = shiftDecrypt(data, key);
            } else {
                result = unicodeDecrypt(data, key);
            }
        } else {
            System.out.println("Error: Unknown operation");
            return;
        }

        if (!outFile.isEmpty()) {
            try {
                Files.write(Paths.get(outFile), result.getBytes());
            } catch (IOException e) {
                System.out.println("Error: Unable to write to the output file");
            }
        } else {
            System.out.println(result);
        }
    }

    private static String shiftEncrypt(String message, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : message.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                char newChar = (char) ((ch - 'a' + key) % 26 + 'a');
                result.append(newChar);
            } else if (ch >= 'A' && ch <= 'Z') {
                char newChar = (char) ((ch - 'A' + key) % 26 + 'A');
                result.append(newChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static String shiftDecrypt(String message, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : message.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                char newChar = (char) ((ch - 'a' - key + 26) % 26 + 'a');
                result.append(newChar);
            } else if (ch >= 'A' && ch <= 'Z') {
                char newChar = (char) ((ch - 'A' - key + 26) % 26 + 'A');
                result.append(newChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static String unicodeEncrypt(String message, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : message.toCharArray()) {
            char newChar = (char) (ch + key);
            result.append(newChar);
        }
        return result.toString();
    }

    private static String unicodeDecrypt(String message, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : message.toCharArray()) {
            char newChar = (char) (ch - key);
            result.append(newChar);
        }
        return result.toString();
    }
}