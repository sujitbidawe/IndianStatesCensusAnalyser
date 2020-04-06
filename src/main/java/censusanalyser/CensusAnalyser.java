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

        Iterator<IndiaCensusCSV> censusCSVIterator = CsvFileBuilder.getIterator(csvFilePath, IndiaCensusCSV.class);
        int numberOfEntries = 0;

        Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> censusCSVIterator;

        numberOfEntries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false).count();
        return numberOfEntries;

    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {

        this.checkValidCsvFile(csvFilePath);

        Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = CsvFileBuilder.getIterator(csvFilePath, IndiaStateCodeCSV.class);
        int numberOfEntries = 0;

        Iterable<IndiaStateCodeCSV> IndiaStateCodeCSVIterable = () -> stateCodeCSVIterator;

        numberOfEntries = (int) StreamSupport.stream(IndiaStateCodeCSVIterable.spliterator(), false).count();
        return numberOfEntries;

    }

    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {

        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

//    public <T> Iterator getIterator(Reader reader, Class classFile) throws CensusAnalyserException {
//
//    }
}