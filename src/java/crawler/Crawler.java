/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import config.Config;
import java.io.File;
import java.io.StringReader;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.xml.sax.InputSource;

/**
 *
 * @author NguyenNTSE63030
 */
public class Crawler {

    private WebDriver driver;
    private String URL;
    private String xml;

    public Crawler(String url, WebDriver driver) {
        this.driver = driver;
        this.URL = url;
        this.xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        this.driver.get(url);
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

//    public void setURL(String url) {
//        this.URL = url;
//        this.driver.get(url);
//    }
    public void close() {
        this.driver.quit();
    }

    private void initXML() {
        this.xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }

    public void setDataXML(String tenSanPham, String maSanPham, String donGia, String chiTiet, String photoURL) {
        this.xml += "<sanPham>";
        this.xml += "<tenSanPham>" + tenSanPham + "</tenSanPham>";

        this.xml += "<maSanPham>" + maSanPham + "</maSanPham>";

        this.xml += "<donGia>" + donGia + "</donGia>";

        this.xml += "<chiTiet>" + chiTiet + "</chiTiet>";
        this.xml += "<photoURL>" + photoURL + "</photoURL>";

        this.xml += "</sanPham>";
    }

    public void getDataFromWeb() {
//        WebElement maxPageNumberEle = this.driver.findElement(By.xpath("//*[@id=\"pagination\"]/ul/li[5]/a"));
//        int maxPageNumber = Integer.parseInt(maxPageNumberEle.getText().trim());
        int index = 0;
        for (int i = 2; i <= Config.MAX_PAGE_LENGTH; i++) {

            ArrayList<WebElement> products = (ArrayList<WebElement>) this.driver.findElements(By.cssSelector("#collection-body > div.col-md-9.col-sm-12.col-xs-12 > div.row.filter-here > div.content-product-list.product-list.filter.clearfix > div > div > div.product-detail.clearfix > div > h3 > a"));
            List<String> productDetailUrls = new ArrayList<>();
            for (WebElement product : products) {
                String productDetailURL = product.getAttribute("href");
                productDetailUrls.add(productDetailURL);
            }

            for (String url : productDetailUrls) {
                try {
                    this.driver.get(url);
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GetDataFromWeb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    WebElement tenSanPhamEle = this.driver.findElement(By.xpath("//*[@id=\"detail-product\"]/div[1]/h1"));
                    WebElement maSanPhamEle = this.driver.findElement(By.xpath("//*[@id=\"pro_sku\"]/span"));
                    WebElement donGiaEle = this.driver.findElement(By.cssSelector("#price-preview > span.pro-price"));
                    WebElement chiTietEle = this.driver.findElement(By.xpath("//*[@id=\"detail-product\"]/div[3]/p[1]"));
                    WebElement photoURLEle = this.driver.findElement(By.xpath("//*[@id=\"product\"]/div[2]/div/div/div[1]/div[1]/div/div[2]/div[1]/img"));

                    String tenSanPham = null;
                    String maSanPham = null;
                    String donGia = null;
                    String chiTiet = null;
                    String photoURL = null;

                    tenSanPham = tenSanPhamEle != null ? tenSanPhamEle.getText().trim() : "";
                    maSanPham = maSanPhamEle != null ? maSanPhamEle.getText().trim() : "";

                    donGia = donGiaEle != null ? donGiaEle.getText().trim().replaceAll("[,₫]", "") : "";
                    chiTiet = chiTietEle != null ? chiTietEle.getText().trim() : "";

                    photoURL = photoURLEle != null ? photoURLEle.getAttribute("src").trim() : "";

                    initXML();
                    setDataXML(tenSanPham, maSanPham, donGia, chiTiet, photoURL);
                    validateXML();
//                    System.out.println("GET " + tenSanPham + " SUCCESSFULLY " + (++index));
                } catch (Exception e) {
                    continue;
                }
            }
            this.driver.get(Config.BASIC_LINK + i);
        }
        System.out.println(this.xml);

    }

    private void validateXML() {
        try {

            JAXBContext ct = JAXBContext.newInstance(SanPhamDTO.class);
            Unmarshaller u = ct.createUnmarshaller();

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("web/WEB-INF/productSchema.xsd"));
            u.setSchema(schema);
            InputSource inputSource = new InputSource(new StringReader(this.xml));

            SanPhamDTO sanPham = (SanPhamDTO) u.unmarshal(inputSource);
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            boolean result = sanPhamDAO.insertSanPham(sanPham);
            if (result) {
                System.out.println("INSERT " + sanPham.getTenSanPham());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
