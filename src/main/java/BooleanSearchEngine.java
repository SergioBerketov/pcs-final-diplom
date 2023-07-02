import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {

    Map<String, List<PageEntry>> infoAboutWord = new HashMap<>();



    public BooleanSearchEngine(File pdfsDir) throws IOException {

        try (var document = new PdfDocument(new PdfReader(pdfsDir))) {
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

                    PageEntry currentPageEntry = new PageEntry(pdfsDir.getName(), document.getNumberOfPages(),
                            wordAndCount.getOrDefault(word, 0) + 1);

                    infoAboutWord.put(word, (List<PageEntry>) currentPageEntry);
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
