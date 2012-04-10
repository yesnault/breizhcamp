package com.cloudbees.breizhcamp;

import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class PdfReportView extends AbstractPdfView {


    Font talkFont =
            FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE,
                    Font.NORMAL, Color.BLACK);
    Font themeFont =
            FontFactory.getFont(FontFactory.HELVETICA, 8,
                    Font.ITALIC, Color.GRAY);

    private PdfPCell createHeaderCell(String content) throws BadElementException {
        Paragraph paragraph = new Paragraph();
        Font font = new Font();
        font.setColor(Color.WHITE);
        paragraph.setFont(font);
        paragraph.add(new Phrase(content));
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPaddingBottom(10);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBackgroundColor(Color.GRAY);
        return cell;
    }

    @Override
    protected Document newDocument() {
        return new Document(PageSize.A4.rotate());
    }

    private void createFirstPage(Document document) throws DocumentException, IOException {
        Rectangle savePagesize = document.getPageSize();
        document.setPageSize(PageSize.A4);
        document.newPage();
        Image imageLogo = Image.getInstance("http://localhost:8080/static/BreizhCamp.png");
        imageLogo.scaleAbsolute(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin(), imageLogo.getHeight());
        document.add(imageLogo);

        Paragraph paragraph = new Paragraph("14 et 15 Juin 2012");
        paragraph.setSpacingAfter(25);
        paragraph.getFont().setSize(25);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.setPageSize(savePagesize);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        Data data = (Data) model.get("data");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String sansRoom = "sansRoom";

        HeaderFooter footer = new HeaderFooter(new Phrase("BreizhCamp 2012"), false);
        document.setFooter(footer);

        createFirstPage(document);
        document.newPage();

        for (Date date : data.getDatesOrdonnees()) {
            Paragraph titre = new Paragraph();
            Font font = new Font();
            font.setStyle(Font.BOLD);
            font.setSize(14);
            titre.setFont(font);
            titre.setAlignment(Paragraph.ALIGN_CENTER);
            titre.add(new Phrase("Programme du " + sdf.format(date)));
            document.add(titre);

            PdfPTable table = new PdfPTable(data.getRooms().size() + 1);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(20);

            table.addCell(createHeaderCell("Heure"));
            for (Room room : data.getRooms()) {
                table.addCell(createHeaderCell(room.getName()));
            }
            for (String creneau : data.getCreneaux().get(date)) {
                table.addCell(creneau);
                if (data.getTalks().get(date).get(creneau).containsKey(sansRoom)) {
                    PdfPCell cell = new PdfPCell();
                    cell.setColspan(data.getRooms().size());
                    Talk talk = data.getTalks().get(date).get(creneau).get(sansRoom);
                    cell.addElement(new Paragraph(new Phrase(talk.getTitle(), talkFont)));
                    cell.addElement(new Paragraph(new Phrase(talk.getTheme().getHtmlValue(), themeFont)));
                    for (Speaker speaker : talk.getSpeakers()) {
                        cell.addElement(new Paragraph(speaker.getFirstName() + " " + speaker.getLastName()));
                    }
                    table.addCell(cell);
                } else {
                    for (Room room : data.getRooms()) {
                        PdfPCell cell = new PdfPCell();

                        Talk talk = data.getTalks().get(date).get(creneau).get(room.getName());
                        if (talk != null) {
                            cell.addElement(new Paragraph(new Phrase(talk.getTitle(), talkFont)));
                            cell.addElement(new Paragraph(new Phrase(talk.getTheme().getHtmlValue(), themeFont)));
                            for (Speaker speaker : talk.getSpeakers()) {
                                if (speaker.getPicture() != null) {
                                    cell.addElement(new Paragraph(speaker.getFirstName() + " " + speaker.getLastName
                                            ()));
                                }
                            }
                        }

                        table.addCell(cell);
                    }
                }
            }
            document.add(table);
        }
    }
}
