import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Record {
    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAuthorNames_Deduped() {
        return AuthorNames_Deduped;
    }

    public void setAuthorNames_Deduped(String authorNames_Deduped) {
        AuthorNames_Deduped = authorNames_Deduped;
    }

    public String getAuthorNames() {
        return AuthorNames;
    }

    public void setAuthorNames(String authorNames) {
        AuthorNames = authorNames;
    }

    public String getAuthorAffiliation() {
        return AuthorAffiliation;
    }

    public void setAuthorAffiliation(String authorAffiliation) {
        AuthorAffiliation = authorAffiliation;
    }

    public String getAuthorKeywords() {
        return AuthorKeywords;
    }

    public void setAuthorKeywords(String authorKeywords) {
        AuthorKeywords = authorKeywords;
    }

    @SerializedName("Year")
    String Year;

    @SerializedName("Title")
    String Title;

    @SerializedName("Link")
    String Link;

    @SerializedName("AuthorNames-Deduped")
    String AuthorNames_Deduped;

    @SerializedName("AuthorNames")
    String AuthorNames;

    @SerializedName("AuthorAffiliation")
    String AuthorAffiliation;

    @SerializedName("AuthorKeywords")
    String AuthorKeywords;

    public String getIEEEKeywords() {
        return IEEEKeywords;
    }

    public void setIEEEKeywords(String IEEEKeywords) {
        this.IEEEKeywords = IEEEKeywords;
    }

    public String getActualAuthorAffiliation() {
        return ActualAuthorAffiliation;
    }

    public void setActualAuthorAffiliation(String actualAuthorAffiliation) {
        ActualAuthorAffiliation = actualAuthorAffiliation;
    }

    String IEEEKeywords;
    String ActualAuthorAffiliation;
}
