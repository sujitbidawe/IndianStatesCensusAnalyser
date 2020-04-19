package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser<E> {

    private CountryEnum.Country country;

    public CensusAnalyser(CountryEnum.Country country) {
        this.country = country;
    }

    public CensusAnalyser() {
    }

    List csvFileList = new ArrayList<CensusDAO>();
    Map<String, CensusDAO> csvFileMap = new HashMap<>();

    public int loadCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        csvFileMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        csvFileList = (List) csvFileMap.values().stream().collect(Collectors.toList());
        return csvFileMap.size();
    }

    public String getStateWiseSortedData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getState());
        this.sortData(censusComparator);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public  String getPopulationWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getPopulation());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public  String getPopulationDensityWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getDensityPerSqKm());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);
        return sortedStateJsonData;
    }

    public  String getAreaWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.getAreaInSqKm());
        this.sortData(censusComparator);
        Collections.reverse(csvFileList);
        String sortedStateJsonData = new Gson().toJson(csvFileList);

        return sortedStateJsonData;
    }

    public  String getStateCodeWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> stateCSVComparator = Comparator.comparing(censusDAO -> censusDAO.getStateCode());
        this.sortData(stateCSVComparator);
        String sortedStateCodeJsonData = new Gson().toJson(csvFileList);
        return sortedStateCodeJsonData;
    }

    public String getStateIDWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> stateCSVComparator = Comparator.comparing(censusDAO -> censusDAO.getStateID());
        this.sortData(stateCSVComparator);
        String sortedStateCodeJsonData = new Gson().toJson(csvFileList);
        return sortedStateCodeJsonData;
    }

    public String getHousingUnitsWiseSortedCensusData(CountryEnum.Country country, String csvFilePath) throws CensusAnalyserException {
        loadCensusData(country, csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> stateCSVComparator = Comparator.comparing(censusDAO -> censusDAO.getHousingUnits());
        this.sortData(stateCSVComparator);
        Collections.reverse(csvFileList);
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
