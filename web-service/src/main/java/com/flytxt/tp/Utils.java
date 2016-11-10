package com.flytxt.tp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utils {

    static String[] loc = new String[]{"","/BOOT-INF/classes/", "./BOOT-INF/classes/", "./classes/", "classes/"};
    public String readLptemplate(String name) throws IOException, URISyntaxException {
        URI uri;
        for(int i = 0; i < loc.length; i ++)
            try{
                //  uri = findFile(name, i);
                //String str = new String(Files.readAllBytes(Paths.get(uri)));
                //return str;
                String valueOf = new String(findFile(name, i));
                System.out.println("string value " + valueOf);
                return valueOf;
            }catch(Exception e){
                System.out.println("---------Not found @ "+loc[i]);
            }
        try{
            uri = fileFromReource(name);
            System.out.println("-++++++++--------found @ ");
            String str = new String(Files.readAllBytes(Paths.get(uri)));
            return str;
        }catch(Exception e){
            System.out.println("---------Not found @ ");
        }

        throw new RuntimeException("file not found "+name);
    }

    public static String replaceWithConsoleStore(String absProcessor) {
        Pattern p = Pattern.compile("(new\\s)+(.+)(Store\\()", Pattern.MULTILINE);
        Matcher m = p.matcher(absProcessor);
        if (m.find())
            return m.replaceAll("$1 Console$3");
        return null;
    }
    private byte[] findFile(String name,int i) throws URISyntaxException{
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(name);
        System.out.println("Found   " +resourceAsStream);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
        {
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = resourceAsStream.read(buffer)) != -1;)
                os.write(buffer, 0, len);

            os.flush();

            return  os.toByteArray();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
        // return getClass().getResource(loc[i]+name).toURI();

    }

    private URI fileFromReource(String name) throws URISyntaxException, IOException {
        Enumeration<URL> systemResources = ClassLoader.getSystemResources(name);
        while(systemResources.hasMoreElements()){
            URL nextElement = systemResources.nextElement();
            System.out.println(("++++++++++++++++++++" + nextElement.toString()));
            return nextElement.toURI();
        }
        return null;
    }
}
