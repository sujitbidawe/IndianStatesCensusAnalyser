package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        this.checkValidCsvFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilderFactory.getIterator(reader, IndiaCensusCSV.class);

            int numberOfEntries;

            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> censusCSVIterator;

             numberOfEntries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false).count();
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {

        this.checkValidCsvFile(csvFilePath);
        try ( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IcsvBuilder csvFileBuilder = CsvBuilderFactory.getCsvBuilder();

            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvFileBuilder.getIterator(reader, IndiaStateCodeCSV.class);
            int numberOfEntries = 0;

            Iterable<IndiaStateCodeCSV> IndiaStateCodeCSVIterable = () -> censusCSVIterator;

        numberOfEntries = (int) StreamSupport.stream(IndiaStateCodeCSVIterable.spliterator(), false).count();
        return numberOfEntries;

        } catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }
    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {

        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

}