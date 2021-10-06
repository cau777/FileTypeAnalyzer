package analyzer.stategies;

import analyzer.FilePattern;

public class NaiveSearchAlgorithm extends SearchAlgorithm {
    @Override
    public FilePattern applyAlgorithm(String file, FilePattern[] patterns) {
        int fileLength = file.length();
        
        for (FilePattern filePattern : patterns) {
            String pattern = filePattern.getPattern();
            int patternLength = pattern.length();
            for (int i = 0; i < fileLength - patternLength + 1; i++) {
                if (compareRange(file, pattern, i)) return filePattern;
            }
        }
        
        return null;
    }
    
    private boolean compareRange(String str, String pattern, int strStart) {
        int patternLength = pattern.length();
        int patternIndex = 0;
        
        for (int i = strStart; i < strStart + patternLength; i++) {
            if (str.charAt(i) != pattern.charAt(patternIndex++)) return false;
        }
        
        return true;
    }
}
