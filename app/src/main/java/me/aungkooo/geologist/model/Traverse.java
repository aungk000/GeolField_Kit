package me.aungkooo.geologist.model;


public class Traverse
{
    private int id;
    private String title, date;

    public static String TITLE = "traverseTitle", DATE = "traverseDate", SIZE = "traverseSize",
    ID = "traverseId";
    public static int REQUEST_TRAVERSE_NEW = 3;
    public static int RESULT_OK = -1;

    public Traverse() {
    }

    public Traverse(int id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Traverse(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
