package com.game.portal.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaginationRequest {
   private int limit;
   private int pageNumber;
}
