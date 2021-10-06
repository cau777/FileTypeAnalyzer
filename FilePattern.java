package analyzer;

public class FilePattern {
    private final int priority;
    private  final String pattern;
    private final String description;
    
    public FilePattern(int priority, String pattern, String description) {
        this.pattern = pattern;
        this.description = description;
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return "FilePattern{" +
                "priority=" + priority +
                ", pattern='" + pattern + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
