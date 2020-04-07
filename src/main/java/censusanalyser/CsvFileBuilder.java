package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;

public class CsvFileBuilder implements IcsvBuilderFactory{

    public <T> Iterator getIterator(Reader reader, Class classFile) throws CensusAnalyserException {

        try {

             CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
             csvToBeanBuilder.withType(classFile);
             csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
             CsvToBean<T> csvToBean = csvToBeanBuilder.build();
             Iterator<T> csvIterator = csvToBean.iterator();

             return csvIterator;
        
        }catch (RuntimeException e ){
            if(e.getMessage().contains("header!")){
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);}
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        } //catch (IOException e) {
           // throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
       // }
    }
}
