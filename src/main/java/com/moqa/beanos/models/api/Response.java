package com.moqa.beanos.models.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<TData> {

    private Status status;

    private TData result;

    private Pagination pagination;

}
