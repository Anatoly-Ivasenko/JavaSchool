import java.util.List;

public interface Album {

    void addMedia(int idMedia);

    Album getAlbum(int idAlbum);

    List<Media> getAllMedia(int idAlbum);

    void deleteAlbum();

    void deleteAlbumWithMedia();
}
