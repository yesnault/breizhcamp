package com.cloudbees.breizhcamp.view;

import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.List;


public class PdfReportView extends AbstractPdfView {


    Font talkFont =
            FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, Color.BLACK);
    Font speakerFont =
            FontFactory.getFont(FontFactory.HELVETICA, 9,
                    Font.NORMAL, Color.BLACK);

    Font themeFont =
            FontFactory.getFont(FontFactory.HELVETICA, 8,
                    Font.ITALIC, Color.GRAY);

    Font presentFont =
            FontFactory.getFont(FontFactory.HELVETICA, 13,
                    Font.BOLD, Color.GRAY);

    Font talkFontTitle =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 17, Font.UNDERLINE, Color.darkGray);

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

    boolean modelA3;

    @Override
    protected Document newDocument() {
        return new Document(PageSize.A4.rotate());
    }

    private void createFirstPage(Document document) throws DocumentException, IOException {
        Rectangle savePagesize = document.getPageSize();
        document.setPageSize(PageSize.A4);
        document.newPage();
        Image imageLogo = Image.getInstance("http://app.breizhcamp.cloudbees.net/static/BreizhCamp.png");
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

        modelA3 = (Boolean) model.get("modeA3");

        List<Talk> talkToExplain = new ArrayList<Talk>();

        HeaderFooter footer = new HeaderFooter(new Phrase("BreizhCamp 2012"), false);
        document.setFooter(footer);

        createFirstPage(document);

        createProgrammePages(document, data, talkToExplain, null);
        if (!modelA3) {
            createTalksPages(document, talkToExplain);
        }
        if (modelA3) {
            for (Room room : data.getRooms()) {
                createProgrammePages(document, data, talkToExplain, room);
            }
        }

    }

    private void createProgrammePages(Document document, Data data, List<Talk> talkToExplain, Room roomSelected) throws DocumentException {
        String sansRoom = "sansRoom";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (modelA3 && roomSelected == null) {
            document.setPageSize(PageSize.A3);
        } else {
            if(roomSelected == null){
                document.setPageSize(PageSize.A4.rotate());
            } else{
                document.setPageSize(PageSize.A4);
            }
        }
        Font font = new Font();
        font.setStyle(Font.BOLD);
        font.setSize(14);
        Paragraph titre = null;


        for (Date date : data.getDatesOrdonnees()) {
            document.newPage();
            if (roomSelected != null) {

                titre = new Paragraph();
                titre.setFont(font);
                titre.setAlignment(Paragraph.ALIGN_CENTER);
                titre.add(new Phrase(roomSelected.getName()));
                document.add(titre);
            }
            titre = new Paragraph();
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
                if (roomSelected == null || room.equals(roomSelected)) {
                    PdfPCell cell = createHeaderCell(room.getName());
                    if (roomSelected != null) {
                        cell.setColspan(data.getRooms().size());
                    }
                    table.addCell(cell);
                }
            }
            for (String creneau : data.getCreneaux().get(date)) {
                table.addCell(createCellCentree(creneau));
                if (data.getTalks().get(date).get(creneau).containsKey(sansRoom)) {
                    if (roomSelected == null) {
                        PdfPCell cell = new PdfPCell();
                        cell.setPaddingBottom(10);
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setColspan(data.getRooms().size());
                        Talk talk = data.getTalks().get(date).get(creneau).get(sansRoom);
                        talkToExplain.add(talk);
                        remplirCellWithTalk(cell, talk);
                        table.addCell(cell);
                    } else {
                        PdfPCell cell = new PdfPCell();
                        cell.setPaddingBottom(10);
                        cell.setColspan(data.getRooms().size());
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                } else {
                    for (Room room : data.getRooms()) {
                        if (roomSelected == null || room.equals(roomSelected)) {
                            PdfPCell cell = new PdfPCell();
                            cell.setPaddingBottom(10);
                            if (roomSelected != null) {
                                cell.setColspan(data.getRooms().size());
                            }
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

                            Talk talk = data.getTalks().get(date).get(creneau).get(room.getName());
                            if (talk != null) {
                                if (roomSelected == null) {
                                    talkToExplain.add(talk);
                                }
                                remplirCellWithTalk(cell, talk);
                            }
                            table.addCell(cell);
                        }
                    }
                }
            }
            document.add(table);
        }
    }

    private void createTalksPages(Document document, List<Talk> talkToExplain) throws DocumentException {
        document.setPageSize(PageSize.A4);
        document.newPage();

        Paragraph paragraph = new Paragraph("Liste des talks");
        paragraph.setSpacingAfter(25);
        paragraph.getFont().setSize(25);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        for (Talk talk : talkToExplain) {
            if (!talk.getTheme().equals(Theme.BREAK)) {
                Paragraph empty = new Paragraph(" ");
                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100);
                table.setKeepTogether(true);
                PdfPCell cell = null;
                Chunk titleTalk = new Chunk(talk.getTitle(), talkFontTitle);
                titleTalk.setLocalDestination("talk" + talk.getId());

                cell = new PdfPCell(new Paragraph(titleTalk));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(empty);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(talk.getDescription()));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(empty);
                cell.setBorder(0);
                table.addCell(cell);
                StringBuilder textSpeakers = new StringBuilder("Présenté par ");
                List<Speaker> speakers = new ArrayList<Speaker>(talk.getSpeakers());
                for (int countSpeakers = 0; countSpeakers < speakers.size(); countSpeakers++) {
                    if (countSpeakers != 0 && countSpeakers == speakers.size() - 1) {
                        textSpeakers.append(" et ");
                    } else if (countSpeakers != 0) {
                        textSpeakers.append(", ");
                    }
                    textSpeakers.append(speakers.get(countSpeakers).getFirstName());
                    textSpeakers.append(' ');
                    textSpeakers.append(speakers.get(countSpeakers).getLastName());
                }
                Paragraph presentBy = new Paragraph(textSpeakers.toString(), presentFont);
                cell = new PdfPCell(presentBy);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(empty);
                cell.setBorder(0);
                table.addCell(cell);
                document.add(table);
            }
        }
    }

    private PdfPCell createCellCentree(String content) {
        return createCellCentree(content, null);
    }

    private PdfPCell createCellCentree(String content, Font font) {
        Paragraph creneau = null;
        if (font == null) {
            creneau = new Paragraph(content);
        } else {
            creneau = new Paragraph(content, font);
        }
        creneau.setAlignment(Paragraph.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setPaddingBottom(10);
        cell.addElement(creneau);
        return cell;
    }

    private void remplirCellWithTalk(PdfPCell cell, Talk talk) {
        Chunk chunk = new Chunk(talk.getTitle(), talkFont);
        chunk.setLocalGoto("talk" + talk.getId());
        Paragraph titleTalk = new Paragraph(chunk);
        titleTalk.setAlignment(Paragraph.ALIGN_CENTER);
        cell.addElement(titleTalk);
        Paragraph theme = new Paragraph(new Phrase(talk.getTheme().getHtmlValue(), themeFont));
        theme.setAlignment(Paragraph.ALIGN_CENTER);
        if (!talk.getTheme().equals(Theme.BREAK)) {
            cell.addElement(theme);
        }
        for (Speaker speaker : talk.getSpeakers()) {
            if (speaker.getPicture() != null) {
                Paragraph speakerText =
                        new Paragraph(speaker.getFirstName() + " " + speaker.getLastName(), speakerFont);
                speakerText.setAlignment(Paragraph.ALIGN_CENTER);
                cell.addElement(speakerText);
            }
        }
    }
}
