package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        this.checkValidCsvFile(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getIterator(reader, IndiaCensusCSV.class);
            int numberOfEntries = 0;

            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> censusCSVIterator;

            numberOfEntries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false).count();
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e ){
            if(e.getMessage().contains("header!")){
                throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);}
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {

        this.checkValidCsvFile(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = this.getIterator(reader, IndiaStateCodeCSV.class);
            int numberOfEntries = 0;

            Iterable<IndiaStateCodeCSV> IndiaStateCodeCSVIterable = () -> stateCodeCSVIterator;

            numberOfEntries = (int) StreamSupport.stream(IndiaStateCodeCSVIterable.spliterator(), false).count();
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e ){
            if(e.getMessage().contains("header!")){
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);}
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {

        if(!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public <T> Iterator getIterator(Reader reader, Class classFile){

        CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(classFile);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<T> csvToBean = csvToBeanBuilder.build();
        Iterator<T> stateCodeCSVIterator = csvToBean.iterator();
        return stateCodeCSVIterator;
    }
}
