package censusanalyser;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter{
    public IndiaCensusAdapter() {
    }

    @Override
    public Map<String, CensusDAO> loadCensusData(String csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> csvFileMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        if (csvFilePath.equals("IndiaCensusCSV"))
            return csvFileMap;
        return super.loadCensusData(IndiaStateCodeCSV.class, csvFilePath);
    }

}
