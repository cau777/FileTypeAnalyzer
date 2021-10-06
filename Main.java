package analyzer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        String path = args[0];
        String patternsPath = args[1];
        String mode = args.length > 2 ? args[2] : "kmp";
        
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
            
            if (files == null) return;
            
            FilePattern[] patterns = loadPatterns(patternsPath);
            
            List<Callable<String>> operations = new ArrayList<>();
            for (File file : files) {
                operations.add(new FileAnalyzer(patterns, file, mode));
            }
            
            List<Future<String>> futures = service.invokeAll(operations);
            futures.forEach(o -> {
                try {
                    System.out.println(o.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
    
    private static FilePattern[] loadPatterns(String file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream stream = new BufferedInputStream(fis);
             Scanner scanner = new Scanner(stream)) {
            
            List<FilePattern> result = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                int priority = Integer.parseInt(parts[0]);
                String pattern = parts[1].substring(1, parts[1].length() - 1);
                String description = parts[2].substring(1, parts[2].length() - 1);
                
                result.add(new FilePattern(priority, pattern, description));
            }
            
            // Order patterns from highest to lowest priority
            result.sort(Comparator.comparingInt(pattern -> -pattern.getPriority()));
            return result.toArray(new FilePattern[0]);
        }
    }
}
