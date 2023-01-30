package org.jismah;

import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        // PRINT WELCOME
        String welcome = "Welcome to my Crawler!\nEnter a URL";
        System.out.println(welcome);

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        System.out.println("Searching... ");

        getGeneralInfo(s);
        listAllParagraph(s);
        listAllImg(s);
        listAllForms(s);


    }

    // FUNCION PARA BUSCAR FORMULARIOS Y DEVOLVER CANTIDAD Y CARACTERISTICAS
    public static void listAllForms(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements form = doc.select("form");
        Elements withAttrPost = new Elements();
        Elements withAttrGet = new Elements();
        Elements InputsPost = new Elements();
        Elements InputsGet = new Elements();

        for( Element element : doc.getAllElements() )
        {
            for( Attribute attribute : element.attributes() )
            {
                if( attribute.getValue().equalsIgnoreCase("post") )
                {
                    withAttrPost.add(element);
                    InputsPost = element.select("input");

                }

                if( attribute.getValue().equalsIgnoreCase("get") )
                {
                    withAttrGet.add(element);
                    InputsGet = element.select("input");
                }
            }
        }


        System.out.print(" * There're " + form.size() + " Forms\n");
        System.out.print("    -> With POST: " + withAttrPost.size() + "\n");

        if (InputsPost.size() > 0) {
            int counter = 0;
            for (Element e: InputsPost) {
                counter++;
                System.out.println("    -- Input #"+ counter +" has a type " + e.attr("type"));
            }
        }

        System.out.print("\n    -> With GET: " + withAttrGet.size());

        if (InputsGet.size() > 0) {
            int counter = 0;
            for (Element e: InputsGet) {
                counter++;
                System.out.println("    -- Input #"+ counter +" has a type " + e.attr("type"));
            }
        }

    }

    // FUNCION PARA LEER TODAS LAS ETIQUETAS IMG DE UNA PAGINA Y DEVUELVE LA CANTIDAD
    public static void listAllImg(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements img = doc.select("img");

        System.out.print(" * There're " + img.size() + " Images\n");
    }

    // FUNCION PARA LEER TODAS LAS ETIQUETAS P DE UNA PAGINA Y DEVUELVE LA CANTIDAD
    public static void listAllParagraph(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements p = doc.select("p");

        System.out.print(" * There're " + p.size() + " Paragraphs\n");
    }

    // FUNCION PARA LEER TODOS LOS LINKS DE UNA PAGINA Y DEVUELVE LA CANTIDAD
    public static void getGeneralInfo(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        int counter = 0;
        for( Element element : doc.getAllElements() )
        {
            counter++;
        }

        Elements links = doc.select("a[href]");
        System.out.print("  In the Website: " + doc.title() + " \n");
        System.out.println(" * There're " + counter + " Elements HTML");
        System.out.print(" * There're " + links.size() + " Links\n");
    }

    // FUNCIONES PARA GENERAR CADENAS DE TEXTO COMO RESULTADO
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }


}