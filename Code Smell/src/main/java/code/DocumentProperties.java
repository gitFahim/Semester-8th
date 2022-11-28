package code;

import java.util.HashMap;

public class DocumentProperties {
    public HashMap<String,Double> getTermFreqMap()
    {
        return termFreqMap;
    }

    HashMap<String,Integer> getWordCountMap()
    {
        return DocWordCounts;
    }

    void setTermFreqMap(HashMap<String,Double> inMap)
    {
        termFreqMap = new HashMap<String, Double>(inMap);
    }


    void setWordCountMap(HashMap<String,Integer> inMap)
    {
        DocWordCounts =new HashMap<String, Integer>(inMap);
    }
    private
    HashMap<String,Double> termFreqMap ;
    HashMap<String,Integer> DocWordCounts;
}
