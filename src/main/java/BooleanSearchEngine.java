import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.itextpdf.kernel.pdf.PdfName.Path;
import static com.itextpdf.kernel.pdf.PdfName.a;

public class BooleanSearchEngine implements SearchEngine {

    private Map<String, List<PageEntry>> infoAboutWords = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        if (pdfsDir.isDirectory()) {
            for (File item : pdfsDir.listFiles()) {
                if (item.isFile()) {

                    try (var document = new PdfDocument(new PdfReader(item))) {

                        for (int i = 1; i < document.getNumberOfPages(); i++) {
                            PdfPage page = document.getPage(i);
                            String text = PdfTextExtractor.getTextFromPage(page);
                            var words = text.split("\\P{IsAlphabetic}+");

                            Map<String, Integer> wordAndCount = new HashMap<>();
                            for (var word : words) {
                                if (word.isEmpty()) {
                                    continue;
                                }
                                word = word.toLowerCase();
                                wordAndCount.put(word, wordAndCount.getOrDefault(word, 0) + 1);
                            }

                            for (Map.Entry entry : wordAndCount.entrySet()) {
                                String key = (String) entry.getKey();

                                PageEntry currentPageEntry = new PageEntry(item.getName(), i, wordAndCount.get(key));
                                List<PageEntry> allEntries = infoAboutWords.getOrDefault(key, new ArrayList<>());
                                allEntries.add(currentPageEntry);
                                infoAboutWords.put(key, allEntries
                                        .stream()
                                        .sorted(PageEntry::compareTo)
                                        .collect(Collectors.toList()));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        word = word.toLowerCase();
        return infoAboutWords.get(word);
    }
}

