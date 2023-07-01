import java.util.Comparator;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry entryCount) {
        return (this.count - entryCount.page);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "\n\tPDF='" + pdfName + "'," +
                "\n\tpage='" + page + "'," +
                "\n\tcount'" + count + "'," +
                '}';
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }
}
