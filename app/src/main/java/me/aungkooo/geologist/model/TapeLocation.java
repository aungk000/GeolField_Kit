package me.aungkooo.geologist.model;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeLocation
{
    private int id, traverseId, stationNo;
    private String title, date, time, latitude, longitude, bearingSlope, lithology, photoPath,
            photoName, beddingFoliation, j1, j2, j3, foldAxis, lineation, note;
    private double horizontalDistance, slopeDistance;
    public static String TITLE = "locationTitle", TIME = "locationTime", ID = "locationId",
    STATION_NO = "stationNo", NO = "locationNo";

    public TapeLocation() {
    }

    public TapeLocation(int id, String title, String time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    // database
    public TapeLocation(int traverseId, int stationNo, String title, String date, String time,
                        String latitude, String longitude, String bearingSlope, String lithology,
                        String photoPath, String photoName, String beddingFoliation, String j1,
                        String j2, String j3, String foldAxis, String lineation, String note,
                        double horizontalDistance, double slopeDistance) {
        this.traverseId = traverseId;
        this.stationNo = stationNo;
        this.title = title;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearingSlope = bearingSlope;
        this.lithology = lithology;
        this.photoPath = photoPath;
        this.photoName = photoName;
        this.beddingFoliation = beddingFoliation;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.foldAxis = foldAxis;
        this.lineation = lineation;
        this.note = note;
        this.horizontalDistance = horizontalDistance;
        this.slopeDistance = slopeDistance;
    }

    // print
    public TapeLocation(int stationNo, String title, String date, String time, String latitude,
                        String longitude, String bearingSlope, String lithology,
                        String photoName, String beddingFoliation, String j1, String j2, String j3,
                        String foldAxis, String lineation, String note, double horizontalDistance,
                        double slopeDistance) {
        this.stationNo = stationNo;
        this.title = title;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearingSlope = bearingSlope;
        this.lithology = lithology;
        this.photoName = photoName;
        this.beddingFoliation = beddingFoliation;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.foldAxis = foldAxis;
        this.lineation = lineation;
        this.note = note;
        this.horizontalDistance = horizontalDistance;
        this.slopeDistance = slopeDistance;
    }


    // single
    public TapeLocation(int stationNo, String date, String time, String latitude,
                        String longitude, String bearingSlope, double horizontalDistance,
                        double slopeDistance, String lithology, String photoPath,
                        String photoName, String beddingFoliation, String j1, String j2, String j3,
                        String foldAxis, String lineation, String note) {
        this.stationNo = stationNo;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearingSlope = bearingSlope;
        this.lithology = lithology;
        this.photoPath = photoPath;
        this.photoName = photoName;
        this.beddingFoliation = beddingFoliation;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.foldAxis = foldAxis;
        this.lineation = lineation;
        this.note = note;
        this.horizontalDistance = horizontalDistance;
        this.slopeDistance = slopeDistance;
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

    public int getStationNo() {
        return stationNo;
    }

    public void setStationNo(int stationNo) {
        this.stationNo = stationNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public double getSlopeDistance() {
        return slopeDistance;
    }

    public void setSlopeDistance(double slopeDistance) {
        this.slopeDistance = slopeDistance;
    }

    public String getBearingSlope() {
        return bearingSlope;
    }

    public void setBearingSlope(String bearingSlope) {
        this.bearingSlope = bearingSlope;
    }

    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    public void setHorizontalDistance(double horizontalDistance) {
        this.horizontalDistance = horizontalDistance;
    }

    public String getLithology() {
        return lithology;
    }

    public void setLithology(String lithology) {
        this.lithology = lithology;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
