package analyzer.stategies;

import analyzer.FilePattern;

import java.util.ArrayList;
import java.util.List;

public class KMPSearchAlgorithm extends SearchAlgorithm {
    @Override
    public FilePattern applyAlgorithm(String file, FilePattern[] patterns) {
        int fileLength = file.length();
        
        for (FilePattern filePattern : patterns) {
            String pattern = filePattern.getPattern();
            int[] prefixFunc = prefixFunction(pattern);
            int j = 0;
            for (int i = 0; i < fileLength; i++) {
                while (j > 0 && file.charAt(i) != pattern.charAt(j)) {
                    j = prefixFunc[j - 1];
                }
                
                if (file.charAt(i) == pattern.charAt(j)) {
                    j += 1;
                }
                
                if (j == pattern.length()) {
                    return filePattern;
                }
            }
        }
        
        return null;
    }
    
    private static int[] prefixFunction(String str) {
        int[] prefixFunc = new int[str.length()];
        
        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunc[i - 1];
            
            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            
            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }
            
            prefixFunc[i] = j;
        }
        
        return prefixFunc;
    }
}
