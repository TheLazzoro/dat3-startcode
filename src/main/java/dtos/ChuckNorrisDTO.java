package dtos;

public class ChuckNorrisDTO {
    String createdAt;
    String iconURL;
    String id;
    String updatedAt;
    String URL;
    String value;

    public ChuckNorrisDTO() {
    }

    @Override
    public String toString() {
        return "ChuckNorrisDTO{" +
                "createdAt='" + createdAt + '\'' +
                ", iconURL='" + iconURL + '\'' +
                ", id=" + id +
                ", updatedAt='" + updatedAt + '\'' +
                ", URL='" + URL + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }
}
