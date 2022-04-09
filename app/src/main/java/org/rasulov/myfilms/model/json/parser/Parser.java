package org.rasulov.myfilms.model.json.parser;

import java.util.List;

public interface Parser<T> {

    List<T> parse() throws Exception;
}
