package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    Map censusCsvFileMap = null;
    Map stateCsvFileMap = null;
    List censusCsvFileList = null;
    List stateCsvFileList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            censusCsvFileMap = new HashMap<String, IndiaCensusCSV>();
            censusCsvFileList = new ArrayList<IndiaCensusCSV>();
            Iterator<IndiaCensusCSV> indiaCensusCSVIterator = csvBuilderFactory.getIterator(reader, IndiaCensusCSV.class);
            while (indiaCensusCSVIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = indiaCensusCSVIterator.next();
                this.censusCsvFileMap.put(indiaCensusCSV.state, indiaCensusCSV);
                this.censusCsvFileList = (List) censusCsvFileMap.values().stream().collect(Collectors.toList());
            }
            return censusCsvFileMap.size();
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
            stateCsvFileMap = new HashMap<String, IndiaStateCodeCSV>();
            stateCsvFileList = new ArrayList<IndiaStateCodeCSV>();
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = csvBuilderFactory.getIterator(reader, IndiaStateCodeCSV.class);
            while(indiaStateCodeCSVIterator.hasNext()) {
                IndiaStateCodeCSV indiaStateCodeCSV = indiaStateCodeCSVIterator.next();
                this.stateCsvFileMap.put(indiaStateCodeCSV.stateCode, indiaStateCodeCSV);
                this.stateCsvFileList = (List) stateCsvFileMap.values().stream().collect(Collectors.toList());
            }
            return stateCsvFileList.size();
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
        if (censusCsvFileList == null || censusCsvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        loadIndiaCensusData(csvFilePath);
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(census -> census.state);
        this.sortData(censusCsvFileList, censusCSVComparator);
        String sortedStateJsonData = new Gson().toJson(censusCsvFileList);
        return sortedStateJsonData;
    }

    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        if (censusCsvFileList == null || censusCsvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        loadIndiaStateCodeData(csvFilePath);
        Comparator<IndiaStateCodeCSV> stateCSVComparator = Comparator.comparing(census -> census.stateCode);
        this.sortData(stateCsvFileList, stateCSVComparator);
        String sortedStateCodeJsonData = new Gson().toJson(stateCsvFileList);
        return sortedStateCodeJsonData;
    }

    private <E> void sortData(List<E>censusList, Comparator<E> censusCSVComparator){
        for (int i = 0; i < censusList.size() - 1; i++){
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                E census1 = censusList.get(j);
                E census2 = censusList.get(j+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }
}
