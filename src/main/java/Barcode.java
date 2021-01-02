import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

public class Barcode {

    public static final String DEST = "src/main/resources/pdf/barcode1.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Barcode().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdf, PageSize.A4);
        Barcode128 code128 = new Barcode128(pdf);
        code128.setCode("ABC123456789");
        code128.setCodeType(Barcode128.CODE128);
        PdfFormXObject xObject = code128.createFormXObject(Color.BLACK, Color.BLACK, pdf);

        float x = 36;
        float y = 750;
        float width = xObject.getWidth();
        float height = xObject.getHeight();

        // Draw the rectangle with the set background color and add the created barcode object.
        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        canvas.saveState();
        canvas.setFillColor(Color.LIGHT_GRAY);
        canvas.rectangle(x, y, width, height);
        canvas.fill();
        canvas.restoreState();
        canvas.addXObject(xObject, 36, 750);

        pdf.close();
    }
}
