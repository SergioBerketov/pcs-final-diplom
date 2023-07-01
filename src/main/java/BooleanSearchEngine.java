import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {

   Map<String, PageEntry> infoAboutWord = new HashMap<>();

   Object pageEntryForSearch = new Object();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        try (var document = new PdfDocument(new PdfReader(pdfsDir))) {
            var strategy = new SimpleTextExtractionStrategy();
            for (int i = 1; i < document.getNumberOfPages(); i++) {
                String text = PdfTextExtractor.getTextFromPage(document.getPage(i), strategy);
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> wordsCounter = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    wordsCounter.put(word, wordsCounter.getOrDefault(word, 0) + 1);

                    PageEntry currentPageEntry = new PageEntry(pdfsDir.getName(), document.getNumberOfPages(), wordsCounter. );
                    infoAboutWord.put(word, currentPageEntry);
                    pageEntryForSearch = currentPageEntry;
                }
            }
        }
    }


    @Override
    public List<PageEntry> search(String word) {
        if (infoAboutWord.containsKey(word)){

            System.out.println(pageEntryForSearch.toString());

        }
        return Collections.emptyList();
    }
}
