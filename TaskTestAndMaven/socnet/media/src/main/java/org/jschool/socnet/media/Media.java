package org.jschool.socnet.media;

import java.io.File;

/**
 * Интерфейс медиаконтента, предназначен для обработки информации о медиаконтенте пользователей.
 *
 * Объект предположительно должен содержать ссылку на файл с медиаконтентом, тип хранимого контента,
 * автора, тэги, юзертэги.
 *
 */
public interface Media {

    File getMediaFile();

    void deleteMedia();
}
