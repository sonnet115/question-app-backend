package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaginationRequest {
   private int limit;
   private int offset;
}
