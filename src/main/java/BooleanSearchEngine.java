import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.itextpdf.kernel.pdf.PdfName.Path;

public class BooleanSearchEngine implements SearchEngine {
    Map<String, List<PageEntry>> infoAboutWord = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        if (pdfsDir.isDirectory()) {
            for (File item : pdfsDir.listFiles()) {
                if (item.isFile()) {

                    try (var document = new PdfDocument(new PdfReader(item))) {

                        var strategy = new SimpleTextExtractionStrategy();
                        for (int i = 1; i < document.getNumberOfPages(); i++) {
                            String text = PdfTextExtractor.getTextFromPage(document.getPage(i), strategy);
                            var words = text.split("\\P{IsAlphabetic}+");

                            Map<String, Integer> wordAndCount = new HashMap<>();
                            for (var word : words) {
                                if (word.isEmpty()) {
                                    continue;
                                }
                                word = word.toLowerCase();
                                wordAndCount.put(word, wordAndCount.getOrDefault(word, 0) + 1);

                                PageEntry currentPageEntry = new PageEntry(item.getName(), document.getNumberOfPages(),
                                        wordAndCount.getOrDefault(word, 0) + 1);
                                List<PageEntry> allEntries = infoAboutWord.getOrDefault(word, new ArrayList<>());
                                allEntries.add(currentPageEntry);
                                infoAboutWord.put(word, allEntries);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = infoAboutWord.get(word).stream()
                .sorted(PageEntry::compareTo)
                .collect(Collectors.toList());
        result.toString();
        return result;
    }
}
