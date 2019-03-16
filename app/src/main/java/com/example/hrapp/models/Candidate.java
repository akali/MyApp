package com.example.hrapp.models;

public class Candidate {

    private int mId;

    private String mName;

    private String mEmail;

    public Candidate(int id, String name, String email) {
        mId = id;
        mName = name;
        mEmail = email;
    }

    public Candidate() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    /*private Position position;
    private String level;
    private String bio;

    public Candidate(int id,
                     String displayName,
                     List<Language> languages,
                     Position position,
                     String level,
                     String bio) {
        this.id = id;
        this.displayName = displayName;
        this.languages = languages;
        this.position = position;
        this.level = level;
        this.bio = bio;
    }

    protected Candidate(Parcel in) {
        id = in.readInt();
        displayName = in.readString();
        languages = new ArrayList<>();
        in.readTypedList(languages, Language.CREATOR);
        position = in.readParcelable(Position.class.getClassLoader());
        level = in.readString();
    }

    public static final Creator<Candidate> CREATOR = new Creator<Candidate>() {
        @Override
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        @Override
        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", languages=" + languages +
                ", position=" + position +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        if (id != candidate.id) return false;
        if (displayName != null ? !displayName.equals(candidate.displayName) : candidate.displayName != null)
            return false;
        if (languages != null ? !languages.equals(candidate.languages) : candidate.languages != null)
            return false;
        if (position != null ? !position.equals(candidate.position) : candidate.position != null)
            return false;
        return level != null ? level.equals(candidate.level) : candidate.level == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String mDisplayName) {
        this.displayName = mDisplayName;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> mLanguages) {
        this.languages = mLanguages;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position mPosition) {
        this.position = mPosition;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String mLevel) {
        this.level = mLevel;
    }

    public static Creator<Candidate> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(displayName);
        dest.writeList(languages);
        dest.writeParcelable(position, flags);
        dest.writeString(level);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }*/
}