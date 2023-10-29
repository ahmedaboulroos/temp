import java.util.*;

public class Test {

    public static void main(String[] args) {
        String input1 = "abd(jnb)asdf";
        String input2 = "abdjnbasdf";
        String input3 = "dd(df)a(ghhh)";

        
        // what should happen in this situation, is not clear in problem statement.
        // (e(as)qq(fg)ewe)

        String input4 = "abd()(asd)(s)as()(e(as)qq(fg)ewe)df";
        // abd()(asd)(s)as()(e(as)qq(fg)ewe)df
        // abd()(asd)(s)as()(e(sa)qq(gf)ewe)df  // inner parans first
        // abd()(dsa)(s)as()(ewe)fg(qq)as(e)df  // then outer parans (what's inside is just chars)
                                                // then outer parans (what's inside is just chars)
        //(e(sa)qq(gf)ewe)
        //(ewe)fg(qq)as(e)

        String input5 = "abc(e(as)qw(fg)ewe)df";
        // abc(e(as)qw(fg)ewe)df
        //      ^  ^  ^  ^
        // abc(e(sa)qw(gf)ewe)df
        //    ^              ^
        // abc(ewe)fg(wq)as(e)df
        String output = firstAttempt(input3);
        //System.out.println(output);
    }

    private static String firstAttempt(String input) {
        boolean inParanState = false;
        StringBuilder outputStringBuilder = new StringBuilder();
        StringBuilder reversingStringBuilder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if(currentChar == '(') {
                outputStringBuilder.append(currentChar);
                inParanState = true;
                continue;
            } else if(currentChar == ')') {
                inParanState = false;
                reversingStringBuilder.reverse();
                outputStringBuilder.append(reversingStringBuilder);
                reversingStringBuilder = new StringBuilder();
                outputStringBuilder.append(currentChar);
                continue;
            }

            if(inParanState) {
                reversingStringBuilder.append(currentChar);
            } else {
                outputStringBuilder.append(currentChar);
            }

        }

        return outputStringBuilder.toString();
    }
    
    private static String secondAttempt(String input) {
        int currDepth = 0;
        int maxDepth = 0;
        record ParanPairs(int start, int end, int depth){ }
        record Paran(char paran, int idx){ }
        StringBuilder outputStringBuilder = new StringBuilder(input);
        List<ParanPairs> paranPairsList = new ArrayList<>();
        Stack<Paran> inputParanChars = new Stack<>();
        for(int i=0; i< input.length(); i++) {
            Paran currentChar = new Paran(input.charAt(i), i);

            if (currentChar.paran == ')') {
                Paran ch = inputParanChars.pop();
                while (ch.paran != '(') {
                    ch = inputParanChars.pop();
                }
                paranPairsList.add(new ParanPairs(ch.idx, i, currDepth));
                currDepth--;
            } else if (currentChar.paran == '(') {
                currDepth++;
                if(currDepth > maxDepth) {
                    maxDepth = currDepth;
                }
                inputParanChars.push(new Paran(currentChar.paran, i));
            } else {
                inputParanChars.push(new Paran(currentChar.paran, i));
            }
        }

        System.out.println(paranPairsList);
        System.out.println("max Depth: " + maxDepth);

        while(maxDepth > 0) {
            final int lookupDepth = maxDepth;
            paranPairsList.stream().filter(pp -> pp.depth == lookupDepth).forEach((pp)->{
                String rev = new StringBuilder(outputStringBuilder.substring(pp.start+1, pp.end)).reverse().toString();
                for (int i = pp.start+1, c =0; i < pp.end; i++, c++) {
                    outputStringBuilder.setCharAt(i, rev.charAt(c));
                }
            });
            maxDepth--;
            System.out.println(outputStringBuilder.toString());
        }

        return outputStringBuilder.toString();
    }

}
