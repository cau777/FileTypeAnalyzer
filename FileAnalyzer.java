package analyzer;

import analyzer.stategies.SearchAlgorithm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

public class FileAnalyzer implements Callable<String> {
    private final FilePattern[] patterns;
    private final File file;
    private final String mode;
    
    public FileAnalyzer(FilePattern[] patterns, File file, String mode) {
        this.patterns = patterns;
        this.file = file;
        this.mode = mode;
    }
    
    @Override
    public String call() {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream stream = new BufferedInputStream(fis)) {
            
            String contents = new String(stream.readAllBytes());
            SearchAlgorithm searchAlgorithm = SearchAlgorithm.getInstance(mode);
            
            return file.getName() + ": " + searchAlgorithm.search(contents, patterns);
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
