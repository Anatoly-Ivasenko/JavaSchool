package org.jschool.socnet.media;

import java.util.List;

/**
 * Интерфейс альбома медиаконтента (фото, видео, аудио), предназначен для обеспечения функционала альбомов.
 *
 * Объект предположительно должен содержать в себе информацию об авторе альбома, а также ссылки на
 * медиа {@see org.jschool.socnet.media.Media}, возможно тип хранимого медиа.
 *
 */
public interface Album {

    void putMedia(Media media);

    void removeMedia(Media media);

    List<Media> getAllMedia();

    void deleteAlbum();

    void deleteAlbumWithMedia();
}
