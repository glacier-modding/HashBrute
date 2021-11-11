import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

// first run:                            ~860  ms
// second run (added threads):           ~1200 ms
// run with improved IOI_MD5:            ~656 ms
// run without consecutive vowels:       ~586 ms
// run without consecutive cons:         ~234 ms
// run without consec vowels and cons:   ~65 ms


// without equals:                   ~120 ms
// without ioi calc:                 ~127 ms
// without stringbuilder:            ~566 ms

public final class HashBrute {
    static java.security.MessageDigest md;

    static {
        try {
            md = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    static final String alphabet = "_etaoinsrhdlucmfywgpbvkxqjz";
    static final char[] alp = alphabet.toCharArray();
    static final String path = "[assembly:/sound/wwise/originals/voices/english(us)/ai/abiatti/accdnt_inv_abiatti_abiatti_001.wav](";
    static final String correct = "00DFC08630C51B48";
    static final String path2 = ").pc_wem";
    static Set<Character> consonants;
    static Set<Character> vowels;

    public static void main(final String[] args) {
        vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');

        consonants = new HashSet<>();
        consonants.add('b');
        consonants.add('c');
        consonants.add('d');
        consonants.add('f');
        consonants.add('g');
        consonants.add('h');
        consonants.add('j');
        consonants.add('k');
        consonants.add('l');
        consonants.add('m');
        consonants.add('n');
        consonants.add('p');
        consonants.add('q');
        consonants.add('r');
        consonants.add('s');
        consonants.add('t');
        consonants.add('v');
        consonants.add('w');
        consonants.add('x');
        consonants.add('y');
        consonants.add('z');

        int amount = 1;

        long value = 0;

        for (int i = 0; i < amount; i++) {
            value += run();
        }

        value /= amount;

        System.out.println(value);
    }

    public static long run() {
        final long startTime = System.nanoTime();
        printAllLetterSequences("", 11);
        final long endTime = System.nanoTime();

        return ((endTime - startTime) / 1000000); // divide by 1000000 to get milliseconds.
    }

    static void printAllLetterSequences(final String prefix, final int length) {
        final String cp = path +
                prefix +
                path2;

        if (IOI_MD5(cp).equalsIgnoreCase(correct)) {
            System.out.println(cp);
        }

        if (prefix.length() < length) {
            int vi = 0;
            int ci = 0;
            for (char c : alp) {
                for (char sc : prefix.toCharArray()) {
                    if (consonants.contains(sc)) {
                        ci++;
                    } else {
                        ci = 0;
                    }

                    if (ci >= 3) {
                        return;
                    }

                    if (vowels.contains(sc)) {
                        vi++;
                    } else {
                        vi = 0;
                    }

                    if (vi >= 4) {
                        return;
                    }
                }

                printAllLetterSequences((prefix + c), length);
            }
        }
    }

    public static String IOI_MD5(final String md5) {
        byte[] array = md.digest(md5.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("00");

        for (int i = 1; i < array.length - 8; ++i) {
            int val = (array[i] & 0xFF) | 0x100;

            while (val >= 255) {
                val -= 256;
            }

            sb.append(Integer.toHexString(val));
        }

        return sb.toString();
    }
}
