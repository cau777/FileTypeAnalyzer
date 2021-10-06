package analyzer.stategies;

import analyzer.FilePattern;

public abstract class SearchAlgorithm {
    public static SearchAlgorithm getInstance(String algorithm) {
        switch (algorithm.toLowerCase()){
            case "naive":
                return new NaiveSearchAlgorithm();
            case "kmp":
                return new KMPSearchAlgorithm();
            case "rk":
                return new RabinKarpSearchAlgorithm();
            default:
                throw new IllegalStateException("Unexpected value: " + algorithm.toLowerCase());
        }
    }
    
    public String search(String content, FilePattern[] patterns) {
        FilePattern result =  applyAlgorithm(content, patterns);
        if (result == null) {
            return "Unknown file type";
        }
        
        return result.getDescription();
    }
    
    public abstract FilePattern applyAlgorithm(String file, FilePattern[] patterns);
}
