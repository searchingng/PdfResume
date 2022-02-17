package com.searching;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfUtil {
    PDPage page = new PDPage(PDRectangle.A4);
    private float x = page.getMediaBox().getWidth();
    private float y = page.getMediaBox().getHeight();
    private float max = 120;
    private float leftStart = 20;
    private float imageWidth;
    private float imageHeight;
    private float imageLeft;
    private float imageUp;

    public void create(User user, String path) throws IOException {

        PDDocument doc = new PDDocument();
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        setPicAndPanels(doc, contentStream, user);

        setHeader(contentStream, user);

        setPersonal(contentStream, user);

        setSkills(contentStream, user);

        setBodyPart(contentStream, user);

        contentStream.close();
        doc.save(new File(path));
        doc.close();
    }

    public void setImageSettings(PDImageXObject image){
        if (image.getWidth() >= image.getHeight()){
            imageWidth = max;
            imageHeight = imageWidth * image.getHeight()/ image.getWidth();
            imageLeft = 0;
            imageUp = y - max;
            imageUp += (max - imageHeight)/2;
        } else {
            imageHeight = max;
            imageWidth = imageHeight * image.getWidth()/ image.getHeight();
            imageUp = y - imageHeight;
            imageLeft = (imageHeight - imageWidth)/2;
        }
    }

    public void setPicAndPanels(PDDocument doc, PDPageContentStream contentStream, User user) throws IOException {
        PDImageXObject image = PDImageXObject.createFromFile(user.getImg(), doc);
        setImageSettings(image);

        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.addRect(0, y-max, max, max);
        contentStream.fill();

        contentStream.setNonStrokingColor(new Color(16776895));
        contentStream.addRect(0, 0, 200, y-max);
        contentStream.fill();

        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.addRect(max, y-max, x - max, max);
        contentStream.fill();

        contentStream.drawImage(image, imageLeft, imageUp, imageWidth, imageHeight);
    }

    public void setHeader(PDPageContentStream contentStream, User user) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(max + 30, y - max/3);

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.showText(String.join(" ", user.getName(), user.getSurname(), user.getMiddlename()));

        contentStream.setLeading(17);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.setNonStrokingColor(Color.lightGray);
        contentStream.showText(user.getJob());

        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.orange);
        contentStream.showText("Address: " + user.getAddress());

        contentStream.setLeading(17);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.orange);
        contentStream.showText("Phone: " + user.getPhone());

        contentStream.endText();
    }

    public void setPersonal(PDPageContentStream contentStream, User user) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(leftStart, y - max*4/3);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.ORANGE.darker().darker());
        contentStream.showText("Personal Details");

        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.showText("Date of Birth");

        contentStream.setLeading(18);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.BLACK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        contentStream.showText(user.getBirthdate().format(formatter));

        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.showText("Place of Birth");

        contentStream.setLeading(18);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.showText(user.getBirthplace());

        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.showText("Gender");

        contentStream.setLeading(18);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.showText(user.getGender());

        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.showText("Nationality");

        contentStream.setLeading(18);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 14);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.showText(user.getNation());

        contentStream.endText();

        contentStream.setNonStrokingColor(new Color(0xFFFFFF));
        contentStream.addRect(0, 450, 200, 5);
        contentStream.fill();
    }

    public void setSkills(PDPageContentStream contentStream, User user) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(leftStart, 420);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.ORANGE.darker().darker());
        contentStream.showText("Qualification");
        contentStream.endText();

        float h = 370;

        for (Skill skill : user.getSkillList()){
            drawSkill(contentStream, skill, h);
            h -= 50;
        }

    }

    public void drawSkill(PDPageContentStream contentStream, Skill skill, float h) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(leftStart, h + 65);

        contentStream.setLeading(50);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.showText(skill.getName());
        contentStream.endText();

        contentStream.setNonStrokingColor(new Color(0xAB8800));
        contentStream.addRect(leftStart, h, 154, 7);
        contentStream.fill();

        float len = skill.getMark() * 150/5;

        contentStream.setNonStrokingColor(new Color(0xFFCA02));
        contentStream.addRect(leftStart+2, h+2, len, 3);
        contentStream.fill();
    }

    public void setBodyPart(PDPageContentStream contentStream, User user) throws IOException {

        contentStream.beginText();
        contentStream.newLineAtOffset(leftStart + 200, y - max * 4 / 3);

        if (user.getExperienceList() != null) {
            contentStream.setFont(PDType1Font.COURIER_BOLD, 30);
            contentStream.setNonStrokingColor(Color.DARK_GRAY);
            contentStream.showText("Experiences");

            for (Experience experience : user.getExperienceList()) {
                setEduAndExp(contentStream, experience);
            }
            contentStream.setLeading(40);
            contentStream.newLine();
        }

        if (user.getEducationList() != null) {
            contentStream.setFont(PDType1Font.COURIER_BOLD, 30);
            contentStream.setNonStrokingColor(Color.DARK_GRAY);
            contentStream.showText("Education");

            for (Experience edu : user.getEducationList()) {
                setEduAndExp(contentStream, edu);
            }
        }

        contentStream.endText();
    }

    public void setEduAndExp(PDPageContentStream contentStream, Experience experience) throws IOException {
        contentStream.setLeading(30);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.setNonStrokingColor(new Color(0xFF4100));
        contentStream.showText(experience.getPosition());

        contentStream.setLeading(20);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 15);
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.showText(experience.getDescribtion());

        contentStream.setLeading(20);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 15);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.showText(experience.getStart() + " - " + experience.getEnd());
    }

    public void read() throws IOException {

        PDDocument doc = Loader.loadPDF(new File("Assessment.pdf"));
        String text = new PDFTextStripper().getText(doc);

        Pattern p = Pattern.compile("\\s\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\s");
        Matcher m = p.matcher(text);

        while (m.find()){
            System.out.println(m.group());
        }

        if (doc != null){
            doc.close();
        }

    }
}
