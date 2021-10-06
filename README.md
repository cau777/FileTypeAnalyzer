# FileTypeAnalyzer

A tool to efficiently find the extension of files by searching for patterns using different algorithms

## Features

- Implementation of naive, Knuth-Morris-Pratt, and Rabin-Karp (optimized to work with multiple patterns) algorithms to
  search for multiple patterns in the files
- Multithreading using Callable and ExecutorService - one thread for each file
- Pattern priority (because some types are based on others, for example, a Word file is a ZIP file)
- Strategy design pattern to choose the algorithm at runtime

## Usage

Start the program with the following command-line arguments

- Path to the directory with the files
- Path to the file containing the patterns to search (example: patterns.txt)
- The algorithm to use \[naive, kmp (Knuth-Morris-Pratt), rk (Rabin-Karp)\] (optional)

## Example

```
java Main test_directories patterns.txt
doc_0.doc: MS Office Word 2003
doc_1.pptx: MS Office PowerPoint 2007+
doc_2.pdf: PDF document
file.pem: PEM certificate
```
