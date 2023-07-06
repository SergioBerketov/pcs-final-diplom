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
        if (this.count == entryCount.count) {
            return 0;
        } else if (this.count < entryCount.count) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "PageEntry" + '{' + "\n"+
                "\n\tPDF='" + pdfName + "'," +
                "\n\tpage='" + page + "'," +
                "\n\tcount'" + count + "'" + "\n" +
                '}' + "\n" + "\n";
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
