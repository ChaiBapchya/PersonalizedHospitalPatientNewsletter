package com.messengers.email;

public interface ImageProvider {

  /**
   * Returns a link to an image that is related to the query. What the query
   * represents can be different depending on the implementation.
   * @param query The query terms, links, or other types
   * @return link to an image as response to the query
   */
  String getImageLink(String query);
}
