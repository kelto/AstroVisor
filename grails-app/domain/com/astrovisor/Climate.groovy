package com.astrovisor

class Climate {

    float minTemp //In Celsius
    float maxTemp
    float meanTemp
    int seasons

    enum ClimateType {
        POLAR, CONTINENTAL, MODERATE, TROPICAL, DRY
    }

    ClimateType type

    static constraints = {
        minTemp validator: { val, obj -> val >= -273
        }
        meanTemp validator: { val, obj ->
            return (val >= obj.minTemp && val <= obj.maxTemp)
        }
        seasons validator: { val ->
            return val >= 1
        }
    }

    Climate(float minTemp, float maxTemp, float meanTemp, int seasons){
        this.minTemp = minTemp
        this.maxTemp = maxTemp
        this.meanTemp = meanTemp
        this.seasons = seasons

        if(meanTemp < -5){
            type = ClimateType.POLAR
        }
        else if(meanTemp >= -5 && meanTemp < 15){
            type = ClimateType.CONTINENTAL
        }
        else if(meanTemp >= 15 && meanTemp < 30){
            type = ClimateType.MODERATE
        }
        else if(meanTemp >= 30 && meanTemp < 50){
            type = ClimateType.TROPICAL
        }
        else{
            type = ClimateType.DRY
        }
    }
}
