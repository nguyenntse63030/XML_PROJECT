/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import config.Config;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author NguyenNTSE63030
 */
public class GetDataFromWeb {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 60);

        Crawler crawler = new Crawler(Config.BASIC_LINK_1, driver);
        String xml = crawler.getXml();

        try {
            sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GetDataFromWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        crawler.getDataFromWeb();
        System.out.println(xml);

        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        System.out.println(stream);
    }
}
