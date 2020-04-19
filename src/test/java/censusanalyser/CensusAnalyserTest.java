package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import static censusanalyser.CensusAnalyser.Country.INDIA;
import static censusanalyser.CensusAnalyser.Country.US;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/CensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    private static final String US_CENSUS_CSV_FILE_PATH = "src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResultFirst() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateWiseSortedData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].getState());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResultLast() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateWiseSortedData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusCSV[28].getState());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnDescendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getPopulationWiseSortedCensusData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[0].getPopulation());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnDescendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getPopulationDensityWiseSortedCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, censusCSV[0].getDensityPerSqKm());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnDescendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getAreaWiseSortedCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, censusCSV[0].getAreaInSqKm());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUsCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldAscendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateWiseSortedData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldAscendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getPopulationWiseSortedCensusData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(37253956, censusCSV[0].getPopulation());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateID_ShouldAscendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateIDWiseSortedCensusData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals("AK", censusCSV[0].getStateID());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldAscendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String stateNameWiseSortedCensusData = censusAnalyser.getPopulationDensityWiseSortedCensusData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateNameWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(3805.61, censusCSV[0].getPopulationDensity(), 0.0);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnDescendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getAreaWiseSortedCensusData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            System.out.println(censusCSV[0].getAreaInSqKm());
            Assert.assertEquals(1723338.01, censusCSV[0].getAreaInSqKm(), 0.0);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnHousingUnits_ShouldAscendinglySortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getHousingUnitsWiseSortedCensusData(US, US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(13680081, censusCSV[0].getHousingUnits());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
