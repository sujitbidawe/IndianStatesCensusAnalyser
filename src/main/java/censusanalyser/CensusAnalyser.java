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
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map csvFileMap =  new HashMap<String, CensusDAO>();;
    List csvFileList = new ArrayList<CensusDAO>();;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            IcsvBuilder csvBuilderFactory = CsvBuilderFactory.getCsvBuilder();
            Iterator<IndiaCensusCSV> indiaCensusCSVIterator = csvBuilderFactory.getIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> indiaCensusCSVIterator;
            StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false)
                    .forEach(indiaCensusCSV -> csvFileMap.put(indiaCensusCSV.getState(), new CensusDAO(indiaCensusCSV)));
            this.csvFileList = (List) csvFileMap.values().stream().collect(Collectors.toList());
            return csvFileMap.size();
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
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = csvBuilderFactory.getIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVIterable = () -> indiaStateCodeCSVIterator;
            StreamSupport.stream(indiaStateCodeCSVIterable.spliterator(), false)
                    .forEach(indiaStateCodeCSV -> csvFileMap.put(indiaStateCodeCSV.getStateCode(), new CensusDAO(indiaStateCodeCSV)));
            this.csvFileList = (List) csvFileMap.values().stream().collect(Collectors.toList());
            return csvFileMap.size();
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
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getState());
        this.sortData(censusComparator);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public String getPopulationWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getPopulation());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public String getPopulationDensityWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getDensityPerSqKm());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public String getAreaWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getAreaInSqKm());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException{
        loadIndiaStateCodeData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> stateCSVComparator = Comparator.comparing(censusDAO -> censusDAO.getStateCode());
        this.sortData(stateCSVComparator);
        String sortedStateCodeJsonData = new Gson().toJson(csvFileList);
        return sortedStateCodeJsonData;
    }

    private  void sortData(Comparator<CensusDAO> censusCSVComparator){
        for (int i = 0; i < csvFileList.size(); i++){
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                CensusDAO census1 = (CensusDAO) csvFileList.get(j);
                CensusDAO census2 = (CensusDAO) csvFileList.get(j + 1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    csvFileList.set(j, census2);
                    csvFileList.set(j+1, census1);
                }
            }
        }
    }



}
