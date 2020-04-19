package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import static censusanalyser.CountryEnum.Country.INDIA;

public class StateCodeAnalyserTest {

    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCodeData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/StateCodeData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/StateCodeInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/StateCodeInvalidHeader.csv";

    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CODE_CSV_FILE_PATH);
           Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeData_WhenSortedOnState_ShouldReturnSortedResultFirst() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateCodeWiseSortedCensusData(INDIA, INDIA_STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] stateCSV = new Gson().fromJson(sortedData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD", stateCSV[0].getStateCode());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenSortedOnState_ShouldReturnSortedResultLast() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedData = censusAnalyser.getStateCodeWiseSortedCensusData(INDIA, INDIA_STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] stateCSV = new Gson().fromJson(sortedData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("WB", stateCSV[36].getStateCode());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
}
