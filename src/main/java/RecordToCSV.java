import java.io.*;
import java.util.Collection;

public class RecordToCSV {
    private static final String CSV_SEPARATOR = "|";
    public static void writeToCSV(Collection<Record> result)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.csv"), "UTF-8"));
            for (Record product : result)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(product.getYear());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.Title);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.Link);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.AuthorNames_Deduped);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.AuthorNames);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.AuthorAffiliation);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getActualAuthorAffiliation());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.AuthorKeywords);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.IEEEKeywords);
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
}
