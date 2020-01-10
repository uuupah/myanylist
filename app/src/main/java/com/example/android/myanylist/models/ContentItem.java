package com.example.android.myanylist.models;

public class ContentItem {
    // vars
    private String title;                   // the title of the piece of media
    private int score;                      // the score out of ten (shown as half stars out of five)
    private String dateAdded;               // the date the piece of media was added to the database (autogenerated)
    private String dateCreated;             // the date the piece of media was created by its creator
    private int status;                     // the status of consumption (planning, watching, completed etc
    private String creator;                 // the name of the creator
    private String description;             // description of the piece of media
    private int image;                      // location of splash image depicting the piece of media;

    // standard constructor
    public ContentItem(String title, int score, String dateCreated, int status, String creator, String description, int image) {
        this.title = title;
        this.score = score;
        this.dateAdded = "01012020";        // should be autogenerated
        this.dateCreated = dateCreated;
        this.status = status;
        this.creator = creator;
        this.description = description;
        this.image = image;
    }

    // empty constructor
    public ContentItem(){
        this.dateAdded = "01012020";        // as above
    };

    // tostring method
    @Override
    public String toString() {
        return "ContentItem{" +
                "title='" + title + '\'' +
                ", score=" + score +
                ", dateAdded='" + dateAdded + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", status='" + status + '\'' +
                ", creator='" + creator + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }

    // getter methods
    public String getTitle() {return title;}
    public int getScore() {return score;}
    public String getDateAdded() {return dateAdded;}
    public String getDateCreated() {return dateCreated;}
    public int getStatus() {return status;}
    public String getCreator() {return creator;}
    public String getDescription() {return description;}
    public int getImage() {return image;}

    // setter methods
    public void setTitle(String title) {this.title = title;}
    public void setScore(int score) {this.score = score;}
    public void setDateAdded(String dateAdded) {this.dateAdded = dateAdded;}            // should probably not exist
    public void setDateCreated(String dateCreated) {this.dateCreated = dateCreated;}
    public void setStatus(int status) {this.status = status;}
    public void setCreator(String creator) {this.creator = creator;}
    public void setDescription(String description) {this.description = description;}
    public void setImage(int image) {this.image = image;}

    // status types
    public final static int STATUS_PLANNING = 0;
    public final static int STATUS_IN_PROGRESS = 1;
    public final static int STATUS_COMPLETED = 2;
    public final static int STATUS_DROPPED = 3;

    public String getStringStatus (int i) {
        switch (i) {
            case STATUS_PLANNING:
                return "Planning";
            case STATUS_IN_PROGRESS:
                return "In Progress";
            case STATUS_COMPLETED:
                return "Completed";
            case STATUS_DROPPED:
                return "Dropped";
            default:
                return "Invalid status";
        }
    }
}
