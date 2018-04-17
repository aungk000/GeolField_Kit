package me.aungkooo.geologist.model;


public class StratigraphyLocation
{
    private int id, traverseId;
    private String title, time, date, latitude, longitude, formation, lithology, fossil, age, fmPath, fmName,
            beddingPlane, foldAxis, fault, joint, rPath, rName, fPath, fName, mineralization, ore,
            mineralizationNature, oPath, oName, note;
    public static String TITLE = "locationTitle", TIME = "locationTime", ID = "locationId",
            NO = "locationNo";

    public StratigraphyLocation() {
    }

    public StratigraphyLocation(int id, String title, String time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public StratigraphyLocation(int traverseId, String title, String time, String date, String latitude,
                                String longitude, String formation, String lithology, String fossil,
                                String age, String fmPath, String fmName, String beddingPlane,
                                String foldAxis, String fault, String joint, String rPath, String rName,
                                String fPath, String fName, String mineralization, String ore,
                                String mineralizationNature, String oPath, String oName, String note) {
        this.traverseId = traverseId;
        this.title = title;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formation = formation;
        this.lithology = lithology;
        this.fossil = fossil;
        this.age = age;
        this.fmPath = fmPath;
        this.fmName = fmName;
        this.beddingPlane = beddingPlane;
        this.foldAxis = foldAxis;
        this.fault = fault;
        this.joint = joint;
        this.rPath = rPath;
        this.rName = rName;
        this.fPath = fPath;
        this.fName = fName;
        this.mineralization = mineralization;
        this.ore = ore;
        this.mineralizationNature = mineralizationNature;
        this.oPath = oPath;
        this.oName = oName;
        this.note = note;
    }

    public StratigraphyLocation(String title, String time, String date, String latitude, String longitude,
                                String formation, String lithology, String fossil, String age,
                                String fmName, String beddingPlane, String foldAxis, String fault,
                                String joint, String rName, String fName,
                                String mineralization, String ore, String mineralizationNature,
                                String oName, String note) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formation = formation;
        this.lithology = lithology;
        this.fossil = fossil;
        this.age = age;
        this.fmName = fmName;
        this.beddingPlane = beddingPlane;
        this.foldAxis = foldAxis;
        this.fault = fault;
        this.joint = joint;
        this.rName = rName;
        this.fName = fName;
        this.mineralization = mineralization;
        this.ore = ore;
        this.mineralizationNature = mineralizationNature;
        this.oName = oName;
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

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getLithology() {
        return lithology;
    }

    public void setLithology(String lithology) {
        this.lithology = lithology;
    }

    public String getFossil() {
        return fossil;
    }

    public void setFossil(String fossil) {
        this.fossil = fossil;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFmPath() {
        return fmPath;
    }

    public void setFmPath(String fmPath) {
        this.fmPath = fmPath;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public String getBeddingPlane() {
        return beddingPlane;
    }

    public void setBeddingPlane(String beddingPlane) {
        this.beddingPlane = beddingPlane;
    }

    public String getFoldAxis() {
        return foldAxis;
    }

    public void setFoldAxis(String foldAxis) {
        this.foldAxis = foldAxis;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getJoint() {
        return joint;
    }

    public void setJoint(String joint) {
        this.joint = joint;
    }

    public String getrPath() {
        return rPath;
    }

    public void setrPath(String rPath) {
        this.rPath = rPath;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getfPath() {
        return fPath;
    }

    public void setfPath(String fPath) {
        this.fPath = fPath;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getMineralization() {
        return mineralization;
    }

    public void setMineralization(String mineralization) {
        this.mineralization = mineralization;
    }

    public String getOre() {
        return ore;
    }

    public void setOre(String ore) {
        this.ore = ore;
    }

    public String getMineralizationNature() {
        return mineralizationNature;
    }

    public void setMineralizationNature(String mineralizationNature) {
        this.mineralizationNature = mineralizationNature;
    }

    public String getoPath() {
        return oPath;
    }

    public void setoPath(String oPath) {
        this.oPath = oPath;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
