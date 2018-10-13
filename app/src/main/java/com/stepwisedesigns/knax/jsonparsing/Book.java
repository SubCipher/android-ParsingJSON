package com.stepwisedesigns.knax.jsonparsing;

public class Book {

    private String mBookTitle;
    private String mAuthor;
    private String mISBN;
    private String mSubtitle;
    private String mID;
    private String mPublisher;
    private String mCategory;


    public Book(){

    }

    public Book(String bookTitle, String author, String publisher, String ID, String subtitle, String category){

        mBookTitle = bookTitle;
        mAuthor = author;
        mID = ID;
        mPublisher = publisher;
        mCategory = category;
        mSubtitle = subtitle;
    }


    public String getmBookTitle(){
        return mBookTitle;
    }

    public String getmAuthor(){
        return mAuthor;
    }

    public String getmISBN(){
        return mISBN;
    }

    public String getmSubtitle (){return mSubtitle; }

    public String getmID(){return  mID; }

    public String getmPublisher() { return mPublisher; }

    public String getmCategory(){ return mCategory; }
}
