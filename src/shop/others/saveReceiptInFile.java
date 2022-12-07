package shop.others;

import shop.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class saveReceiptInFile {

    final String path = "src//shop//Receipts//";
   private String fileName;
    private File file;
    private FileWriter fileWriter;

    public saveReceiptInFile(Receipt receipt) throws IOException {
        this.fileName = "Receipt_" + receipt.getId()+".txt";
        this.file = new File(path + fileName);
        this.file.createNewFile();
        this.fileWriter = new FileWriter(path + this.fileName);
        write(receipt.toString());
    }

    public File getFile() {
        return file;
    }

    public void write(String txt) throws IOException {
        this.fileWriter.write(txt);
        this.fileWriter.close();
    }
}
