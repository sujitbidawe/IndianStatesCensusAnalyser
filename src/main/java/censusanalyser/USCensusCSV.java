package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

        @CsvBindByName(column = "State Id")
        private String stateID;

        @CsvBindByName(column = "State")
        private String state;

        @CsvBindByName(column = "Population Density")
        private double densityPerSqKm;

        @CsvBindByName(column = "Population")
        private int population;

        @CsvBindByName(column = "Total area")
        private double areaInSqKm;

        @CsvBindByName(column = "Housing units")
        private int housingUnits;

        @CsvBindByName(column = "Water area")
        private double waterArea;

        @CsvBindByName(column = "Land Area")
        private double landArea;

        @CsvBindByName(column = "Housing Density")
        private double housingDensity;

    public USCensusCSV(String stateID, String state, int population, double areaInSqKm, double populationDensity) {
        this.stateID = stateID;
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = populationDensity;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPopulationDensity() {
        return densityPerSqKm;
    }

    public void setPopulationDensity(double populationDensity) {
        this.densityPerSqKm = populationDensity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(double areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public int getHousingUnits() {
        return housingUnits;
    }

    public void setHousingUnits(int housingUnits) {
        this.housingUnits = housingUnits;
    }

    public double getWaterArea() {
        return waterArea;
    }

    public void setWaterArea(double waterArea) {
        this.waterArea = waterArea;
    }

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public double getHousingDensity() {
        return housingDensity;
    }

    public void setHousingDensity(double housingDensity) {
        this.housingDensity = housingDensity;
    }
    public USCensusCSV() {
    }

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "StateID='" + stateID + '\''+
                "State='" + state + '\'' +
                "PopulationDensity='" + densityPerSqKm + '\'' +
                "Population='" + population + '\'' +
                "AreaInSqKm='" + areaInSqKm + '\'' +
                "HousingUnits='" + housingUnits + '\'' +
                "WaterArea='" + waterArea + '\'' +
                "LandArea='" + landArea + '\'' +
                "HousingDensity='" + housingDensity + '\'' +
                '}';
    }
}
