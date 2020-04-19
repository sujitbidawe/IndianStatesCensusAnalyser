package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, INVALID_FILE_TYPE,
        INVALID_FILE_DELIMITER, INVALID_FILE_HEADER,
        INVALID_COUNTRY;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, CsvFileBuilderException.ExceptionType type) {
        super(message);
        this.type = ExceptionType.valueOf(type.name());
    }

}
