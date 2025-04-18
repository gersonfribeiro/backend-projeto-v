package com.qualiai.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HeaderPaginator<T> {
    private int limite;
    private int offset;
    private int totalPaginas;
    private int totalRegistros;
    private List<T> registros;
}
