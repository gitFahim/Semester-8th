package code;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TfidfCalculation {
    SortedSet<String> wordList = new TreeSet(String.CASE_INSENSITIVE_ORDER);

    //Calculates inverse Doc frequency.
    public HashMap<String,Double> calculateInverseDocFrequency(DocumentProperties [] docProperties)
    {

        HashMap<String,Double> InverseDocFreqMap = new HashMap<>();
        int size = docProperties.length;
        double wordCount ;
        for (String word : wordList) {
            wordCount = 0;
            for(int i=0;i<size;i++)
            {
                HashMap<String,Integer> tempMap = docProperties[i].getWordCountMap();
                if(tempMap.containsKey(word))
                {
                    wordCount++;
                    continue;
                }
            }
            double temp = size/ wordCount;
            double idf = 1 + Math.log(temp);
            InverseDocFreqMap.put(word,idf);
        }
        return InverseDocFreqMap;
    }

    //calculates Term frequency for all terms
    public HashMap<String,Double> calculateTermFrequency(HashMap<String,Integer>inputMap) {

        HashMap<String ,Double> termFreqMap = new HashMap<>();
        double sum = 0.0;
        //Get the sum of all elements in hashmap
        for (float val : inputMap.values()) {
            sum += val;
        }

        //create a new hashMap with Tf values in it.
        Iterator it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            double tf = (Integer)pair.getValue()/ sum;
            termFreqMap.put((pair.getKey().toString()),tf);
        }
        return termFreqMap;
    }

    //Returns if input contains numbers or not
    public  boolean isDigit(String input)
    {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        }
        return false;
    }

    //Writes the contents of hashmap to CSV file
    public  void outputAsCSV(HashMap<String,Double>treeMap,String OutputPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Double> keymap : treeMap.entrySet()) {
            builder.append(keymap.getKey());
            builder.append(",");
            builder.append(keymap.getValue());
            builder.append("\r\n");
        }
        String content = builder.toString().trim();
        BufferedWriter writer = new BufferedWriter(new FileWriter(OutputPath));
        writer.write(content);
        writer.close();
    }

    public void printMap(HashMap<String,Double>tfIDF, String Name) {
        ArrayList<Double> list = new ArrayList<>();
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : tfIDF.entrySet()) {
            list.add(entry.getValue());
        }
        OptionalDouble average = list
                .stream()
                .mapToDouble(a -> a)
                .average();

        for (Map.Entry<String, Double> entry : tfIDF.entrySet()) {
            if (entry.getValue() > average.getAsDouble()) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        }
        //System.out.println(Name);
        ArrayList<String>relevant = new ArrayList<>();
        for(Map.Entry<String, Double>it:sortedMap.entrySet()) {
            relevant.add(it.getKey());
            System.out.print(it.getKey() + " ");
        }

//        String[] keys = sortedMap.keySet().toArray(String[]::new);
//
//
//        //System.out.println(Name);
//        for (String k : keys) {
//            System.out.print(k);
//            System.out.print(" ");
//        }

    }

    public ArrayList<String> removeDuplicates(ArrayList<String> relevant) {
        ArrayList<String> newList = new ArrayList<>();

        // Traverse through the first list
        for (String element : relevant) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    //cleaning up the input by removing .,:"
    public  String cleanseInput(String input)
    {
        String newStr = input.replaceAll("[, . : ;\"]", "");
        newStr = newStr.replaceAll("\\p{P}","");
        newStr = newStr.replaceAll("\t","");
        return newStr;
    }
    // Converts the input text file to hashmap and even dumps the final output as CSV files
    public  HashMap<String, Integer> getTermsFromFile(String Filename, int count, File folder) {
        HashMap<String, Integer> WordCount = new HashMap<String, Integer>();
        BufferedReader reader = null;
        HashMap<String, Integer> finalMap = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(Filename));
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.toLowerCase().split(" ");
                for (String term : words) {
                    //cleaning up the term ie removing .,:"
                    term = cleanseInput(term);
                    //ignoring numbers
                    if (isDigit(term)) {
                        continue;
                    }
                    if (term.length() == 0) {
                        continue;
                    }
                    wordList.add(term);
                    if (WordCount.containsKey(term)) {
                        WordCount.put(term, WordCount.get(term) + 1);
                    } else {
                        WordCount.put(term, 1);
                    }
                }
                line = reader.readLine();
            }
            // sorting the hashmap
            Map<String, Integer> treeMap = new TreeMap<>(WordCount);
            finalMap = new HashMap<String, Integer>(treeMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalMap;
    }
}