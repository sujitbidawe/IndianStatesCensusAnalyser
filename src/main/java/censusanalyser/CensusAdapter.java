package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;
import com.bl.csvbuilder.CsvBuilder;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

import static censusanalyser.CsvBuilderFactory.getCsvBuilder;
import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    public Map<String, CensusDAO> csvFileMap = new HashMap();

    public <T> Map<String, CensusDAO> loadCensusData(Class<T> censusCSVClass, String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            Iterator<T> csvFileIterator = csvBuilderFactory.getIterator(reader, censusCSVClass);
            Iterable<T> csvFileIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().contains("IndiaCensusCSV")) {
                while (csvFileIterator.hasNext()) {
                    CensusDAO censusDAO = new CensusDAO((IndiaCensusCSV) csvFileIterator.next());
                    csvFileMap.put(censusDAO.getState(), censusDAO);
                }
            } else if
            (censusCSVClass.getName().contains("USCensusCSV")) {

                StreamSupport.stream(csvFileIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.getState(), new CensusDAO(censusCSV)));
            }
            else if
            (censusCSVClass.getName().contains("IndiaStateCodeCSV")) {

                StreamSupport.stream(csvFileIterable.spliterator(), false)
                        .map(IndiaStateCodeCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.getStateCode(), new CensusDAO(censusCSV)));
            }
            return csvFileMap;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException("Invalid Header", CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CensusAnalyserException("Invalid Delimiters", CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IOException e) {
            e.getStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return csvFileMap;
    }
}