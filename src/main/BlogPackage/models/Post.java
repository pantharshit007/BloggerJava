package main.BlogPackage.models;

public class Post {
    private String id;
    private String title;
    private String content;
    private boolean published;
    private String authorId;

    public Post(String id, String title, String content, boolean published, String authorId){
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.authorId = authorId;
    }

    //getter
    public String getTitle(){
        return title;
    };
    public String getContent(){
        return content;
    };
    public boolean getPublished(){
        return published;
    };
    public String getAuthorId(){
        return authorId;
    };

    //setter
    public void setId(String id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setPublished(boolean published){
        this.published = published;
    }
    public void setAuthorId(String authorId){
        this.authorId = authorId;
    }
}
