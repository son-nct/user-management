/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author WIN 10
 */
public class MyContextListener implements ServletContextListener {

    private final String PATH = "/WEB-INF/fileController.txt";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath("/");
        String filePath = path + PATH;
        Map<String, String> siteMap = readFile(filePath);
        context.setAttribute("SITEMAP", siteMap);
    }

    private Map<String, String> readFile(String filePath) {
        FileReader fr = null;
        BufferedReader bf = null;
        Map<String, String> siteMap = null;
        try {
            fr = new FileReader(filePath);
            bf = new BufferedReader(fr);
            while (bf.ready()) {
                String s = bf.readLine();
                String[]arr = s.split("=");
                if(arr.length == 2) {
                    if(siteMap == null) {
                        siteMap = new HashMap<>();
                    }
                    String key = arr[0];
                    String value = arr[1];
                    
                    siteMap.put(key, value);
                }
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, "Not found your fileController.txt", e.getCause());
        } catch (IOException e) {
            Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, "Could not read fileController.txt", e.getCause());
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, "Could not read fileController.txt", e.getCause());
            }

        }
        return  siteMap;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
