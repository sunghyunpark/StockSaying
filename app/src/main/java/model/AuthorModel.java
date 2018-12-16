package model;

public class AuthorModel {
    private String authorName;

    public AuthorModel(String authorName){
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
