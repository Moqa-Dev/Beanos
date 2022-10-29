package com.moqa.beanos.models.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private long total = 0;

    private int pageSize = 10;

    private int pageNumber = 0;
}
