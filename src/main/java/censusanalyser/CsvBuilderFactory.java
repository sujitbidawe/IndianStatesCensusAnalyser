package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;

public class CsvBuilderFactory {
    public static CsvBuilder getCsvBuilder(){
        return new CsvBuilder();
    }
}
