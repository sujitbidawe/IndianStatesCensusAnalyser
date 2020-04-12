package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            List<IndiaCensusCSV> csvFileList = csvBuilderFactory.getList(reader, IndiaCensusCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try ( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            List<IndiaStateCodeCSV> csvFilelist = csvBuilderFactory.getList(reader, IndiaStateCodeCSV.class);
            return csvFilelist.size();
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

    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            List<IndiaCensusCSV> csvFileList = csvBuilderFactory.getList(reader, IndiaCensusCSV.class);
            Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(census -> census.state);
            this.sort(csvFileList, censusCSVComparator);
            String sortedStateJsonData = new Gson().toJson(csvFileList);
            return sortedStateJsonData;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }

    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            List<IndiaStateCodeCSV> csvFileList = csvBuilderFactory.getList(reader, IndiaStateCodeCSV.class);
            Comparator<IndiaStateCodeCSV> stateCSVComparator = Comparator.comparing(census -> census.stateCode);
            this.sort2(csvFileList, stateCSVComparator);
            String sortedStateJsonData = new Gson().toJson(csvFileList);
            return sortedStateJsonData;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }

    private void sort(List<IndiaCensusCSV>censusList, Comparator<IndiaCensusCSV> censusCSVComparator){
        for (int i = 0; i < censusList.size() - 1; i++){
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusList.get(j);
                IndiaCensusCSV census2 = censusList.get(j+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }

    private void sort2(List<IndiaStateCodeCSV>censusList, Comparator<IndiaStateCodeCSV> censusCSVComparator){
        for (int i = 0; i < censusList.size() - 1; i++){
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaStateCodeCSV census1 = censusList.get(j);
                IndiaStateCodeCSV census2 = censusList.get(j+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }
}
