
// EXERCISE 2: Factory Method Pattern - Documents

import java.util.*;

interface Document {
    void open();
}
 
class WordDocument implements Document {
    public void open() { System.out.println("Opening Word Document"); }
}
 
class PdfDocument implements Document {
    public void open() { System.out.println("Opening PDF Document"); }
}
 
class ExcelDocument implements Document {
    public void open() { System.out.println("Opening Excel Document"); }
}
 
abstract class DocumentFactory {
    public abstract Document createDocument();
}
 
class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new WordDocument(); }
}
 
class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new PdfDocument(); }
}
 
class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new ExcelDocument(); }
}
 
class FactoryMethodTest {
    public static void run() {
        System.out.println("\n=== Exercise 2: Factory Method Pattern ===");
        DocumentFactory[] factories = {
            new WordDocumentFactory(),
            new PdfDocumentFactory(),
            new ExcelDocumentFactory()
        };
        for (DocumentFactory factory : factories) {
            Document doc = factory.createDocument();
            doc.open();
        }
    }
}

class Main {
    public static void main(String[] args) {
        FactoryMethodTest.run();
    }
}
 