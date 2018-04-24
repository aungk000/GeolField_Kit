package me.aungkooo.geologist.model;


public class StratigraphyLocation
{
    private int id, traverseId;
    private String title, time, date, latitude, longitude, formation, lithology, fossil, age,
            formationPath, formationName, formationFacing, beddingPlane, foldAxis, fault, joint,
            rockPath, rockName, rockFacing,
            fossilPath, fossilName, fossilFacing, mineralization, ore, mineralizationNature,
            orePath, oreName, oreFacing, note;
    public static final String TITLE = "locationTitle", TIME = "locationTime", ID = "locationId",
            NO = "locationNo";

    public StratigraphyLocation() {
    }

    public StratigraphyLocation(int id, String title, String time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    // database
    public StratigraphyLocation(int traverseId, String title, String time, String date, String latitude,
                                String longitude, String formation, String lithology, String fossil,
                                String age, String formationPath, String formationName, String formationFacing,
                                String beddingPlane, String foldAxis, String fault, String joint,
                                String rockPath, String rockName, String rockFacing,
                                String fossilPath, String fossilName, String fossilFacing,
                                String mineralization, String ore, String mineralizationNature,
                                String orePath, String oreName, String oreFacing, String note) {
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
        this.formationPath = formationPath;
        this.formationName = formationName;
        this.formationFacing = formationFacing;
        this.beddingPlane = beddingPlane;
        this.foldAxis = foldAxis;
        this.fault = fault;
        this.joint = joint;
        this.rockPath = rockPath;
        this.rockName = rockName;
        this.rockFacing = rockFacing;
        this.fossilPath = fossilPath;
        this.fossilName = fossilName;
        this.fossilFacing = fossilFacing;
        this.mineralization = mineralization;
        this.ore = ore;
        this.mineralizationNature = mineralizationNature;
        this.orePath = orePath;
        this.oreName = oreName;
        this.oreFacing = oreFacing;
        this.note = note;
    }

    // print
    public StratigraphyLocation(String title, String time, String date, String latitude, String longitude,
                                String formation, String lithology, String fossil, String age,
                                String formationName, String formationFacing, String beddingPlane,
                                String foldAxis,
                                String fault, String joint, String rockName, String rockFacing,
                                String fossilName, String fossilFacing,
                                String mineralization, String ore, String mineralizationNature,
                                String oreName, String oreFacing, String note) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formation = formation;
        this.lithology = lithology;
        this.fossil = fossil;
        this.age = age;
        this.formationName = formationName;
        this.formationFacing = formationFacing;
        this.beddingPlane = beddingPlane;
        this.foldAxis = foldAxis;
        this.fault = fault;
        this.joint = joint;
        this.rockName = rockName;
        this.rockFacing = rockFacing;
        this.fossilName = fossilName;
        this.fossilFacing = fossilFacing;
        this.mineralization = mineralization;
        this.ore = ore;
        this.mineralizationNature = mineralizationNature;
        this.oreName = oreName;
        this.oreFacing = oreFacing;
        this.note = note;
    }

    // single
    public StratigraphyLocation(String time, String date, String latitude, String longitude,
                                String formation, String lithology, String fossil, String age,
                                String formationPath, String formationName, String formationFacing,
                                String beddingPlane,
                                String foldAxis, String fault, String joint, String rockPath,
                                String rockName, String rockFacing, String fossilPath, String fossilName,
                                String fossilFacing, String mineralization, String ore,
                                String mineralizationNature, String orePath, String oreName,
                                String oreFacing, String note) {
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formation = formation;
        this.lithology = lithology;
        this.fossil = fossil;
        this.age = age;
        this.formationPath = formationPath;
        this.formationName = formationName;
        this.formationFacing = formationFacing;
        this.beddingPlane = beddingPlane;
        this.foldAxis = foldAxis;
        this.fault = fault;
        this.joint = joint;
        this.rockPath = rockPath;
        this.rockName = rockName;
        this.rockFacing = rockFacing;
        this.fossilPath = fossilPath;
        this.fossilName = fossilName;
        this.fossilFacing = fossilFacing;
        this.mineralization = mineralization;
        this.ore = ore;
        this.mineralizationNature = mineralizationNature;
        this.orePath = orePath;
        this.oreName = oreName;
        this.oreFacing = oreFacing;
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

    public String getFormationPath() {
        return formationPath;
    }

    public void setFormationPath(String formationPath) {
        this.formationPath = formationPath;
    }

    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public String getFormationFacing() {
        return formationFacing;
    }

    public void setFormationFacing(String formationFacing) {
        this.formationFacing = formationFacing;
    }

    public String getRockPath() {
        return rockPath;
    }

    public void setRockPath(String rockPath) {
        this.rockPath = rockPath;
    }

    public String getRockName() {
        return rockName;
    }

    public void setRockName(String rockName) {
        this.rockName = rockName;
    }

    public String getRockFacing() {
        return rockFacing;
    }

    public void setRockFacing(String rockFacing) {
        this.rockFacing = rockFacing;
    }

    public String getFossilPath() {
        return fossilPath;
    }

    public void setFossilPath(String fossilPath) {
        this.fossilPath = fossilPath;
    }

    public String getFossilName() {
        return fossilName;
    }

    public void setFossilName(String fossilName) {
        this.fossilName = fossilName;
    }

    public String getFossilFacing() {
        return fossilFacing;
    }

    public void setFossilFacing(String fossilFacing) {
        this.fossilFacing = fossilFacing;
    }

    public String getOrePath() {
        return orePath;
    }

    public void setOrePath(String orePath) {
        this.orePath = orePath;
    }

    public String getOreName() {
        return oreName;
    }

    public void setOreName(String oreName) {
        this.oreName = oreName;
    }

    public String getOreFacing() {
        return oreFacing;
    }

    public void setOreFacing(String oreFacing) {
        this.oreFacing = oreFacing;
    }
}
