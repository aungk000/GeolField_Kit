package me.aungkooo.geologist.model;

/**
 * Created by Ko Oo on 14/4/2018.
 */

public class MyNotesLocation
{
    private int id, traverseId;
    private String title, time, date, latitude, longitude, map, rockType, rockUnit, outcropPath, outcropName,
            texture, weatheringColor, freshColor, grainSize, mineralComposition, lithologyNote,
            samplePath, sampleName, beddingFoliation, j1, j2, j3, foldAxis, lineation, note;
    public static String TITLE = "locationTitle", TIME = "locationTime", ID = "locationId",
            NO = "locationNo";

    public MyNotesLocation() {
    }

    public MyNotesLocation(int id, String title, String time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public MyNotesLocation(int traverseId, String title, String time, String date, String latitude,
                           String longitude,
                           String map, String rockType, String rockUnit, String outcropPath,
                           String outcropName, String texture, String weatheringColor,
                           String freshColor, String grainSize, String mineralComposition,
                           String lithologyNote, String samplePath, String sampleName,
                           String beddingFoliation, String j1, String j2, String j3, String foldAxis,
                           String lineation, String note) {
        this.traverseId = traverseId;
        this.title = title;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.map = map;
        this.rockType = rockType;
        this.rockUnit = rockUnit;
        this.outcropPath = outcropPath;
        this.outcropName = outcropName;
        this.texture = texture;
        this.weatheringColor = weatheringColor;
        this.freshColor = freshColor;
        this.grainSize = grainSize;
        this.mineralComposition = mineralComposition;
        this.lithologyNote = lithologyNote;
        this.samplePath = samplePath;
        this.sampleName = sampleName;
        this.beddingFoliation = beddingFoliation;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.foldAxis = foldAxis;
        this.lineation = lineation;
        this.note = note;
    }

    public MyNotesLocation(String title, String time, String date, String latitude, String longitude,
                           String map,
                           String rockType, String rockUnit, String outcropName, String texture,
                           String weatheringColor, String freshColor, String grainSize,
                           String mineralComposition, String lithologyNote, String sampleName,
                           String beddingFoliation, String j1, String j2, String j3, String foldAxis,
                           String lineation, String note) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.map = map;
        this.rockType = rockType;
        this.rockUnit = rockUnit;
        this.outcropName = outcropName;
        this.texture = texture;
        this.weatheringColor = weatheringColor;
        this.freshColor = freshColor;
        this.grainSize = grainSize;
        this.mineralComposition = mineralComposition;
        this.lithologyNote = lithologyNote;
        this.sampleName = sampleName;
        this.beddingFoliation = beddingFoliation;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.foldAxis = foldAxis;
        this.lineation = lineation;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTraverseId() {
        return traverseId;
    }

    public void setTraverseId(int traverseId) {
        this.traverseId = traverseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getRockType() {
        return rockType;
    }

    public void setRockType(String rockType) {
        this.rockType = rockType;
    }

    public String getRockUnit() {
        return rockUnit;
    }

    public void setRockUnit(String rockUnit) {
        this.rockUnit = rockUnit;
    }

    public String getOutcropPath() {
        return outcropPath;
    }

    public void setOutcropPath(String outcropPath) {
        this.outcropPath = outcropPath;
    }

    public String getOutcropName() {
        return outcropName;
    }

    public void setOutcropName(String outcropName) {
        this.outcropName = outcropName;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getWeatheringColor() {
        return weatheringColor;
    }

    public void setWeatheringColor(String weatheringColor) {
        this.weatheringColor = weatheringColor;
    }

    public String getFreshColor() {
        return freshColor;
    }

    public void setFreshColor(String freshColor) {
        this.freshColor = freshColor;
    }

    public String getGrainSize() {
        return grainSize;
    }

    public void setGrainSize(String grainSize) {
        this.grainSize = grainSize;
    }

    public String getMineralComposition() {
        return mineralComposition;
    }

    public void setMineralComposition(String mineralComposition) {
        this.mineralComposition = mineralComposition;
    }

    public String getLithologyNote() {
        return lithologyNote;
    }

    public void setLithologyNote(String lithologyNote) {
        this.lithologyNote = lithologyNote;
    }

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getBeddingFoliation() {
        return beddingFoliation;
    }

    public void setBeddingFoliation(String beddingFoliation) {
        this.beddingFoliation = beddingFoliation;
    }

    public String getJ1() {
        return j1;
    }

    public void setJ1(String j1) {
        this.j1 = j1;
    }

    public String getJ2() {
        return j2;
    }

    public void setJ2(String j2) {
        this.j2 = j2;
    }

    public String getJ3() {
        return j3;
    }

    public void setJ3(String j3) {
        this.j3 = j3;
    }

    public String getFoldAxis() {
        return foldAxis;
    }

    public void setFoldAxis(String foldAxis) {
        this.foldAxis = foldAxis;
    }

    public String getLineation() {
        return lineation;
    }

    public void setLineation(String lineation) {
        this.lineation = lineation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
