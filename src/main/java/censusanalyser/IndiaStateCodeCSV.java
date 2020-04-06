package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public int stateName;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public int stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "SrNo='" + srNo + '\'' +
                ", State Name='" + stateName + '\'' +
                ", TIN='" + tin + '\'' +
                ", StateCode='" + stateCode + '\'' +
                '}';
    }
}
