package analyzer.stategies;

import analyzer.FilePattern;

import java.util.*;
import java.util.stream.Collectors;

public class RabinKarpSearchAlgorithm extends SearchAlgorithm {
    private static final long m = 1_000_000_009;
    private static final int a = 53;
    
    @Override
    public FilePattern applyAlgorithm(String file, FilePattern[] allPatterns) {
        Map<Integer, List<FilePattern>> patternsPerLength = Arrays.stream(allPatterns)
                .collect(Collectors.groupingBy(o -> o.getPattern().length()));
        
        int fileLen = file.length();
        int foundPriority = -1;
        FilePattern foundPattern = null;
        
        for (Map.Entry<Integer, List<FilePattern>> entry : patternsPerLength.entrySet()) {
            int patternLen = entry.getKey();
            
            // Only search for patterns with higher priority
            int finalFoundPriority = foundPriority;
            List<FilePattern> patterns = entry.getValue().stream().filter(o -> o.getPriority() > finalFoundPriority).collect(Collectors.toList());
            
            if (patterns.size() == 0) continue;
            
            String[] strPatterns = patterns.stream().map(FilePattern::getPattern).toArray(String[]::new);
            
            if (patternLen > fileLen) continue;
            
            long[] patternHashes = new long[strPatterns.length];
            long currSubstrHash = 0;
            long pow = 1;
            
            for (int i = 0; i < patternLen; i++) {
                for (int p = 0; p < patternHashes.length; p++) {
                    patternHashes[p] += (long) (strPatterns[p].charAt(i)) * pow;
                    patternHashes[p] %= m;
                }
                
                currSubstrHash += (long) (file.charAt(fileLen - patternLen + i)) * pow;
                currSubstrHash %= m;
                
                if (i != patternLen - 1) {
                    pow = (pow * a) % m;
                }
            }
            
            for (int i = fileLen; i >= patternLen; i--) {
                for (int p = 0; p < patternHashes.length; p++) {
                    // Also tests if the substrings are equal to avoid hash collisions
                    if (patternHashes[p] == currSubstrHash && substringEqual(file, strPatterns[p], i - patternLen)) {
                        FilePattern found = patterns.get(p);
                        if (found.getPriority() > foundPriority) {
                            foundPriority = found.getPriority();
                            foundPattern = found;
                        }
                    }
                }
                
                if (i > patternLen) {
                    currSubstrHash = (currSubstrHash - (long) (file.charAt(i - 1)) * pow % m + m) * a % m;
                    currSubstrHash = (currSubstrHash + (long) (file.charAt(i - patternLen - 1))) % m;
                }
            }
        }
        
        return foundPattern;
    }
    
    private static boolean substringEqual(String text, String pattern, int start) {
        int length = pattern.length();
        
        for (int i = 0; i < length; i++) {
            if (text.charAt(start + i) != pattern.charAt(i)) {
                return false;
            }
        }
        
        return true;
    }
}
